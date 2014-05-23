package org.supply.simulator.sandbox.quad;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 2/23/14
 * Time: 11:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class FancierQuadTest {


    // Entry point for the application
    public static void main(String[] args) {
        new FancyQuadTest();
    }


    // Setup variables
    private static final String WINDOW_TITLE = "The Quad: Moving";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double PI = 3.14159265358979323846;
    // Quad variables
//    private List<> vaoIds = 0;
//    private int vboIds = 0;
//    private int vboiIds = 0;
    List<QuadIds> quadIdsList;

    Integer vboiId = null;
    Integer vboId = null;
    Integer colorVboId = null;

    private int indicesCount = 0;
    // Shader variables
    private int pId = 0;
    // Texture variables
    private int[] texIds = new int[] {0, 0};
    private int textureSelector = 0;
    // Moving variables
    private int projectionMatrixLocation = 0;
    private int viewMatrixLocation = 0;
    private int modelMatrixLocation = 0;
    private Matrix4f projectionMatrix = null;
    private Matrix4f viewMatrix = null;
    private Matrix4f modelMatrix = null;
    private Vector3f modelPos = null;
    private Vector3f modelAngle = null;
    private Vector3f modelScale = null;
    private Vector3f cameraPos = null;
    private Vector3f cameraAngle = null;
    private FloatBuffer matrix44Buffer = null;
    private String errorMsg = "";

    private int quadColumns;
    private int quadRows;


    public FancierQuadTest(int quadRows, int quadColumns) {

        this.quadIdsList = new ArrayList<QuadIds>();
        this.quadRows = quadRows;
        this.quadColumns = quadColumns;

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
            System.out.println(errorMsg);
        }
//        this.setupTextures();
//        projectionMatrixLocation = GL20.glGetUniformLocation(pId,"projectionMatrix");
//        viewMatrixLocation = GL20.glGetUniformLocation(pId, "viewMatrix");
//        modelMatrixLocation = GL20.glGetUniformLocation(pId, "modelMatrix");

        this.setupMatrices();

        while (!Display.isCloseRequested()) {
            // Do a single loop (logic/render)
            try {
                this.loopCycle();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("loopCycle failed");
            }

            // Force a maximum FPS of about 60
            Display.sync(60);
            // Let the CPU synchronize with the GPU if GPU is tagging behind
            Display.update();
        }

        // Destroy OpenGL (Display)
        try {
            this.destroyOpenGL();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println("destroyOpenGl failed");
        }
    }

    private void setupMatrices() {
        // Setup projection matrix
        projectionMatrix = new Matrix4f();
        float fieldOfView = 60f;
        float aspectRatio = (float)WIDTH / (float)HEIGHT;
        float near_plane = 0.1f;
        float far_plane = 100f;

        float y_scale = this.coTangent(this.degreesToRadians(fieldOfView / 2f));
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

        // Setup model matrix
        modelMatrix = new Matrix4f();

        // Create a FloatBuffer with the proper size to store our matrices later
        matrix44Buffer = BufferUtils.createFloatBuffer(16);
    }

    private void setupTextures() throws Exception  {
        texIds[0] = this.loadPNGTexture("F:\\Users\\Brandon\\Desktop/grass.png", GL13.GL_TEXTURE0);
        texIds[1] = this.loadPNGTexture("F:\\Users\\Brandon\\Desktop/grass.png", GL13.GL_TEXTURE0);

        this.exitOnGLError("setupTexture");
    }

    private void setupOpenGL() throws Exception  {
        // Setup an OpenGL context with API version 3.2
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                    .withForwardCompatible(true)
                    .withProfileCore(true);

            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle(WINDOW_TITLE);
            Display.create(pixelFormat, contextAtrributes);

            GL11.glViewport(0, 0, WIDTH, HEIGHT);
        } catch (LWJGLException e) {
            e.printStackTrace();
            throw new Exception("you suck");
//            System.exit(-1);
        }

        // Setup an XNA like background color
        GL11.glClearColor(130f/255f, 208f/255f, 157f/255f, 0f);

        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, WIDTH, HEIGHT);

        this.exitOnGLError("setupOpenGL");
    }

    private void setupQuads() throws Exception {
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
            for(int i = 0; i < indices.length;i++) {
                indices[i] = values.get(i);
            }


//            byte[] indices = {
//                    0, 1, 2,
//                    2, 3, 0
//            };

//            System.out.println(Arrays.toString(indices));

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



        this.setupQuad(verticesFloatBuffer, verticesByteBuffer);
    }

    private void setupQuad(FloatBuffer verticesFloatBuffer, ByteBuffer verticesByteBuffer) throws Exception  {

//
//        int blackInt = black?0:1;
//
//
//        // We'll define our quad using 4 vertices of the custom 'TexturedVertex' class
//        VertexData v0 = new VertexData();
//        //top left
//        v0.setXYZ(x, y, z+length); v0.setRGB(blackInt,blackInt,blackInt); v0.setST(0, 0);
//        VertexData v1 = new VertexData();
//        //bottom left
//        v1.setXYZ(x, y-length, z); v1.setRGB(blackInt,blackInt,blackInt); v1.setST(0, 1);
//        VertexData v2 = new VertexData();
//        //bottom right
//        v2.setXYZ(x+length, y-length, z-length); v2.setRGB(blackInt,blackInt,blackInt); v2.setST(1, 1);
//        VertexData v3 = new VertexData();
//        //top right
//        v3.setXYZ(x+length, y, z); v3.setRGB(blackInt,blackInt,blackInt); v3.setST(1, 0);
//
//        VertexData[] vertices = new VertexData[]{v0, v1, v2, v3};
//
//        // Put each 'Vertex' in one FloatBuffer
//        ByteBuffer verticesByteBuffer = BufferUtils.createByteBuffer(vertices.length *
//                VertexData.stride);
//        FloatBuffer verticesFloatBuffer = verticesByteBuffer.asFloatBuffer();
//        for (int i = 0; i < vertices.length; i++) {
//            // Add position, color and texture floats to the buffer
//            verticesFloatBuffer.put(vertices[i].getElements());
//        }
//        verticesFloatBuffer.flip();
//
//
//        // OpenGL expects to draw vertices in counter clockwise order by default
//
//
//        // Create a new Vertex Array Object in memory and select it (bind)
//        int vaoId = GL30.glGenVertexArrays();
//        GL30.glBindVertexArray(vaoId);
//
//        // Create a new Vertex Buffer Object in memory and select it (bind)
//        if(vboId == null) {
//            vboId = GL15.glGenBuffers();
//        }
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
//        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesFloatBuffer, GL15.GL_STREAM_DRAW);
//
//        // Put the position coordinates in attribute list 0
//        GL20.glVertexAttribPointer(0, VertexData.positionElementCount, GL11.GL_FLOAT,
//                false, VertexData.stride, VertexData.positionByteOffset);
//        // Put the color components in attribute list 1
//        GL20.glVertexAttribPointer(1, VertexData.colorElementCount, GL11.GL_FLOAT,
//                false, VertexData.stride, VertexData.colorByteOffset);
//        // Put the texture coordinates in attribute list 2
////        GL20.glVertexAttribPointer(2, VertexData.textureElementCount, GL11.GL_FLOAT,
////                false, VertexData.stride, VertexData.textureByteOffset);
//
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//
//        // Deselect (bind to 0) the VAO
//        GL30.glBindVertexArray(0);
//
//        // Create a new VBO for the indices and select it (bind) - INDICES
//
//        if(vboiId == null) {
//
//            byte[] indices = {
//                    0, 1, 2,
//                    2, 3, 0
//            };
//            indicesCount = indices.length;
//            ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
//            indicesBuffer.put(indices);
//            indicesBuffer.flip();
//
//            vboiId = GL15.glGenBuffers();
//            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
//            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer,
//                    GL15.GL_STATIC_DRAW);
//            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
//        }
//
//        quadIdsList.add(new QuadIds(vaoId, vboId, vboiId));
//
//        // Set the default quad rotation, scale and position values
//        modelPos = new Vector3f(0, 0, 0);
//        modelAngle = new Vector3f(0, 0, 0);
//        modelScale = new Vector3f(1, 1, 1);
//        cameraPos = new Vector3f(0, 0, -1);
//
//        this.exitOnGLError("setupQuad");






        // OpenGL expects to draw vertices in counter clockwise order by default


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

        this.exitOnGLError("setupQuad");

    }

    private void setupShaders() throws Exception  {
        //Load the vertex shader
        errorMsg = "setupShaders start";

//        int vsId = this.loadShader("C:/Users/Alex/IdeaProjects/game/Sandbox/src/main/resources/shaders/vertex.glsl", GL20.GL_VERTEX_SHADER);
//        int fsId = this.loadShader("C:/Users/Alex/IdeaProjects/game/Sandbox/src/main/resources/shaders/fragments.glsl", GL20.GL_FRAGMENT_SHADER);
        int vsId = this.loadShader("E:/sandbox/src/main/resources/shaders/vertex.glsl", GL20.GL_VERTEX_SHADER);
        int fsId = this.loadShader("E:/sandbox/src/main/resources/shaders/fragments.glsl", GL20.GL_FRAGMENT_SHADER);

        errorMsg = "setupShaders create pgm";
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

        errorMsg = "setupShaders get locations";
        //Get matrices uniform locations
        projectionMatrixLocation = GL20.glGetUniformLocation(pId,"projectionMatrix");
        viewMatrixLocation = GL20.glGetUniformLocation(pId, "viewMatrix");
        modelMatrixLocation = GL20.glGetUniformLocation(pId, "modelMatrix");

        System.out.println(projectionMatrixLocation);
        System.out.println(viewMatrixLocation);
        System.out.println(modelMatrixLocation );

        this.exitOnGLError("setupShaders");
    }

    private void logicCycle() throws Exception  {
        //-- Input processing
        float rotationDelta = 3f;
        float scaleDelta = 0.1f;
        float posDelta = 0.02f;
        float mouseDeltaThresh = 0.000000001f;
        Keyboard.enableRepeatEvents(true);

        Vector3f scaleAddResolution = new Vector3f(scaleDelta, scaleDelta, scaleDelta);
        Vector3f scaleMinusResolution = new Vector3f(-scaleDelta, -scaleDelta,
                -scaleDelta);
//

        while(Keyboard.next()/*|| Mouse.isInsideWindow()*/) {
            // Only listen to events where the key was pressed (down event)
            if (!Keyboard.getEventKeyState()) continue;

            // Switch textures depending on the key released
            switch (Keyboard.getEventKey()) {
                case Keyboard.KEY_1:
                    textureSelector = 0;
                    break;
                case Keyboard.KEY_2:
                    textureSelector = 1;
                    break;
            }

            // Change model scale, rotation and translation values
//            switch (Keyboard.getEventKey()) {
//                // Move
//                case Keyboard.KEY_UP:
//                    cameraPos.y += posDelta;
//                    break;
//                case Keyboard.KEY_DOWN:
//                    cameraPos.y -= posDelta;
//                    break;
//                case Keyboard.KEY_L:
//                    cameraPos.x += posDelta;
//                    break;
//                case Keyboard.KEY_K:
//                    cameraPos.x -= posDelta;
//                    break;
//                // Scale
//                case Keyboard.KEY_P:
//                    Vector3f.add(modelScale, scaleAddResolution, modelScale);
//                    break;
//                case Keyboard.KEY_M:
//                    Vector3f.add(modelScale, scaleMinusResolution, modelScale);
//                    break;
//                // Rotation
//                case Keyboard.KEY_LEFT:
//                    modelAngle.z += rotationDelta;
//                    break;
//                case Keyboard.KEY_RIGHT:
//                    modelAngle.z -= rotationDelta;
//                    break;
//                case Keyboard.KEY_Y:
//                    cameraAngle.y += posDelta;
//                    break;
//                case Keyboard.KEY_G:
//                    cameraAngle.y -= posDelta;
//                    break;
//                case Keyboard.KEY_T:
//                    cameraAngle.x += posDelta;
//                    break;
//                case Keyboard.KEY_F:
//                    cameraAngle.x -= posDelta;
//                    break;
//            }

            switch (Keyboard.getEventKey()) {
                // Move

                case Keyboard.KEY_W:
                    cameraPos.z += posDelta;
                    break;
                case Keyboard.KEY_S:
                    cameraPos.z -= posDelta;
                    break;

                case Keyboard.KEY_A:
                    cameraPos.x += posDelta;
                    break;
                case Keyboard.KEY_D:
                    cameraPos.x -= posDelta;
                    break;


                case Keyboard.KEY_UP:
                    cameraAngle.x += posDelta;
                    break;
                case Keyboard.KEY_DOWN:
                    cameraAngle.x -= posDelta;
                    break;

                // Scale
                case Keyboard.KEY_P:
                    Vector3f.add(modelScale, scaleAddResolution, modelScale);
                    break;
                case Keyboard.KEY_M:
                    Vector3f.add(modelScale, scaleMinusResolution, modelScale);
                    break;
                // Rotation
                case Keyboard.KEY_LEFT:
                    modelAngle.z += rotationDelta;
                    break;
                case Keyboard.KEY_RIGHT:
                    modelAngle.z -= rotationDelta;
                    break;
                case Keyboard.KEY_Y:
                    cameraAngle.y += posDelta;
                    break;
                case Keyboard.KEY_G:
                    cameraAngle.y -= posDelta;
                    break;
                case Keyboard.KEY_T:
                    cameraAngle.x += posDelta;
                    break;
                case Keyboard.KEY_F:
                    cameraAngle.x -= posDelta;
                    break;
            }


            if (Mouse.getDX()>mouseDeltaThresh) {
                System.out.println("X:"+Mouse.getDX());
                cameraAngle.x += rotationDelta;
            } else if (Mouse.getDX()<-mouseDeltaThresh) {
                System.out.println("X:"+Mouse.getDX());
                cameraAngle.x -= rotationDelta;
            }
            if (Mouse.getDY()>mouseDeltaThresh) {
                System.out.println("Y:"+Mouse.getDY());
                cameraAngle.y += rotationDelta;
            } else if (Mouse.getDY()<-mouseDeltaThresh) {
                System.out.println("Y:"+Mouse.getDY());
                cameraAngle.y -= rotationDelta;
            }

        }

        //-- Update matrices
        // Reset view and model matrices
        viewMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();

        // Translate camera
        Matrix4f.translate(cameraPos, viewMatrix, viewMatrix);
        Matrix4f.rotate(cameraAngle.z, new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
        Matrix4f.rotate(cameraAngle.y, new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate(cameraAngle.x, new Vector3f(1, 0, 0), viewMatrix, viewMatrix);

        // Scale, translate and rotate model
        Matrix4f.scale(modelScale, modelMatrix, modelMatrix);
        Matrix4f.translate(modelPos, modelMatrix, modelMatrix);
        Matrix4f.rotate(this.degreesToRadians(modelAngle.z), new Vector3f(0, 0, 1),
                modelMatrix, modelMatrix);
        Matrix4f.rotate(this.degreesToRadians(modelAngle.y), new Vector3f(0, 1, 0),
                modelMatrix, modelMatrix);
        Matrix4f.rotate(this.degreesToRadians(modelAngle.x), new Vector3f(1, 0, 0),
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


        this.exitOnGLError("logicCycle");
    }

    private void renderCycle() throws Exception  {


        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL20.glUseProgram(pId);

        // Display Text

//        DrawString.drawString("you suck",5,5);
        // Bind the texture
//        GL13.glActiveTexture(GL13.GL_TEXTURE0);
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texIds[textureSelector]);
        for(QuadIds ids: quadIdsList) {
            int vaoId = ids.vaoId;
            int vboiId = ids.vboiId;
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

        GL20.glUseProgram(0);

        this.exitOnGLError("renderCycle");
    }

    private void loopCycle() throws Exception  {
//        System.out.println("HI MOM");
        // Update logic
        this.logicCycle();
        // Update rendered frame
        this.renderCycle();

        this.exitOnGLError("loopCycle");
    }

    private void destroyOpenGL() throws Exception  {
        // Delete the texture
        GL11.glDeleteTextures(texIds[0]);
        GL11.glDeleteTextures(texIds[1]);

        // Delete the shaders
        GL20.glUseProgram(0);
        GL20.glDeleteProgram(pId);

        for(QuadIds ids: quadIdsList) {
            int vaoId = ids.vaoId;
            int vboId = ids.vboId;
            int vboiId = ids.vboiId;
            int colorVboId = ids.colorVboId;

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

    private int loadShader(String filename, int type) throws Exception  {
        errorMsg = "    loadShader start";
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;


        int lineNum = 0;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
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

        this.exitOnGLError("loadShader");

        return shaderID;
    }

    private int loadPNGTexture(String filename, int textureUnit) throws Exception {
        ByteBuffer buf = null;
        int tWidth = 0;
        int tHeight = 0;

        try {
            // Open the PNG file as an InputStream
            InputStream in = new FileInputStream(filename);
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

        this.exitOnGLError("loadPNGTexture");

        return texId;
    }

    private float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }

    private float degreesToRadians(float degrees) {
        return degrees * (float)(PI / 180d);
    }

    private void exitOnGLError(String errorMessage) throws Exception {
        int errorValue = GL11.glGetError();

        if (errorValue != GL11.GL_NO_ERROR) {
            String errorString = GLU.gluErrorString(errorValue);
            System.err.println("ERROR - " + errorMessage + ": " + errorString);

            if (Display.isCreated()) Display.destroy();
//            System.exit(-1);
            throw new Exception("you suck");
        }
    }

    protected class QuadIds {
        private Integer vaoId, vboId, vboiId, colorVboId;

        public QuadIds(Integer vaoId, Integer vboId, Integer vboiId, Integer colorVboId) {
            this.vaoId = vaoId;
            this.vboId = vboId;
            this.vboiId = vboiId;
            this.colorVboId = colorVboId;
        }

        int getVaoId() {
            return vaoId;
        }

        int getVboId() {
            return vboId;
        }

        int getVboiId() {
            return vboiId;
        }

        int getColorVboId() {
            return colorVboId;
        }
    }

}
