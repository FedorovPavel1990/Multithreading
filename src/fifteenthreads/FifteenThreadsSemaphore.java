package fifteenthreads;

import java.util.concurrent.Semaphore;

public class FifteenThreadsSemaphore {

    private static final int THREADS_COUNT = 15;

    public static void main(String[] args) throws InterruptedException {
        final Object monitor = new Object();
        Semaphore semaphore = new Semaphore(0);

        for (int i = 0; i < THREADS_COUNT; i++) {
            new Thread(() -> {
                TestProcess.process();
                System.out.println(Thread.currentThread().getName() + " готов к выполнению");
                synchronized (monitor) {
                    monitor.notifyAll();
                }
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " начал выполнение");
            }).start();
        }

        while (semaphore.getQueueLength() != THREADS_COUNT) {
            synchronized (monitor) {
                monitor.wait();
            }
        }
        semaphore.release(THREADS_COUNT);
    }

//    Результат выполнения:
//
//    Thread-10 готов к выполнению
//    Thread-4 готов к выполнению
//    Thread-5 готов к выполнению
//    Thread-9 готов к выполнению
//    Thread-11 готов к выполнению
//    Thread-8 готов к выполнению
//    Thread-14 готов к выполнению
//    Thread-3 готов к выполнению
//    Thread-2 готов к выполнению
//    Thread-13 готов к выполнению
//    Thread-6 готов к выполнению
//    Thread-12 готов к выполнению
//    Thread-0 готов к выполнению
//    Thread-1 готов к выполнению
//    Thread-7 готов к выполнению
//    Thread-13 начал выполнение
//    Thread-4 начал выполнение
//    Thread-14 начал выполнение
//    Thread-12 начал выполнение
//    Thread-10 начал выполнение
//    Thread-9 начал выполнение
//    Thread-7 начал выполнение
//    Thread-2 начал выполнение
//    Thread-1 начал выполнение
//    Thread-11 начал выполнение
//    Thread-0 начал выполнение
//    Thread-3 начал выполнение
//    Thread-5 начал выполнение
//    Thread-8 начал выполнение
//    Thread-6 начал выполнение
//
//    Process finished with exit code 0

}
