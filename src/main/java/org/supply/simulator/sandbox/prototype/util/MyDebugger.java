package org.supply.simulator.sandbox.prototype.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.supply.simulator.sandbox.prototype.display.VertexData;

import java.nio.ByteBuffer;

/**
 * Created by Alex on 7/6/2014.
 */
public class MyDebugger {
//    protected static Logger logger = LogManager.getLogger(MyDebugger.class);
//
//    public static void printOpenGLError() {
//        switch(GL11.glGetError()) {
//            case GL11.GL_NO_ERROR: logger.info("OpenGl ERROR: GL_NO_ERROR");
//                break;
//            case GL11.GL_INVALID_ENUM: logger.error("OpenGl ERROR: GL_INVALID_ENUM");
//                break;
//            case GL11.GL_INVALID_VALUE: logger.error("OpenGl ERROR: GL_INVALID_VALUE");
//                break;
//            case GL11.GL_INVALID_OPERATION: logger.error("OpenGl ERROR: GL_INVALID_OPERATION");
//                break;
//            case GL11.GL_OUT_OF_MEMORY: logger.error("OpenGl ERROR: GL_OUT_OF_MEMORY");
//                break;
//            case GL11.GL_STACK_UNDERFLOW: logger.error("OpenGl ERROR: GL_STACK_UNDERFLOW");
//                break;
//            case GL11.GL_STACK_OVERFLOW: logger.error("OpenGl ERROR: GL_STACK_OVERFLOW");
//                break;
//        }
//    }
    public static void getFragmentShaderPositionValue(int rows, int cols) {
        ByteBuffer bytes  = BufferUtils.createByteBuffer(4 * rows * cols *
                VertexData.colorByteCount);
        // GL31.glGetUniformBlockIndex(ShaderProgramType.PLAY,"debugPosition");/

    }

    public static void printColorBuffer(int bufferId,int rows, int cols) {
        ByteBuffer bytes  = BufferUtils.createByteBuffer(4 * rows * cols *
                VertexData.colorByteCount);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId);

        bytes = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER,GL15.GL_READ_ONLY,
                4 * rows * cols * VertexData.colorByteCount, bytes);
        printByteBuffer(rows, cols, bytes, 4);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public static void printPositionsBuffer(int bufferId,int rows, int cols) {
        ByteBuffer bytes  = BufferUtils.createByteBuffer(4 * rows * cols *
                VertexData.colorByteCount);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId);
        bytes = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER,GL15.GL_READ_ONLY,
                4 * rows * cols * VertexData.positionBytesCount, bytes);

        printFloatBuffer(rows, cols, bytes, 1);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

    }

    public static void printIndicesBuffer(int bufferId,int rows, int cols) {
        ByteBuffer bytes  = BufferUtils.createByteBuffer(4 * rows * cols *
                VertexData.colorByteCount);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, bufferId);
        bytes = GL15.glMapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,GL15.GL_READ_ONLY,
                6*4 * rows * cols * 4, bytes);
        printIntBuffer(rows, cols, bytes, 6);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

    }


    private static void printFloatBuffer(int rows, int columns, ByteBuffer buf, int stride) {
        for (int i=0; i<rows;i++) {
            for (int j=0; j<columns;j++) {
                System.out.print("{ ");
                for (int k = 0; k<stride;k++) {
                    System.out.print(buf.getFloat()+" ");
                }
                System.out.print("}");
            }
            System.out.println();
        }
    }

    private static void printByteBuffer(int rows, int columns, ByteBuffer buf, int stride) {
        for (int i=0; i<rows;i++) {
            for (int j=0; j<columns;j++) {
                System.out.print("{ ");
                for (int k = 0; k<stride;k++) {
                    System.out.print(buf.get() + " ");
                }
                System.out.print("}");
            }
            System.out.println();
        }
    }

    private static void printIntBuffer(int rows, int columns, ByteBuffer buf, int stride) {
        for (int i=0; i<rows;i++) {
            for (int j=0; j<columns;j++) {
                System.out.print("{ ");
                for (int k = 0; k<stride;k++) {
                    System.out.print(buf.getInt() + " ");
                }
                System.out.print("}");
            }
            System.out.println();
        }
    }

}
