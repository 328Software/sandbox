package org.supply.simulator.sandbox.texture;



import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Created by Alex on 7/13/2014.
 */
public class TexturedChunk {}
//        extends AbstractChunkRenderable
//        implements Chunk<ChunkData<float[], byte[]>>, ChunkRenderable, HasId<Long> {
//
//    protected Long id;
//
//    public static final int INDICES_PER_VERTEX = 6;
//
//    private boolean isBuilt;
//    private boolean isDestroyed;
//
//   // private ChunkIndexEngine<ChunkType,ChunkIndexHandle> indexManager;
//
//    private ChunkData<float[], byte[]> data;
//
//    public TexturedChunk () {
//        isBuilt =false;
//        isDestroyed=true;
////        indexManager = new MockChunkIndexEngine();
//    }
//
//    @Override
//    public void build() {
////        rows = data.getRows();
////        columns = data.getColumns();
////
////        indicesBufferId = indexManager.get(ChunkType.MEDIUM_T).getIndicesId();
////        if (!indexEngine.isIndicesBufferIdStored(rows,columns)) {
////
////            List<Integer> indicesBufferData = indexEngine.createIndicesBufferData(2, 2);
////
////
////            IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indicesBufferData.size());
////            for(Integer i: indicesBufferData) {
////                indicesBuffer.put(i);
////            }
////
////            indicesBuffer.flip();
////
////            indicesBufferId = GL15.glGenBuffers();
////            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
////            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
////            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
////
////            indexEngine.storeIndicesBufferId(rows,columns,indicesBufferId);
////        } else {
////            indicesBufferId = indexEngine.getIndicesBufferId(rows,columns);
////        }
//
//
//        vertexAttributesId = GL30.glGenVertexArrays();
//
//        GL30.glBindVertexArray(vertexAttributesId);
//
//        positionsArrayId = GL15.glGenBuffers();
//        colorsArrayId = GL15.glGenBuffers();
//
//        FloatBuffer verticesFloatBuffer = BufferUtils.createFloatBuffer(data.getPositions().length);
////        for(Float f: data.getPositions()) {
//            verticesFloatBuffer.put(data.getPositions());
////        }
//        verticesFloatBuffer.flip();
//
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionsArrayId);
//        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesFloatBuffer, GL15.GL_STATIC_DRAW);
//
//
//        GL20.glVertexAttribPointer(locations[0], BasicChunkData.POSITION_COUNT, GL11.GL_FLOAT,
//                false, BasicChunkData.POSITION_BYTES, BasicChunkData.POSITION_BYTE_OFFSET);
//
//        GL20.glVertexAttribPointer(locations[2], BasicChunkData.TEXTURE_COUNT, GL11.GL_FLOAT,false,
//                BasicChunkData.STRIDE,
//                BasicChunkData.TEXTURE_BYTE_OFFSET);
//
//
//        ByteBuffer verticesByteBuffer = BufferUtils.createByteBuffer(data.getColors().length);
////        for(Byte b: data.getColors()) {
//            verticesByteBuffer.put(data.getColors());
////        }
//        verticesByteBuffer.flip();
//
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorsArrayId);
//        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesByteBuffer, GL15.GL_STATIC_DRAW);
//
//        GL20.glVertexAttribPointer(locations[1], BasicChunkData.COLOR_COUNT, GL11.GL_UNSIGNED_BYTE,
//                true, BasicChunkData.COLOR_BYTES, 0);
//
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//
//        GL30.glBindVertexArray(0);
//        data=null;
//        isBuilt =true;
//    }
//
//    @Override
//    public void destroy() {
//        GL30.glBindVertexArray(vertexAttributesId);
//
//        GL20.glDisableVertexAttribArray(locations[0]);
//        GL20.glDisableVertexAttribArray(locations[1]);
//
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//        GL15.glDeleteBuffers(positionsArrayId);
//
//        //  TODO figure out why we are unbinding this buffer twice
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//        GL15.glDeleteBuffers(colorsArrayId);
//
//        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
//        GL15.glDeleteBuffers(indicesBufferId);
//
//        GL30.glBindVertexArray(0);
//        GL30.glDeleteVertexArrays(vertexAttributesId);
//
//        isDestroyed = true;
//    }
//
//    @Override
//    public void render() {
//
////        GL30.glBindVertexArray(vertexAttributesId);
////        GL20.glEnableVertexAttribArray(locations[0]);
////        GL20.glEnableVertexAttribArray(locations[1]);
////        GL20.glEnableVertexAttribArray(locations[2]);
////
////        // Bind to the index VBO that has all the information about the order of the vertices
////        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
////
////        // Draw the vertices
////        GL32.glDrawElementsBaseVertex(GL11.GL_TRIANGLES, rows * columns * INDICES_PER_VERTEX, GL11.GL_UNSIGNED_INT, 0, 0);
////        // Put everything back to default (deselect)
////        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
////        GL20.glDisableVertexAttribArray(locations[0]);
////        GL20.glDisableVertexAttribArray(locations[1]);
////        GL20.glDisableVertexAttribArray(locations[2]);
////        GL30.glBindVertexArray(0);
//
//    }
//
//    public boolean isBuilt() {
//        return isBuilt;
//    }
//
//    @Override
//    public boolean isDestroyed() {
//        return isDestroyed;
//    }
//
//    @Override
//    public Long getId() {
//        return id;
//    }
//
//    @Override
//    public ChunkData<float[], byte[]> getData() {
//        return data;
//    }
//
//    //@Override
//    //public void setChunkIndexEngine(ChunkIndexEngine indexManager) {
//     //   this.indexManager = indexManager;
//    //}
//
//    @Override
//    public void setData(ChunkData<float[], byte[]> data) {
//        this.data=data;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//}
