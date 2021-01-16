package starvation;

import deadlock.TestObject;

/**
 * Для фикса голодания нужно после выполнения необходимых операций вызвать wait() на залоченном объекте, чтобы у других
 * потоков была возможность перехватить залоченный объект
 */
public class StarvationFix {

    public static void main(String[] args) {
        TestObject object = new TestObject("TestObject");

        Thread thread = getNewThread(object);
        Thread anotherThread = getNewThread(object);

        thread.start();
        anotherThread.start();
    }

    private static Thread getNewThread(TestObject object) {
        return new Thread(() -> {
            while (true) {
                try {
                    synchronized (object) {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + " работает");
                        object.wait(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    Результат выполнения:
//
//    Thread-1 работает
//    Thread-0 работает
//    Thread-1 работает
//    Thread-0 работает
//    Thread-1 работает
//    Thread-0 работает
//    Thread-1 работает

}
