package ru.otus;


import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Application {
    public static void main(String[] args) {
        var executor = Executors.newFixedThreadPool(2);

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        executor.execute(new CustomThread(1, lock, condition));
        executor.execute(new CustomThread(2, lock, condition));

        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        executor.shutdownNow();
    }
}
