package org.supply.simulator.sandbox;

import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.Display;
import org.supply.simulator.display.assetengine.shader.ShaderHandle;
import org.supply.simulator.display.assetengine.shader.ShaderProgramType;
import org.supply.simulator.display.window.impl.BasicPlayWindow;
import org.supply.simulator.sandbox.mockdisplay.MockCamera;
import org.supply.simulator.sandbox.mockdisplay.MockChunkManager;
import org.supply.simulator.sandbox.mockdisplay.MockDisplayCore;
import org.supply.simulator.sandbox.mockdisplay.MockShaderEngine;

/**
 * Created by Alex on 6/29/2014.
 */
public class PlayWindowWithHUDTest {

    private BasicPlayWindow window;


    @Before
    public void create() {
//        MockDisplayCore.build("PlayWindowWithHUDTest");
//
//        MockShaderEngine<ShaderProgramType,ShaderHandle> shaderEngine = new MockShaderEngine();
//        shaderEngine.set(ShaderProgramType.PLAY,"shaders/vertex.glsl");
//        shaderEngine.set(ShaderProgramType.PLAY,"shaders/fragments.glsl");
//        shaderEngine.set(ShaderProgramType.MENU,"shaders/vertexWithTexture.glsl");
//        shaderEngine.set(ShaderProgramType.MENU,"shaders/fragmentsWithTexture.glsl");
//
//        window = new BasicPlayWindow();
//        window.setShaderEngine(shaderEngine);
//        window.setCamera(new MockCamera());
//        window.setChunkManager(new MockChunkManager<>());
//
//        window.build();

    }

    @Test
    public void render() {
//        while (!Display.isCloseRequested()) {
//            window.render();
//
//            MockDisplayCore.render();
//        }
//
//        window.destroy();
//        MockDisplayCore.destroy();
    }
}
