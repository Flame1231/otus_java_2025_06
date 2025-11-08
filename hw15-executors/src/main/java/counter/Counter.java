package counter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Counter {
    private static final int MAX_NUMBER = 10;
    private static final int MIN_NUMBER = 1;
    private final Object monitor = new Object();
    private int lastPrintThread = 0;
    private int lastAnswerThread = 0;

    public void process(boolean isPrinter) {
        String threadName = Thread.currentThread().getName();
        int direction = 1;
        log.info("Поток {} начал работу", threadName);

        while (!Thread.currentThread().isInterrupted()) {
            synchronized (monitor) {
                if (isPrinter) {
                    while (lastPrintThread != lastAnswerThread) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            log.info("Поток {} был прерван во время ожидания", threadName);
                            return;
                        }
                    }

                    lastPrintThread += direction;
                    log.info("[{}] : {}", threadName, lastPrintThread);

                    if (lastPrintThread == MAX_NUMBER) {
                        direction = -1;
                    } else if (lastPrintThread == MIN_NUMBER) {
                        direction = 1;
                    }

                } else {
                    if (lastPrintThread != lastAnswerThread) {
                        log.info("[{}] : {}", threadName, lastPrintThread);
                        lastAnswerThread = lastPrintThread;
                    }
                    monitor.notifyAll();
                }
            }

            sleep();
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
