package counter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Counter {
    private static final int MAX_NUMBER = 10;
    private static final int MIN_NUMBER = 1;
    private final Object monitor = new Object();
    private int lastPrintThread = 0;
    private int lastAnswerThread = 0;

    public void print() {
        String threadName = Thread.currentThread().getName();
        int plusNumber = 1;
        log.info("Поток {} начал работу", threadName);

        while (!Thread.currentThread().isInterrupted()) {
            synchronized (monitor) {
                while (lastPrintThread != lastAnswerThread) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        log.info("Поток {} был прерван во время ожидания", threadName);
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                lastPrintThread += plusNumber;
                log.info("[{}] : {}", threadName, lastPrintThread);
                sleep();
                if (lastPrintThread == MAX_NUMBER) {
                    plusNumber = -1;
                } else if (lastPrintThread == MIN_NUMBER) {
                    plusNumber = 1;
                }
            }
        }
        log.info("Поток {} завершил работу", threadName);
    }

    public void answer() {
        String threadName = Thread.currentThread().getName();
        log.info("Поток {} начал работу", threadName);

        while (!Thread.currentThread().isInterrupted()) {
            synchronized (monitor) {
                if (lastPrintThread != lastAnswerThread) {
                    log.info("[{}] : {}", threadName, lastPrintThread);
                    sleep();
                    lastAnswerThread = lastPrintThread;
                }
                monitor.notifyAll();
            }
        }
        log.info("Поток {} завершил работу", threadName);
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            String threadName = Thread.currentThread().getName();
            log.error("Поток {} был прерван во время сна: {}", threadName, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
