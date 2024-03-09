package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomThread implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CustomThread.class);
    private final ReentrantLock lock;
    private final Condition condition;
    private final int threadId;
    private static int currentTread = 1;
    private static int count = 1;
    private boolean isInc;

    public CustomThread(int threadId, ReentrantLock lock, Condition condition) {
        this.threadId = threadId;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {

            isInc();

            lock.lock();
            try {

                while (threadId != currentTread) {
                    condition.await();
                }

                log.info(String.valueOf(count));

                if (threadId == 1) {
                    currentTread = 2;
                } else if (threadId == 2) {
                    if (isInc) {
                        count++;
                    } else {
                        count--;
                    }
                    currentTread = 1;
                }

                condition.signalAll();

            } catch (InterruptedException exception) {
                log.info(exception.getMessage());
            } finally {
                lock.unlock();
            }

            isInc();
        }
    }

    private void isInc() {
        if (count == 1) isInc = true;
        if (count == 10) isInc = false;
    }
}
