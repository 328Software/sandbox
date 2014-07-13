package org.supply.simulator.sandbox.prototype.util;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by Alex on 6/1/2014.
 */
public class GraphicsUtilities {

    public static int loadShader(String filename, int type) throws Exception  {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;


        int lineNum = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(filename)));
        String line;
        while ((line = reader.readLine()) != null) {
            lineNum++;
            shaderSource.append(line).append("\n");
        }
        reader.close();


        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader.");
            throw new Exception("lineNum: " + lineNum);
//            System.exit(-1);
        }

        return shaderID;
    }

    public static int loadPNGTexture(String filename, int textureUnit) throws Exception {
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
