package org.supply.simulator.sandbox;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by Brandon on 6/6/2014.
 */
public class ExecutorServiceTest {
    protected ThreadPoolExecutor threadPoolExecutor;
    protected ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;


    @Test
    public void SimpleThreadPoolExecutorTest() {
        printTestMessage("SimpleThreadPoolExecutorTest", true);
        threadPoolExecutor = new ThreadPoolExecutor(1,1,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(1));
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!!");
            }
        });
        terminateThreadPoolExecutor(threadPoolExecutor, 10);
        System.out.println("Done");
        printTestMessage("SimpleThreadPoolExecutorTest", false);
    }

    @Test
    public void SimpleScheduledThreadPoolExecutorTest() {
        printTestMessage("SimpleScheduledThreadPoolExecutorTest", true);
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("World");
            }
        },5,TimeUnit.SECONDS);
        Future f = scheduledThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        });
        terminateThreadPoolExecutor(scheduledThreadPoolExecutor,10);
        printTestMessage("SimpleScheduledThreadPoolExecutorTest", false);
    }


    @Test
    public void BlockingScheduledThreadPoolExecutorTest() {
        printTestMessage("BlockingScheduledThreadPoolExecutorTest", true);
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        scheduledThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                long wait = System.currentTimeMillis() + (1000*4);
                while(System.currentTimeMillis() < wait );
                System.out.println("Hello");
            }
        });
        scheduledThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                    long wait = System.currentTimeMillis() + (1000*10);
                    while(System.currentTimeMillis() < wait );
            }
        });
        Future f = scheduledThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("World");
            }
        });
        terminateThreadPoolExecutor(scheduledThreadPoolExecutor,10);
        printTestMessage("BlockingScheduledThreadPoolExecutorTest", false);
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
