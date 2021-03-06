package org.supply.simulator.sandbox.prototype.display;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.supply.simulator.sandbox.prototype.input.Camera;
import org.supply.simulator.sandbox.prototype.input.Input;
import org.supply.simulator.sandbox.prototype.util.DebugUtilities;
import org.supply.simulator.sandbox.prototype.util.GraphicsUtilities;
import org.supply.simulator.sandbox.prototype.util.MathUtilities;
import org.supply.simulator.sandbox.prototype.util.MyDebugger;


import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 6/1/2014.
 */
public class UserDisplay {

    //Singleton Instances
    private static UserDisplay instance;

    // Setup variables
    private static final String WINDOW_TITLE = "The Quad: Moving";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int textureWidth = 620;
    private static final int textureHeight = 539;

    //Quad variables
    List<QuadIds> quadIdsList;
    private int quadColumns;
    private int quadRows;

    //VBx indices
    Integer vboiId = null;
    Integer vboId = null;
    Integer colorVboId = null;
    private int indicesCount = 0;

    // Shader variables
    private int pId = 0;

    // Moving variables
    private int projectionMatrixLocation = 0;
    private int viewMatrixLocation = 0;
    private int modelMatrixLocation = 0;
    private Matrix4f projectionMatrix = null;
    private Matrix4f viewMatrix = null;
    private Matrix4f modelMatrix = null;
    private FloatBuffer matrix44Buffer = null;

    //Camera/Quad positional information
    private Vector3f modelPos = null;
    private Vector3f modelAngle = null;
    private Vector3f modelScale = null;
    private Vector3f cameraPos = null;
    private Vector3f cameraAngle = null;

    //Texture variables
    int frameBufferId;
    int depthBufferId;


    //*****Singleton pattern constructor
    //to be removed when we get dependency injection

    /**
     *
     */

    private UserDisplay() {}

    /**
     *
     * @return
     */
    public static UserDisplay instance() {
        if (instance==null)instance=new UserDisplay();
        return instance;
    }


    //*****Singleton pattern constructor


    //*****Init:
    //Starts up the display
    public void init () {

        //initialize quadIds
        this.quadIdsList = new ArrayList<QuadIds>();

        try {
            // Initialize OpenGL (Display)
            this.setupOpenGL();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("setupOpenGL failed");
        }

        try {
            this.setupQuads();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("setupQuad failed");
        }
        try {
            this.setupShaders();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("setupShaders failed");
        }

        try {
            this.setupTexture();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("setupTexture failed");
        }

        this.setupMatrices();

    }



    //*****Loop method
    //These methods must be executed in order during each display loop cycle
    public void receiveInput(Camera input) {
        modelPos    = input.getModelPos();
        modelAngle  = input.getModelAngle();
        modelScale  = input.getModelScale();
        cameraPos   = input.getCameraPos();
        cameraAngle = input.getCameraAngle();
    }
    public void render(Camera input) {
        //*****MOVE THE CAMERA********************************

        //-- Update matrices
        // Reset view
        viewMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();

        // Translate and rotate camera

//        if (cameraPos!=input.getCameraPos()) {
            //System.out.println("TRANSLATE");
            cameraPos   = input.getCameraPos();
            Matrix4f.translate(cameraPos, viewMatrix, viewMatrix);
           // DebugUtilities.printMatrix4f(viewMatrix);
//        }

//        if (cameraAngle!=input.getCameraAngle()) {
           // cameraAngle = input.getCameraAngle();
        //    System.out.println("ROTATE Z");
            Matrix4f.rotate(cameraAngle.z, new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
          //  DebugUtilities.printMatrix4f(viewMatrix);

         //   System.out.println("ROTATE Y");
            Matrix4f.rotate(cameraAngle.y, new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
          //  DebugUtilities.printMatrix4f(viewMatrix);

      //      System.out.println("ROTATE x");
            Matrix4f.rotate(cameraAngle.x, new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
         //   DebugUtilities.printMatrix4f(viewMatrix);
//        }


        // Scale, translate and rotate model
        Matrix4f.scale(modelScale, modelMatrix, modelMatrix);
        Matrix4f.translate(modelPos, modelMatrix, modelMatrix);
        Matrix4f.rotate(MathUtilities.degreesToRadians(modelAngle.z), new Vector3f(0, 0, 1),
                modelMatrix, modelMatrix);
        Matrix4f.rotate(MathUtilities.degreesToRadians(modelAngle.y), new Vector3f(0, 1, 0),
                modelMatrix, modelMatrix);
        Matrix4f.rotate(MathUtilities.degreesToRadians(modelAngle.x), new Vector3f(1, 0, 0),
                modelMatrix, modelMatrix);

        // Upload matrices to the uniform variables
        GL20.glUseProgram(pId);

        projectionMatrix.store(matrix44Buffer); matrix44Buffer.flip();
        GL20.glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
        viewMatrix.store(matrix44Buffer); matrix44Buffer.flip();
        GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);
        modelMatrix.store(matrix44Buffer); matrix44Buffer.flip();
        GL20.glUniformMatrix4(modelMatrixLocation, false, matrix44Buffer);

        GL20.glUseProgram(0);




        //*****RENDER THE QUADS*******************************
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL20.glUseProgram(pId);

//        EXTFramebufferObject.glBindFramebufferEXT( EXTFramebufferObject.GL_FRAMEBUFFER_EXT, frameBufferId);
        //GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
//        GL11.glViewport( 0, 0, textureWidth, textureHeight);
//        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);


        for(QuadIds ids: quadIdsList) {
            int vaoId = ids.getVaoId();
            int vboiId = ids.getVboiId();
            // Bind to the VAO that has all the information about the vertices
            GL30.glBindVertexArray(vaoId);
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);

            // Bind to the index VBO that has all the information about the order of the vertices
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);

            // Draw the vertices
            GL32.glDrawElementsBaseVertex(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_INT, 0,0);
            // Put everything back to default (deselect)
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL30.glBindVertexArray(0);
        }

//        EXTFramebufferObject.glBindFramebufferEXT( EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
        //GL11.glPopAttrib();


        GL20.glUseProgram(0);

        this.exitOnGLError("render");

        // Force a maximum FPS of about 60
        Display.sync(60);

        // Let the CPU synchronize with the GPU if GPU is tagging behind
        Display.update();
    }
    //*****Loop method


    //*****exit methods
    public void destroyOpenGL() {

        // Delete the shaders
        GL20.glUseProgram(0);
        GL20.glDeleteProgram(pId);

        for(QuadIds ids: quadIdsList) {
            int vaoId = ids.getVaoId();
            int vboId = ids.getVboId();
            int vboiId = ids.getVboiId();
            int colorVboId = ids.getColorVboId();

            // Select the VAO
            GL30.glBindVertexArray(vaoId);

            // Disable the VBO index from the VAO attributes list
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);

            // Delete the vertex VBO
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
            GL15.glDeleteBuffers(vboId);

            // Delete the index VBO
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL15.glDeleteBuffers(vboiId);


            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL15.glDeleteBuffers(colorVboId);

            // Delete the VAO
            GL30.glBindVertexArray(0);
            GL30.glDeleteVertexArrays(vaoId);
        }

        this.exitOnGLError("destroyOpenGL");

        Display.destroy();
    }
    //*****exit methods

    //*****Getters
    public boolean isCloseRequested() {
        return Display.isCloseRequested();
    }
    //*****Getters

    //*****Setters
    //Must be set before calling init
    public void setQuadRowsCols (int rows, int columns) {
        this.quadColumns = columns;
        this.quadRows = rows;
    }
    //*****Setters

    //***** Private utility functions
    private void setupOpenGL() {
        // Setup an OpenGL context with API version 3.2
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                    .withForwardCompatible(true)
                    .withProfileCore(true);

            org.lwjgl.opengl.Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            org.lwjgl.opengl.Display.setTitle(WINDOW_TITLE);
            org.lwjgl.opengl.Display.create(pixelFormat, contextAtrributes);

            GL11.glViewport(0, 0, WIDTH, HEIGHT);
        } catch (LWJGLException e) {
            e.printStackTrace();
//
        }

        // Setup an XNA like background color
        GL11.glClearColor(130f/255f, 208f/255f, 157f/255f, 0f);

        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, WIDTH, HEIGHT);

        this.exitOnGLError("setupOpenGL");
    }


    private void setupQuads() {
        List<Integer> values = new ArrayList<Integer>();

        ByteBuffer verticesByteBuffer = BufferUtils.createByteBuffer(4 * quadRows * quadColumns *
                VertexData.positionBytesCount);


        FloatBuffer verticesFloatBuffer = verticesByteBuffer.asFloatBuffer();

        verticesByteBuffer = BufferUtils.createByteBuffer(4 * quadRows * quadColumns *
                VertexData.colorByteCount);

        for(int i = 0; i < quadRows; i++) {
            for(int j = 0; j < quadColumns; j++) {

                int offset = (i*quadColumns+j)*4;

                values.add(offset);
                values.add(offset+1);
                values.add(offset+2);
                values.add(offset+2);
                values.add(offset+3);
                values.add(offset);

//                byte blackInt = (i%2|j%2)==0||(i%2&j%2)==1?(byte)0:(byte)((i*quadColumns+j)/256);
                byte blackInt = (i%2|j%2)==0||(i%2&j%2)==1?(byte)0:(byte)-1;



                float x =-0.5f + 0.1f*i,y=0.5f-0.1f*j,z=-(.1f*(i+j)),length=0.1f;


                // We'll define our quad using 4 vertices of the custom 'TexturedVertex' class
                VertexData v0 = new VertexData();
                //top left
                v0.setXYZ(x, y, /*z+length*/0); v0.setRGB(blackInt, (byte)(blackInt&0xf), (byte)(blackInt&0xf0));// v0.setST(0, 0);
                VertexData v1 = new VertexData();
                //bottom left
                v1.setXYZ(x, y-length, /*z*/0); v1.setRGB(blackInt, (byte)(blackInt&0xf), (byte)(blackInt&0xf0));// v1.setST(0, 1);
                VertexData v2 = new VertexData();
                //bottom right
                v2.setXYZ(x+length, y-length, /*z-length*/0); v2.setRGB(blackInt, (byte)(blackInt&0xf), (byte)(blackInt&0xf0));// v2.setST(1, 1);
                VertexData v3 = new VertexData();
                //top right
                v3.setXYZ(x+length, y, /*z*/0); v3.setRGB(blackInt, (byte)(blackInt&0xf), (byte)(blackInt&0xf0));// v3.setST(1, 0);

                VertexData[] vertices = new VertexData[]  {v0, v1, v2, v3};

                // Put each 'Vertex' in one FloatBuffer

                for (int k = 0; k < vertices.length; k++) {
                    // Add position, color and texture floats to the buffer
                    verticesFloatBuffer.put(vertices[k].getElements().getPositionData());
                    verticesByteBuffer.put(vertices[k].getElements().getColorData());
                }
            }
        }

        verticesFloatBuffer.flip();
        verticesByteBuffer.flip();


        if(vboiId == null) {

            int[] indices = new int[values.size()];

           System.out.println("PRINTING BUFFER INDICES");
            for(int i = 0; i < indices.length;i++) {
                indices[i] = values.get(i);

//                if (i<100) {
//                    System.out.println("  "+indices[i]);
//                }
            }


            indicesCount = indices.length;
            IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indicesCount);
            indicesBuffer.put(indices);

            indicesBuffer.flip();

            vboiId = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer,
                    GL15.GL_STATIC_DRAW);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        }



        // Create a new Vertex Array Object in memory and select it (bind)
        int vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        // Create a new Vertex Buffer Object in memory and select it (bind)
        if(vboId == null) {
            vboId = GL15.glGenBuffers();
        }
        if(colorVboId == null) {
            colorVboId = GL15.glGenBuffers();
        }
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesFloatBuffer, GL15.GL_STATIC_DRAW);

        // Put the position coordinates in attribute list 0
        GL20.glVertexAttribPointer(0, VertexData.positionElementCount, GL11.GL_FLOAT,
                false, VertexData.positionBytesCount, VertexData.positionByteOffset);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorVboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesByteBuffer, GL15.GL_STATIC_DRAW);
        // Put the color components in attribute list 1
        GL20.glVertexAttribPointer(1, VertexData.colorElementCount, GL11.GL_UNSIGNED_BYTE,
                true, VertexData.colorByteCount, 0);


        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);

        // Create a new VBO for the indices and select it (bind) - INDICES


        quadIdsList.add(new QuadIds(vaoId, vboId, vboiId, colorVboId));

        // Set the default quad rotation, scale and position values
        modelPos = new Vector3f(0, 0, 0);
        modelAngle = new Vector3f(0, 0, 0);
        modelScale = new Vector3f(1, 1, 1);
        cameraPos = new Vector3f(0, 0, -1);
        cameraAngle = new Vector3f(0, 0, 0);

//        MyDebugger.printColorBuffer(colorVboId,quadRows,quadColumns);
//        MyDebugger.printPositionsBuffer(vboId,quadRows,quadColumns);
//        MyDebugger.printIndicesBuffer(vboiId,quadRows,quadColumns);



        this.exitOnGLError("setupQuads");
    }

    private void setupShaders() {
        //Load the vertex shader


        int vsId = 0;
        int fsId = 0;
        try {
//            ClassLoader.getSystemResourceAsStream("shaders/vertext.glsl")

//            vsId = GraphicsUtilities.loadShader("shaders/vertex.glsl", GL20.GL_VERTEX_SHADER);
            vsId = GraphicsUtilities.loadShader("shaders/vertex.glsl", GL20.GL_VERTEX_SHADER);
            this.exitOnGLError("GraphicsUtilities.loadShader");
            fsId = GraphicsUtilities.loadShader("shaders/fragments.glsl", GL20.GL_FRAGMENT_SHADER);
            this.exitOnGLError("GraphicsUtilities.loadShader");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        int vsId = GraphicsUtilities.loadShader("E:/sandbox/src/main/resources/shaders/vertex.glsl", GL20.GL_VERTEX_SHADER);
//        int fsId = GraphicsUtilities.loadShader("E:/sandbox/src/main/resources/shaders/fragments.glsl", GL20.GL_FRAGMENT_SHADER);

        //Create a new shader program that links both shaders
        pId = GL20.glCreateProgram();
        GL20.glAttachShader(pId, vsId);
        GL20.glAttachShader(pId, fsId);

        //Position information will be attribute 0
        GL20.glBindAttribLocation(pId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(pId, 1, "in_Color");
        //Textute information will be attribute 2
//        GL20.glBindAttribLocation(pId, 2, "in_TextureCoord");

        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);

        //Get matrices uniform locations
        projectionMatrixLocation = GL20.glGetUniformLocation(pId,"projectionMatrix");
        viewMatrixLocation = GL20.glGetUniformLocation(pId, "viewMatrix");
        modelMatrixLocation = GL20.glGetUniformLocation(pId, "modelMatrix");

        System.out.println("666VIEWMATRIX");
        System.out.println(projectionMatrixLocation);
        System.out.println(viewMatrixLocation);
        System.out.println(modelMatrixLocation );

        this.exitOnGLError("setupShaders");

    }

    private void setupMatrices() {
        // Setup projection matrix
        projectionMatrix = new Matrix4f();
        float fieldOfView = 60f;
        float aspectRatio = (float)WIDTH / (float)HEIGHT;
        float near_plane = 0.1f;
        float far_plane = 100f;

        float y_scale = MathUtilities.coTangent(MathUtilities.degreesToRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
        projectionMatrix.m33 = 0;

        // Setup view matrix
        viewMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();

        // Create a FloatBuffer with the proper size to store our matrices later
        matrix44Buffer = BufferUtils.createFloatBuffer(16);

        this.exitOnGLError("setupMatrices");
    }


    private void exitOnGLError(String setupOpenGL) {
        int errorValue = GL11.glGetError();

        if (errorValue != GL11.GL_NO_ERROR) {
            String errorString = GLU.gluErrorString(errorValue);
            System.out.println("ERROR IN:"+setupOpenGL+", MSG:"+errorString);
            if (org.lwjgl.opengl.Display.isCreated()) org.lwjgl.opengl.Display.destroy();

        }
    }


    private void setupTexture() {

//        //create frame and depth buffer
//        frameBufferId = EXTFramebufferObject.glGenFramebuffersEXT();
//        depthBufferId = EXTFramebufferObject.glGenRenderbuffersEXT();
//
//        //Bind frame buffer
//        EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,frameBufferId);
//
//        //create texture
//        int textureId = 0;
//        try {
//            textureId = GraphicsUtilities.loadPNGTexture("texture/alexsface.png",GL13.GL_TEXTURE0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //Attach texture to framebuffer
//        EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
//                EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT,
//                GL11.GL_TEXTURE_2D,textureId,0);
//
//        //bind depth buffer
//        EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT,depthBufferId);
//
//        //Alloc depth buffer space
//        EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT,
//                GL14.GL_DEPTH_COMPONENT24, textureWidth, textureHeight);
//
//        // bind to renderbuffer
//        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT
//                ,EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT
//                ,EXTFramebufferObject.GL_RENDERBUFFER_EXT
//                , depthBufferId);
//
//        EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,0);
//
//        if(EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT)
//                ==EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT) {
//            System.out.println("Framebuffer complete");
//        } else {
//            System.out.println("Framebuffer failed");
//        }




    }
    //*****Init

    //***** Private utility functions


}
