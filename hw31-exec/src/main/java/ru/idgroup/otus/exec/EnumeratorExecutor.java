package ru.idgroup.otus.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class EnumeratorExecutor {
    private static final Logger logger = LoggerFactory.getLogger(EnumeratorExecutor.class);
    public static final int MAX_COUNT = 10;
    public static final String START_COMMAND = "start";
    public static final String LAST_COMMAND = "last";
    private Integer currentNumber =  1;

    private String last = LAST_COMMAND;

    private synchronized void action(String action) {
        while(!Thread.currentThread().isInterrupted()) {

            try {
                for(int i=1;i<=MAX_COUNT;i++) {
                    while (last.equals(action) && i >= currentNumber)
                        this.wait();
                    messagging(action,i);
                    notifierCommand(action, 1);
                }

                currentNumber = 10;
                for(int i=MAX_COUNT-1;i>0;i--) {
                    while (last.equals(action) && i <= currentNumber)
                        this.wait();
                    messagging(action,i);
                    notifierCommand(action, -1);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Thread.currentThread().interrupt();
        }
    }

    private void messagging(String action, int i) throws InterruptedException {
        logger.info(Thread.currentThread().getName() + " i:" + i);
        last = action;
    }

    private void notifierCommand(String action, int inc) {
        this.notifyAll();
        if (action.equals(LAST_COMMAND)) {
            currentNumber = currentNumber + inc;
        }

    }

    public static void main(String[] args) {
        Thread first;
        Thread second;

        EnumeratorExecutor task = new EnumeratorExecutor();
        first = new Thread(() -> task.action(START_COMMAND));
        second = new Thread(() -> task.action(LAST_COMMAND));

        first.start();
        second.start();

    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
