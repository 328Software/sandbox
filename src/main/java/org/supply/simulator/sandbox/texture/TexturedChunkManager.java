package org.supply.simulator.sandbox.texture;

import org.supply.simulator.display.manager.chunk.AbstractChunkManager;
import org.supply.simulator.display.manager.chunk.Chunk;
import org.supply.simulator.display.manager.chunk.impl.BasicChunkData;
import org.supply.simulator.display.manager.chunk.impl.BasicChunkIndexManager;
import org.supply.simulator.display.manager.chunk.impl.FloatPositionByteColorChunkData;
import org.supply.simulator.display.window.Camera;
import org.supply.simulator.sandbox.mockdisplay.VertexData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 7/13/2014.
 */
public class TexturedChunkManager <V extends Chunk> extends AbstractChunkManager<Chunk> {
    private int chunkRows = 50;
    private int chunkColumns = 50;
    private int totalChunkRows = 5;
    private int totalChunkColumns = 5;



    private boolean isFirst;

    public TexturedChunkManager () {
        super();
        isFirst = true;
        chunkCollection = new ArrayList<Chunk>();
        indexManager = new BasicChunkIndexManager();

    }

    @Override
    protected void updateChunks(Camera view) {
        if (isFirst) {
            isFirst=false;
            for (int i = 0; i<totalChunkRows*chunkRows;i=i+chunkRows) {
                for (int j = 0; j<totalChunkColumns*chunkColumns;j=j+chunkColumns) {
                    TexturedChunk chunk = new TexturedChunk();
                    chunk.setAttributeLocations(new int[]{0,1,2});
                    chunk.setData(getChunkData(chunkRows,chunkColumns,i,j));
                    chunkCollection.add(chunk);
                }
            }
        }
    }


    public static BasicChunkData<List<Float>,List<Byte>> getChunkData
            (int row, int col, int topLeftX, int topLeftY) {
        BasicChunkData<List<Float>,List<Byte>> basicDataOut = new FloatPositionByteColorChunkData();

        List<Float> positions = new ArrayList<Float>();
        List<Byte> colors = new ArrayList<Byte>();

//        for(int i = topLeftX; i < +row+topLeftX; i++) {
//            for(int j = topLeftY; j < col+topLeftY; j++) {
//
////                byte blackInt = (i%2|j%2)==0||(i%2&j%2)==1?(byte)0:(byte)((i*columns+j)/256);
//                byte blackInt = (i%2|j%2)==0||(i%2&j%2)==1?(byte)0:(byte)-1;
//
//                float x =-0.5f + 0.1f*i,y=0.5f-0.1f*j,z=-(.1f*(i+j)),length=0.1f;
//
//                byte colorbyte = (byte)(((i/col%2|j/row%2)==0||(i/col%2&j/row%2)==1)?(blackInt&0xf):(blackInt&0xff));
//
//                // We'll define our quad using 4 vertices of the custom 'TexturedVertex' class
//                VertexData v0 = new VertexData();
//                //top left
//                v0.setXYZ(x, y, /*z+length*/0); v0.setRGB(blackInt, colorbyte, (byte)(blackInt&0xaa));// v0.setST(0, 0);
//                VertexData v1 = new VertexData();
//                //bottom left
//                v1.setXYZ(x, y-length, /*z*/0); v1.setRGB(blackInt, colorbyte, (byte)(blackInt&0xaa));// v1.setST(0, 1);
//                VertexData v2 = new VertexData();
//                //bottom right
//                v2.setXYZ(x+length, y-length, /*z-length*/0); v2.setRGB(blackInt, colorbyte, (byte)(blackInt&0xaa));// v2.setST(1, 1);
//                VertexData v3 = new VertexData();
//                //top right
//                v3.setXYZ(x+length, y, /*z*/0); v3.setRGB(blackInt, colorbyte, (byte)(blackInt&0xaa));// v3.setST(1, 0);
//
//                VertexData[] vertices = new VertexData[]  {v0, v1, v2, v3};
//
//                // Put each 'Vertex' in one FloatBuffer
//
//                for (int k = 0; k < vertices.length; k++) {
//                    // Add position, color and texture floats to the buffer
//                    for(Float f: vertices[k].getElements().getPositionData()) {
//                        positions.add(f);
//                    }
//                    for(Byte b: vertices[k].getElements().getColorData())  {
//                        colors.add(b);
//                    }
//                }
//            }
//        }
        float length = .5f;

        VertexData v0 = new VertexData();
        //top left
        v0.setXYZ(0, 0, /*z+length*/0); v0.setRGB((byte)255, (byte)255, (byte)255);// v0.setST(0, 0);
        VertexData v1 = new VertexData();
        //bottom left
        v1.setXYZ(0, 0-length, /*z*/0); v1.setRGB((byte)255, (byte)255, (byte)255);// v1.setST(0, 1);
        VertexData v2 = new VertexData();
        //bottom right
        v2.setXYZ(0+length, 0-length, /*z-length*/0); v2.setRGB((byte)255, (byte)255, (byte)255);// v2.setST(1, 1);
        VertexData v3 = new VertexData();
        //top right
        v3.setXYZ(0+length, 0, /*z*/0); v3.setRGB((byte)255,(byte)255,(byte)255);// v3.setST(1, 0);

        VertexData[] vertices = new VertexData[]  {v0, v1, v2, v3};

        // Put each 'Vertex' in one FloatBuffer

        for (int k = 0; k < vertices.length; k++) {
            // Add position, color and texture floats to the buffer
            for(Float f: vertices[k].getElements().getPositionData()) {
                positions.add(f);
            }
            for(Byte b: vertices[k].getElements().getColorData())  {
                colors.add(b);
            }
        }


        basicDataOut.setColors(colors);
        basicDataOut.setPositions(positions);

        basicDataOut.setColumns(col);
        basicDataOut.setRows(row);

        return basicDataOut;
    }

    public void setChunkSizes(int chunkRows,int chunkColumns,int totalChunkRows,int totalChunkColumns) {
        this.chunkRows = chunkRows;
        this.chunkColumns = chunkColumns;
        this.totalChunkRows = totalChunkRows;
        this.totalChunkColumns = totalChunkColumns;
    }

}
