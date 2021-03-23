package starvation;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Так как в данном примере используются одинаковые потоки, то можно перед выполнением действий в цикле вызвать
 * notify(), а после выполнения вызывать wait() - в итоге в первом потоке по вызову notify() ничего не произойдет, но
 * после выполнения своих действий поток встанет в режим ожидания, и управление перехватит второй поток, который снимет
 * режим ожидания с первого потока, выполнит свои действия и сам встанет в режим ожидания и т. д. В конце вызываем
 * notifyAll() для разблокировки всех ждущих потоков после завершения основного потока
 */
public class StarvationFixWithWaitAndNotify {

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
                        object.notify();
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + " работает");
                        object.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (object) {
                object.notifyAll();
            }
        });
    }

//    Результат выполнения:
//
//    Thread-0 работает
//    Thread-1 работает
//    Thread-0 работает
//    Thread-1 работает
//    Thread-0 работает
//    Thread-1 работает
//    Thread-0 работает
//    Thread-1 работает
//    Thread-0 работает
//    Thread-1 работает
//
//    Process finished with exit code 0

}
