package starvation;

import java.util.concurrent.atomic.AtomicBoolean;

public class Starvation {

    private final static AtomicBoolean isAlive = new AtomicBoolean(true);

    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();

        Thread thread = getNewThread(object);
        Thread anotherThread = getNewThread(object);

        thread.start();
        anotherThread.start();

        Thread.sleep(10000);
        isAlive.set(false);
    }

    private static Thread getNewThread(Object object) {
        return new Thread(() -> {
            while (isAlive.get()) {
                try {
                    synchronized (object) {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + " работает");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    Результат выполнения:
//
//    Thread-0 работает
//    Thread-0 работает
//    Thread-0 работает
//    Thread-0 работает
//    Thread-0 работает
//    Thread-0 работает
//    Thread-0 работает
//    Thread-0 работает
//    Thread-0 работает
//    Thread-0 работает
//    Thread-1 работает
//
//    Process finished with exit code 0

}
