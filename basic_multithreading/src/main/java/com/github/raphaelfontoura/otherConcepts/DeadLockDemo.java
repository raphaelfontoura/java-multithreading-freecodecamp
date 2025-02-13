package com.github.raphaelfontoura.otherConcepts;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDemo {
    private final Lock lockA = new ReentrantLock(true);
    private final Lock lockB = new ReentrantLock(true);

    public void workerOne() {
        lockA.lock();
        System.out.println("Worker One acquired lock A");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lockB.lock();
        System.out.println("Worker One acquired lock B");
        lockA.unlock();
        lockB.unlock();
    }

    public void workerTwo() {
        lockB.lock();
        System.out.println("Worker Two acquired lock B");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lockA.lock();
        System.out.println("Worker Two acquired lock A");
        lockA.unlock();
        lockB.unlock();
    }

    public static void main(String[] args) {
        DeadLockDemo deadLockDemo = new DeadLockDemo();
        new Thread(deadLockDemo::workerOne).start();
        new Thread(deadLockDemo::workerTwo).start();

        // approach to check deadlock
        new Thread(() -> {
            ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
            while (true) {
                long[] threadIds = mxBean.findDeadlockedThreads();
                if (threadIds != null) {
                    System.out.println("Deadlock detected");
                    ThreadInfo[] threadInfo = mxBean.getThreadInfo(threadIds);
                    for (long threadId : threadIds) {
                        System.out.println("Thread ID: " + threadId + " is in deadlock");
                    }
                    break;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    /*
     * This code will cause a deadlock because workerOne acquires lockA and workerTwo acquires lockB.
     * Then, workerOne tries to acquire lockB and workerTwo tries to acquire lockA.
     * To see the thread dump and understand the deadlock, run the following command:
     * jps -l (to get the PID)
     * jstack <PID>
     *  
     * or use the following code to print the thread dump:
     * jps -l (to get the PID)
     * kill -3 <PID>
     * back to the terminal where the application is running
     * and check the thread dump
     */

    /*
     * Alternatives to avoid DeadLocks:
     * 1. Use Timeouts
     * 2. Global ordering of locks
     * 3. Avoid nesting of locks
     * 4. Use Thread safe alternatives
     */
}
