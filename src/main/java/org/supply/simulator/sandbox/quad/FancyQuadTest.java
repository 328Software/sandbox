package org.supply.simulator.sandbox.quad;

import org.lwjgl.util.vector.Matrix4f;

public class FancyQuadTest {
//    // Entry point for the application
//    public static void main(String[] args) {
//        new FancyQuadTest();
//    }
//
//
//    // Setup variables
//    private final String WINDOW_TITLE = "The Quad: Moving";
//    private final int WIDTH = 800;
//    private final int HEIGHT = 600;
//    private final double PI = 3.14159265358979323846;
//    // Quad variables
//    private int vaoId = 0;
//    private int vboId = 0;
//    private int vboiId = 0;
//    private int indicesCount = 0;
//    private VertexData[] vertices = null;
//    private ByteBuffer verticesByteBuffer = null;
//    // Shader variables
//    private int pId = 0;
//    // Texture variables
//    private int[] texIds = new int[] {0, 0};
//    private int textureSelector = 0;
//    // Moving variables
//    private int projectionMatrixLocation = 0;
//    private int viewMatrixLocation = 0;
//    private int modelMatrixLocation = 0;
//    private Matrix4f projectionMatrix = null;
//    private Matrix4f viewMatrix = null;
//    private Matrix4f modelMatrix = null;
//    private Vector3f modelPos = null;
//    private Vector3f modelAngle = null;
//    private Vector3f modelScale = null;
//    private Vector3f cameraPos = null;
//    private FloatBuffer matrix44Buffer = null;
//
//    public FancyQuadTest() {
//        try {
//        // Initialize OpenGL (Display)
//        this.setupOpenGL();
//
//        this.setupQuad();
//        this.setupShaders();
////        this.setupTextures();
////        projectionMatrixLocation = GL20.glGetUniformLocation(pId,"projectionMatrix");
////        viewMatrixLocation = GL20.glGetUniformLocation(pId, "viewMatrix");
////        modelMatrixLocation = GL20.glGetUniformLocation(pId, "modelMatrix");
//
//        this.setupMatrices();
//
//        while (!Display.isCloseRequested()) {
//            // Do a single loop (logic/render)
//            this.loopCycle();
//
//            // Force a maximum FPS of about 60
//            Display.sync(60);
//            // Let the CPU synchronize with the GPU if GPU is tagging behind
//            Display.update();
//        }
//
//        // Destroy OpenGL (Display)
//        this.destroyOpenGL();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("you suck loser");
//        }
//    }
//
//    private void setupMatrices() {
//        // Setup projection matrix
//        projectionMatrix = new Matrix4f();
//        float fieldOfView = 60f;
//        float aspectRatio = (float)WIDTH / (float)HEIGHT;
//        float near_plane = 0.1f;
//        float far_plane = 100f;
//
//        float y_scale = this.coTangent(this.degreesToRadians(fieldOfView / 2f));
//        float x_scale = y_scale / aspectRatio;
//        float frustum_length = far_plane - near_plane;
//
//        projectionMatrix.m00 = x_scale;
//        projectionMatrix.m11 = y_scale;
//        projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
//        projectionMatrix.m23 = -1;
//        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
//        projectionMatrix.m33 = 0;
//
//        // Setup view matrix
//        viewMatrix = new Matrix4f();
//
//        // Setup model matrix
//        modelMatrix = new Matrix4f();
//
//        // Create a FloatBuffer with the proper size to store our matrices later
//        matrix44Buffer = BufferUtils.createFloatBuffer(16);
//    }
//
//    private void setupOpenGL() throws Exception  {
//        // Setup an OpenGL context with API version 3.2
//        try {
//            PixelFormat pixelFormat = new PixelFormat();
//            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
//                    .withForwardCompatible(true)
//                    .withProfileCore(true);
//
//            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
//            Display.setTitle(WINDOW_TITLE);
//            Display.create(pixelFormat, contextAtrributes);
//
//            GL11.glViewport(0, 0, WIDTH, HEIGHT);
//        } catch (LWJGLException e) {
//            e.printStackTrace();
//            throw new Exception("you suck");
////
//        }
//
//        // Setup an XNA like background color
//        GL11.glClearColor(130f/255f, 208f/255f, 157f/255f, 0f);
//
//        // Map the internal OpenGL coordinate system to the entire screen
//        GL11.glViewport(0, 0, WIDTH, HEIGHT);
//
//        this.exitOnGLError("setupOpenGL");
//    }
//
//    private void setupQuad() throws Exception  {
//        // We'll define our quad using 4 vertices of the custom 'TexturedVertex' class
//        VertexData v0 = new VertexData();
//        v0.setXYZ(-0.5f, 0.5f, 0); v0.setRGB(1, 0, 0); v0.setST(0, 0);
//        VertexData v1 = new VertexData();
//        v1.setXYZ(-0.5f, -0.5f, 0); v1.setRGB(0, 1, 0); v1.setST(0, 1);
//        VertexData v2 = new VertexData();
//        v2.setXYZ(0.5f, -0.5f, 0); v2.setRGB(0, 0, 1); v2.setST(1, 1);
//        VertexData v3 = new VertexData();
//        v3.setXYZ(0.5f, 0.5f, 0); v3.setRGB(1, 1, 1); v3.setST(1, 0);
//
//        vertices = new VertexData[] {v0, v1, v2, v3};
//
//        // Put each 'Vertex' in one FloatBuffer
//        verticesByteBuffer = BufferUtils.createByteBuffer(vertices.length *
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
//        byte[] indices = {
//                0, 1, 2,
//                2, 3, 0
//        };
//        indicesCount = indices.length;
//        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
//        indicesBuffer.put(indices);
//        indicesBuffer.flip();
//
//        // Create a new Vertex Array Object in memory and select it (bind)
//        vaoId = GL30.glGenVertexArrays();
//        GL30.glBindVertexArray(vaoId);
//
//        // Create a new Vertex Buffer Object in memory and select it (bind)
//        vboId = GL15.glGenBuffers();
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
//        GL20.glVertexAttribPointer(2, VertexData.textureElementCount, GL11.GL_FLOAT,
//                false, VertexData.stride, VertexData.textureByteOffset);
//
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//
//        // Deselect (bind to 0) the VAO
//        GL30.glBindVertexArray(0);
//
//        // Create a new VBO for the indices and select it (bind) - INDICES
//        vboiId = GL15.glGenBuffers();
//        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
//        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer,
//                GL15.GL_STATIC_DRAW);
//        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
//
//        // Set the default quad rotation, scale and position values
//        modelPos = new Vector3f(0, 0, 0);
//        modelAngle = new Vector3f(0, 0, 0);
//        modelScale = new Vector3f(1, 1, 1);
//        cameraPos = new Vector3f(0, 0, -1);
//
//        this.exitOnGLError("setupQuad");
//    }
//
//    private void setupShaders() throws Exception  {
//        // Load the vertex shader
////        int vsId = this.loadShader("assets/shaders/moving/vertex.glsl", GL20.GL_VERTEX_SHADER);
//        // Load the fragment shader
////        int fsId = this.loadShader("assets/shaders/moving/fragment.glsl", GL20.GL_FRAGMENT_SHADER);
//
//        // Create a new shader program that links both shaders
////        pId = GL20.glCreateProgram();
////        System.out.println(pId);
////        GL20.glAttachShader(pId, vsId);
////        GL20.glAttachShader(pId, fsId);
//
//        // Position information will be attribute 0
////        GL20.glBindAttribLocation(pId, 0, "in_Position");
//        // Color information will be attribute 1
////        GL20.glBindAttribLocation(pId, 1, "in_Color");
//        // Textute information will be attribute 2
////        GL20.glBindAttribLocation(pId, 2, "in_TextureCoord");
//
////        GL20.glLinkProgram(pId);
////        GL20.glValidateProgram(pId);
//
//        // Get matrices uniform locations
//        projectionMatrixLocation = GL20.glGetUniformLocation(0,"projectionMatrix");
//        viewMatrixLocation = GL20.glGetUniformLocation(0, "viewMatrix");
//        modelMatrixLocation = GL20.glGetUniformLocation(0, "modelMatrix");
//
//        System.out.println(projectionMatrixLocation);
//        System.out.println(viewMatrixLocation);
//        System.out.println("modelMatrixLocation = " + modelMatrixLocation);
////
//
//        this.exitOnGLError("setupShaders");
//    }
//
//    private void logicCycle() throws Exception  {
//        //-- Input processing
//        float rotationDelta = 15f;
//        float scaleDelta = 0.1f;
//        float posDelta = 0.4f;
//        Vector3f scaleAddResolution = new Vector3f(scaleDelta, scaleDelta, scaleDelta);
//        Vector3f scaleMinusResolution = new Vector3f(-scaleDelta, -scaleDelta,
//                -scaleDelta);
////
//        while(Keyboard.next()) {
//            // Only listen to events where the key was pressed (down event)
//            if (!Keyboard.getEventKeyState()) continue;
//
//            // Switch textures depending on the key released
//            switch (Keyboard.getEventKey()) {
//                case Keyboard.KEY_1:
//                    textureSelector = 0;
//                    break;
//                case Keyboard.KEY_2:
//                    textureSelector = 1;
//                    break;
//            }
//
//            // Change model scale, rotation and translation values
//            switch (Keyboard.getEventKey()) {
//                // Move
//                case Keyboard.KEY_UP:
//                    modelPos.y += posDelta;
//                    System.out.println("UP PLEASE " + modelPos.y);
//                    break;
//                case Keyboard.KEY_DOWN:
//                    modelPos.y -= posDelta;
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
//            }
//        }
//
////        GL20.glUseProgram(0);
//
//        //-- Update matrices
//        // Reset view and model matrices
//        viewMatrix = new Matrix4f();
//        modelMatrix = new Matrix4f();
//
//        // Translate camera
//        Matrix4f.translate(cameraPos, viewMatrix, viewMatrix);
//
//        // Scale, translate and rotate model
//        Matrix4f.scale(modelScale, modelMatrix, modelMatrix);
//        Matrix4f.translate(modelPos, modelMatrix, modelMatrix);
//        Matrix4f.rotate(this.degreesToRadians(modelAngle.z), new Vector3f(0, 0, 1),
//                modelMatrix, modelMatrix);
//        Matrix4f.rotate(this.degreesToRadians(modelAngle.y), new Vector3f(0, 1, 0),
//                modelMatrix, modelMatrix);
//        Matrix4f.rotate(this.degreesToRadians(modelAngle.x), new Vector3f(1, 0, 0),
//                modelMatrix, modelMatrix);
//
//        // Upload matrices to the uniform variables
//        GL20.glUseProgram(pId);
//
//
//        projectionMatrix.store(matrix44Buffer); matrix44Buffer.flip();
//        GL20.glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
//        viewMatrix.store(matrix44Buffer); matrix44Buffer.flip();
//        GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);
//        modelMatrix.store(matrix44Buffer); matrix44Buffer.flip();
//        GL20.glUniformMatrix4(modelMatrixLocation, false, matrix44Buffer);
//
//        GL20.glUseProgram(0);
//
//
//        this.exitOnGLError("logicCycle");
//    }
//
//    private void renderCycle() throws Exception  {
//        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
//
//        GL20.glUseProgram(pId);
//
//        // Bind the texture
//        GL13.glActiveTexture(GL13.GL_TEXTURE0);
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texIds[textureSelector]);
//
//        // Bind to the VAO that has all the information about the vertices
//        GL30.glBindVertexArray(vaoId);
//        GL20.glEnableVertexAttribArray(0);
//        GL20.glEnableVertexAttribArray(1);
//        GL20.glEnableVertexAttribArray(2);
//
//        // Bind to the index VBO that has all the information about the order of the vertices
//        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
//
//        // Draw the vertices
//        GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);
//
//        // Put everything back to default (deselect)
//        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
//        GL20.glDisableVertexAttribArray(0);
//        GL20.glDisableVertexAttribArray(1);
//        GL20.glDisableVertexAttribArray(2);
//        GL30.glBindVertexArray(0);
//
//        GL20.glUseProgram(0);
//
//        this.exitOnGLError("renderCycle");
//    }
//
//    private void loopCycle() throws Exception  {
////        System.out.println("HI MOM");
//        // Update logic
//        this.logicCycle();
//        // Update rendered frame
//        this.renderCycle();
//
//        this.exitOnGLError("loopCycle");
//    }
//
//    private void destroyOpenGL() throws Exception  {
//        // Delete the texture
//        GL11.glDeleteTextures(texIds[0]);
//        GL11.glDeleteTextures(texIds[1]);
//
//        // Delete the shaders
//        GL20.glUseProgram(0);
//        GL20.glDeleteProgram(pId);
//
//        // Select the VAO
//        GL30.glBindVertexArray(vaoId);
//
//        // Disable the VBO index from the VAO attributes list
//        GL20.glDisableVertexAttribArray(0);
//        GL20.glDisableVertexAttribArray(1);
//
//        // Delete the vertex VBO
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//        GL15.glDeleteBuffers(vboId);
//
//        // Delete the index VBO
//        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
//        GL15.glDeleteBuffers(vboiId);
//
//        // Delete the VAO
//        GL30.glBindVertexArray(0);
//        GL30.glDeleteVertexArrays(vaoId);
//
//        this.exitOnGLError("destroyOpenGL");
//
//        Display.destroy();
//    }
//
//    private float coTangent(float angle) {
//        return (float)(1f / Math.tan(angle));
//    }
//
//    private float degreesToRadians(float degrees) {
//        return degrees * (float)(PI / 180d);
//    }
//
//    private void exitOnGLError(String errorMessage) throws Exception {
//        int errorValue = GL11.glGetError();
//
//        if (errorValue != GL11.GL_NO_ERROR) {
//
//            System.out.println("ERROR VALUE IS " + errorValue);
//
//            String errorString = GLU.gluErrorString(errorValue);
//            System.err.println("ERROR - " + errorMessage + ": " + errorString);
//
//            if (Display.isCreated()) Display.destroy();
////
//            throw new Exception("you suck");
//        }
//    }
}
