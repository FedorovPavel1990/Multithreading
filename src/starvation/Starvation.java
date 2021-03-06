package starvation;

import deadlock.TestObject;

public class Starvation {

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

}
