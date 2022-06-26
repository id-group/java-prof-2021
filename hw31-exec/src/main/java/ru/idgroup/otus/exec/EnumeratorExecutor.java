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
                while (currentNumber < MAX_COUNT) {
                    messagging(action);
                    notifierCommand(action, 1);
                }

                while (currentNumber > 0) {
                    messagging(action);
                    notifierCommand(action,-1);
                    this.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.notifyAll();

            Thread.currentThread().interrupt();
        }
    }

    private void messagging(String action) throws InterruptedException {
        while (last.equals(action))
            this.wait();
        last = action;
        logger.info(Thread.currentThread().getName() + " i:" + currentNumber);
    }

    private void notifierCommand(String action, int inc) {
        if (action.equals(LAST_COMMAND)) {
            this.notifyAll();
            currentNumber = currentNumber + inc;
        }
        if (action.equals(START_COMMAND)) {
            this.notifyAll();
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
