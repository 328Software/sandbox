package org.supply.simulator.sandbox.io;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Brandon on 7/27/2014.
 */
public class FileChannelTest {
    FileChannel channel;
    Set<OpenOption> options;
    public static final String channelFilePath = "/file_channel_test.txt";



    @Before
    public void createFixture() {
        options = new HashSet<OpenOption>();
        options.add(StandardOpenOption.WRITE);
        options.add(StandardOpenOption.READ);
    }

    @Test
    public void testFileChannel() {
        try {
            ByteBuffer b = ByteBuffer.allocate(64);
            RandomAccessFile file = new RandomAccessFile(
                    new File(this.getClass().getResource(channelFilePath).toURI()),
                    "rw"
            );

            channel = file.getChannel();
            for(int i = 0; i < file.length()/64; i++) {
//                System.out.println(i);
                channel.position(i * 64);
                b.clear();
                int j = channel.read(b);
                b.flip();
                if(j > 0) {
                    while(b.hasRemaining()) {
                        System.out.print(new String(new byte[]{b.get()},"US-ASCII"));
                    }
                }
            }
            channel.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
