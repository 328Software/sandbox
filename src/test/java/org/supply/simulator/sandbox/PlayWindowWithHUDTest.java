package org.supply.simulator.sandbox;

import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.Display;
import org.supply.simulator.display.shader.ShaderProgramType;
import org.supply.simulator.display.shader.ShaderType;
import org.supply.simulator.display.shader.impl.BasicShaderEngine;
import org.supply.simulator.display.window.impl.BasicPlayWindow;
import org.supply.simulator.sandbox.mockdisplay.MockCamera;
import org.supply.simulator.sandbox.mockdisplay.MockChunkManager;
import org.supply.simulator.sandbox.mockdisplay.MockDisplayCore;

/**
 * Created by Alex on 6/29/2014.
 */
public class PlayWindowWithHUDTest {

    private BasicPlayWindow window;

    @Before
    public void create() {
        MockDisplayCore.build("PlayWindowWithHUDTest");

        BasicShaderEngine shaderEngine = new BasicShaderEngine();
        shaderEngine.setShaderFile("shaders/vertex.glsl", ShaderType.VERTEX, ShaderProgramType.PLAY);
        shaderEngine.setShaderFile("shaders/fragments.glsl", ShaderType.FRAGMENT,ShaderProgramType.PLAY);
        shaderEngine.setShaderFile("shaders/vertexWithTexture.glsl", ShaderType.VERTEX, ShaderProgramType.MENU);
        shaderEngine.setShaderFile("shaders/fragmentsWithTexture.glsl", ShaderType.FRAGMENT,ShaderProgramType.MENU);

        window = new BasicPlayWindow();
        window.setShaderEngine(shaderEngine);
        window.setCamera(new MockCamera());
        window.setChunkManager(new MockChunkManager<>());

        window.build();

    }

    @Test
    public void render() {
        while (!Display.isCloseRequested()) {
            window.render();

            MockDisplayCore.render();
        }

        window.destroy();
        MockDisplayCore.destroy();
    }
}
