package org.supply.simulator.sandbox.mockdisplay;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.supply.simulator.display.assetengine.shader.*;
import org.supply.simulator.display.assetengine.shader.impl.BasicShaderHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Alex on 7/14/2014.
 */
public class MockShaderEngine<K extends ShaderProgramType, V extends ShaderHandle>
        extends AbstractShaderEngine<ShaderProgramType,ShaderHandle>
        implements ShaderEngine<ShaderProgramType,ShaderHandle> {

    private String[] vertexShader;
    private String[] tessellationShader;
    private String[] geometryShader;
    private String[] fragmentShader;
    private String[] computeShader;

    public MockShaderEngine () {

        vertexShader = new String [ShaderProgramType.COUNT];
        tessellationShader= new String [ShaderProgramType.COUNT];
        geometryShader= new String [ShaderProgramType.COUNT];
        fragmentShader= new String [ShaderProgramType.COUNT];
        computeShader= new String [ShaderProgramType.COUNT];


    }

    @Override
    protected void createProgram(ShaderProgramType type) {

        int vertexId = -1;
        int fragmentId = -1;
        int programId = -1;

        if (vertexShader[type.value()]!=null) {
            vertexId = loadShader(vertexShader[type.value()], GL20.GL_VERTEX_SHADER);
        } else {
            logger.error("Failed to load vertex shader");
            System.exit(-1);
        }

        if (fragmentShader[type.value()]!=null) {
            fragmentId=loadShader(fragmentShader[type.value()], GL20.GL_FRAGMENT_SHADER);
        } else {
            logger.error("Failed to load fragment shader");
            System.exit(-1);
        }

        programId = GL20.glCreateProgram();


        GL20.glAttachShader(programId,vertexId);
        GL20.glAttachShader(programId,fragmentId);

        //Position information will be attribute 0
        GL20.glBindAttribLocation(programId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(programId, 1, "in_Color");

        //TODO ADDED FOR TEXTURE
        GL20.glBindAttribLocation(programId, 2, "in_TextureCoord");

        if (geometryShader[type.value()]!=null) {

        }

        if (tessellationShader[type.value()]!=null) {

        }

        if (computeShader[type.value()]!=null) {

        }


        GL20.glLinkProgram(programId);
        GL20.glValidateProgram(programId);

        BasicShaderHandle data = new BasicShaderHandle();
        data.setProjectionMatrixLocation(GL20.glGetUniformLocation(programId,"projectionMatrix"));
        data.setViewMatrixLocation(GL20.glGetUniformLocation(programId, "viewMatrix"));
        data.setModelMatrixLocation(GL20.glGetUniformLocation(programId, "modelMatrix"));
        data.setProgramId(programId);

        shaderMap.put(type,data);
//        projectionMatrixLocation = GL20.glGetUniformLocation(programId,"projectionMatrix");
//        viewMatrixLocation[type.value()]       = GL20.glGetUniformLocation(programId, "viewMatrix");
//        modelMatrixLocation[type.value()]      = GL20.glGetUniformLocation(programId, "modelMatrix");

    }

    private int loadShader(String fileName, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;


        int lineNum = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName)));
        String line;


        try {
            while ((line = reader.readLine()) != null) {
                lineNum++;
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            logger.error("ERROR READING SHADER FILE " + fileName);
            e.printStackTrace();
        }


        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            logger.error("Could not compile shader.");
        }

        return shaderID;
    }

    public void set(ShaderProgramType pType, String fileName) {
        if(fileName.contains(ShaderType.VERTEX.toString())) {
            this.vertexShader[pType.value()] = fileName;
        } else if(fileName.contains(ShaderType.FRAGMENT.toString())){
            this.fragmentShader[pType.value()] = fileName;
        }
    }
}
