package org.supply.simulator.sandbox;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.*;
import org.supply.simulator.display.assetengine.shader.ShaderEngine;
import org.supply.simulator.display.assetengine.shader.ShaderProgramType;
import org.supply.simulator.display.assetengine.shader.ShaderType;
import org.supply.simulator.display.assetengine.shader.impl.BasicShaderEngine;
import org.supply.simulator.display.window.Camera;
import org.supply.simulator.sandbox.mockdisplay.MockCamera;
import org.supply.simulator.sandbox.mockdisplay.MockDisplayCore;
import org.supply.simulator.sandbox.texture.TexturedChunk;
import org.supply.simulator.sandbox.texture.TexturedChunkManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * Created by Alex on 6/28/2014.
 */
public class FramebufferTexturedChunkTest {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private TexturedChunkManager manager;
    private Camera camera;
    private ShaderEngine shaderEngine;

    //Texture variables
    private int frameBufferId;
    private int depthBufferId;
    private int textureId;

    private static final int textureWidth = 500;
    private static final int textureHeight = 500;


    @Before
    public void create() {
        MockDisplayCore.build("FramebufferTexturedChunkTest");

        shaderEngine = new BasicShaderEngine();
        shaderEngine.setShaderFile("shaders/vertexWithTexture.glsl", ShaderType.VERTEX, ShaderProgramType.PLAY);
        shaderEngine.setShaderFile("shaders/fragmentsWithTexture.glsl", ShaderType.FRAGMENT, ShaderProgramType.PLAY);

        camera = new MockCamera();

        manager = new TexturedChunkManager();
        manager.setChunkSizes(20,20,1,1);

        camera.setProjectionMatrixLocation(shaderEngine.get(ShaderProgramType.PLAY).getProjectionMatrixLocation());
        camera.setModelMatrixLocation(shaderEngine.get(ShaderProgramType.PLAY).getModelMatrixLocation());
        camera.setViewMatrixLocation(shaderEngine.get(ShaderProgramType.PLAY).getViewMatrixLocation());


        camera.build();

        initFrameBuffer();
    }

    @Test
    public void render() {
        while (!Display.isCloseRequested()) {
            //camera.update();

            GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);


            EXTFramebufferObject.glBindFramebufferEXT( EXTFramebufferObject.GL_FRAMEBUFFER_EXT, frameBufferId);
            GL11.glViewport(0, 0, WIDTH, HEIGHT);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);


            // Set shader program type to VIEW
            GL20.glUseProgram(shaderEngine.get(ShaderProgramType.PLAY).getProgramId());

            camera.render();

            // Clear shader program type
            GL20.glUseProgram(0);

            EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);

            // Clear bit

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // Set shader program type t o CHUNK
            GL20.glUseProgram(shaderEngine.get(ShaderProgramType.PLAY).getProgramId());

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

            // Update chunkCollection with new camera position
            manager.update(camera);
            Iterator<TexturedChunk> it = manager.iterator();
            while (it.hasNext())
            {
                it.next().render();
            }
            GL20.glUseProgram(0);



            MockDisplayCore.render();
        }

        manager.clear();
        GL20.glUseProgram(0);
        GL20.glDeleteProgram(shaderEngine.get(ShaderProgramType.PLAY).getProgramId());
        MockDisplayCore.destroy();
    }

    private void initFrameBuffer() {
        //create frame and depth buffer
        frameBufferId = EXTFramebufferObject.glGenFramebuffersEXT();
        depthBufferId = EXTFramebufferObject.glGenRenderbuffersEXT();

        //Bind frame buffer
        EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,frameBufferId);

        //create texture
        textureId = 0;
        try {
            textureId = loadPNGTexture2D("textures/alexsface.png", GL13.GL_TEXTURE0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Attach texture to framebuffer
        EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
                EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT,
                GL11.GL_TEXTURE_2D,textureId,0);

        //bind depth buffer
        EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT,depthBufferId);

        //Alloc depth buffer space
        EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT,
                GL14.GL_DEPTH_COMPONENT24, textureWidth, textureHeight);

        // bind to renderbuffer
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT
                ,EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT
                ,EXTFramebufferObject.GL_RENDERBUFFER_EXT
                , depthBufferId);



        if(EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT)
                ==EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT) {
            System.out.println("Framebuffer complete");
        } else {
            System.out.println("Framebuffer failed");
        }
    }

    private int loadPNGTexture2D(String filename, int textureUnit) throws Exception {
        ByteBuffer buf = null;
        int tWidth = 0;
        int tHeight = 0;

        try {
            // Open the PNG file as an InputStream
            InputStream in = ClassLoader.getSystemResourceAsStream(filename);
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            // Get the width and height of the texture
            tWidth = decoder.getWidth();
            tHeight = decoder.getHeight();


            // Decode the PNG file in a ByteBuffer
            buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("you suck");

//            System.exit(-1);
        }

        // Create a new texture object in memory and bind it
        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(textureUnit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        // All RGB bytes are aligned to each other and each component is 1 byte
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        // Upload the texture data and generate mip maps (for scaling)
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, tWidth, tHeight, 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        // Setup the ST coordinate system
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        // Setup what to do when the texture has to be scaled
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_LINEAR_MIPMAP_LINEAR);

        return texId;
    }


}
