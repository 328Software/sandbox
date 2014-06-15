package org.supply.simulator.sandbox;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Brandon on 6/6/2014.
 */
public class ExecutorServiceTest {
    protected ThreadPoolExecutor threadPoolExecutor;
    protected ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    ScheduledFuture<?> future;
            final AtomicInteger times = new AtomicInteger();

    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();

//    @Test
//    public void simpleThreadPoolExecutorTest() {
//        printTestMessage("SimpleThreadPoolExecutorTest", true);
//        threadPoolExecutor = new ThreadPoolExecutor(1,1,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(1));
//        threadPoolExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Hello World!!");
//            }
//        });
//        terminateThreadPoolExecutor(threadPoolExecutor, 10);
//        System.out.println("Done");
//        printTestMessage("SimpleThreadPoolExecutorTest", false);
//    }
//
//    @Test
//    public void simpleScheduledThreadPoolExecutorTest() {
//        printTestMessage("SimpleScheduledThreadPoolExecutorTest", true);
//        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
//        scheduledThreadPoolExecutor.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("World");
//            }
//        },5,TimeUnit.SECONDS);
//        Future f = scheduledThreadPoolExecutor.submit(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Hello");
//            }
//        });
//        terminateThreadPoolExecutor(scheduledThreadPoolExecutor,10);
//        printTestMessage("SimpleScheduledThreadPoolExecutorTest", false);
//    }
//
//
//    @Test
//    public void blockingScheduledThreadPoolExecutorTest() {
//        printTestMessage("BlockingScheduledThreadPoolExecutorTest", true);
//        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
//        scheduledThreadPoolExecutor.submit(new Runnable() {
//            @Override
//            public void run() {
//                long wait = System.currentTimeMillis() + (1000*4);
//                while(System.currentTimeMillis() < wait );
//                System.out.println("Hello");
//            }
//        });
//        scheduledThreadPoolExecutor.submit(new Runnable() {
//            @Override
//            public void run() {
//                    long wait = System.currentTimeMillis() + (1000*10);
//                    while(System.currentTimeMillis() < wait );
//            }
//        });
//        Future f = scheduledThreadPoolExecutor.submit(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("World");
//            }
//        });
//        terminateThreadPoolExecutor(scheduledThreadPoolExecutor,10);
//        printTestMessage("BlockingScheduledThreadPoolExecutorTest", false);
//    }

//    @Test
//    synchronized public void spamThreadPoolExecutorTest() {
//        final int max = 1000000;
//        threadPoolExecutor = new ThreadPoolExecutor(1,1,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(max-1));
//            for (int i = 0; i < max; i++) {
//                final int j = i;
//                threadPoolExecutor.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (j % (max / 10) == (max / 10) -1) {
//                            System.out.print(".");
//                            if (j == max-1) {
//                                System.out.print("Done!");
//                                System.out.println();
//                            }
//                        }
//                    }
//                });
//            }
//            terminateThreadPoolExecutor(threadPoolExecutor, 0);
//    }

    @Test
    public void spamScheduledThreadPoolExecutorTest() {
        final int max = 1000000;
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
//
//  for (int i = 0; i < max; i++) {
//            final int j = i;
            future = scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
//                static Integer times = 0;
                @Override
                public void run() {
//                    synchronized (times) {
                        if (times.intValue() == max) future.cancel(true);
                        if (times.intValue() % (max / 10) == (max / 10) - 1) {
                            System.out.print(".");
                            if (times.intValue() == max - 1) {
                                System.out.print("Done!");
                                System.out.println();
                            }
                        }
                        times.incrementAndGet();
//                    }
                }
            },0,1,TimeUnit.NANOSECONDS);
//        scheduledThreadPoolExecutor.shutdown();
//        scheduledThreadPoolExecutor.awaitTermination()
//                   System.out.println("ok?");
        while(times.intValue()< max);
        terminateThreadPoolExecutor(scheduledThreadPoolExecutor, 300);
    }

    protected void printTestMessage(String testName, boolean start) {
        String surroundingCharacters = "*****";
        System.out.println(
                surroundingCharacters +
                        " " +
                        ((start)?"Starting ":"Ending ") +
                        testName +
                        surroundingCharacters);
    }

    protected void terminateThreadPoolExecutor(ThreadPoolExecutor executor, long timeout) {
        try {
            executor.shutdown();
            executor.awaitTermination(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Failed to wait for tasks to finish. Aborting all tasks immediately.");
            executor.shutdownNow();
        }
    }
}
