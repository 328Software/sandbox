package org.supply.simulator.sandbox.mockdisplay;

import org.supply.simulator.display.assetengine.indices.AbstractChunkIndexEngine;
import org.supply.simulator.display.assetengine.indices.ChunkIndexData;
import org.supply.simulator.display.assetengine.indices.ChunkIndexHandle;
import org.supply.simulator.display.assetengine.indices.ChunkType;
import org.supply.simulator.display.assetengine.indices.impl.BasicChunkIndexData;
import org.supply.simulator.display.assetengine.indices.impl.BasicChunkIndexHandle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 7/14/2014.
 */
public class MockChunkIndexEngine<K,V extends ChunkIndexHandle> extends AbstractChunkIndexEngine<K,ChunkIndexHandle>{

    @Override
    protected ChunkIndexData getIndicesBufferData() {
        ChunkIndexData<List<Integer>> data = new BasicChunkIndexData<>();

        data.setData(createTriangleIndicesData(ChunkType.MEDIUM_T.rows(), ChunkType.MEDIUM_T.columns()));
//        switch (key.glRenderType()) {
//            case GL11.GL_TRIANGLES:indicesBufferData = this.createTriangleIndicesData(key.rows(), key.columns());
//                break;
//            case GL11.GL_QUADS://indicesBufferData = this.createQuadIndicesData(key.rows(), key.columns());
//                break;
//
//        }

        return data;
    }




    private List<Integer> createTriangleIndicesData(int rows, int columns) {
        List<Integer> values = new ArrayList<Integer>();

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                int offset = (i* columns +j)*4;
                values.add(offset);
                values.add(offset+1);
                values.add(offset+2);
                values.add(offset+2);
                values.add(offset+3);
                values.add(offset);
            }
        }
        return values;
    }

    public void set(K key, String fileName) {
        ChunkIndexHandle data = new BasicChunkIndexHandle();

        if (fileName==null) {
            data.setIndicesId(-1);
            bufferIdMap.put(key,data);
        } else {
            //read indices data file???
        }
    }


}
