package ru.idgroup.otus.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumeratorExecutor {
    private static final Logger logger = LoggerFactory.getLogger(EnumeratorExecutor.class);
    public static final int MAX_COUNT = 10;
    public static final String START_COMMAND = "start";
    public static final String LAST_COMMAND = "last";
    private static Thread first;
    private static Thread second;
    private int currentNumber =  1;
    private int cnt = 0;
    private static int THREADS_CNT = 2;

    private synchronized void action(String action) {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                for(int i = 1; i <= MAX_COUNT; i++) {
                    if(cnt==0 && !action.equals(START_COMMAND))
                        this.wait();
                    else
                        this.notifyAll();

                    if (i == currentNumber) {
                        logger.info(Thread.currentThread().getName() + " i:" + i);
                        cnt++;
                        if(cnt==THREADS_CNT && action.equals(LAST_COMMAND)) {
                            currentNumber++;
                            cnt=0;
                            notifyAll();
                            logger.info("after notify");
                        }
                        else
                            this.wait();
                    }
                    else {
                        this.wait();
                    }
                }

                currentNumber=9;
                for(int i=MAX_COUNT-1;i>0;i--) {
                    if(cnt==0 && !action.equals(START_COMMAND))
                        this.wait();
                    else
                        this.notifyAll();

                    if (i == currentNumber) {
                        logger.info(Thread.currentThread().getName() + " i:" + i);
                        cnt++;
                        if(cnt==THREADS_CNT && action.equals(LAST_COMMAND)) {
                            currentNumber--;
                            cnt=0;
                            notifyAll();
                            logger.info("after notify");
                        }
                        else
                            this.wait();
                    }
                    else {
                        this.wait();
                    }
                }
                Thread.currentThread().interrupt();

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
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
