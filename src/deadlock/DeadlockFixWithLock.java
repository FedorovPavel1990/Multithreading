package deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockFixWithLock {

    public static void main(String args[]) {
        ReentrantLock lock = new ReentrantLock();

        TestObject object = new TestObject("Объект 1");
        TestObject anotherObject = new TestObject("Объект 2");

        new Thread(() -> doWithLock(lock, object, anotherObject)).start();
        new Thread(() -> doWithLock(lock, anotherObject, object)).start();
    }

    private static void doWithLock(ReentrantLock lock, TestObject object, TestObject anotherObject) {
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)){
                object.lockAnotherObject(anotherObject);
            } else {
                System.out.printf("Потоку %s не удалось залочить %s\n",
                        Thread.currentThread().getName(),
                        object.getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

//    Результат выполнения:
//
//    Thread-0 собирается залочить Объект 1
//    Thread-0 залочил Объект 1
//    Статус Объект 1 - Залочен
//    Thread-0 что-то делает с Объект 1
//    Thread-0 собирается залочить Объект 2
//    Thread-0 залочил Объект 2
//    Статус Объект 2 - Залочен
//    Thread-0 что-то делает с Объект 2
//    Thread-0 разлочил Объект 2
//    Статус Объект 2 - Не залочен
//    Thread-0 разлочил Объект 1
//    Статус Объект 1 - Не залочен
//    Thread-1 собирается залочить Объект 2
//    Thread-1 залочил Объект 2
//    Статус Объект 2 - Залочен
//    Thread-1 что-то делает с Объект 2
//    Thread-1 собирается залочить Объект 1
//    Thread-1 залочил Объект 1
//    Статус Объект 1 - Залочен
//    Thread-1 что-то делает с Объект 1
//    Thread-1 разлочил Объект 1
//    Статус Объект 1 - Не залочен
//    Thread-1 разлочил Объект 2
//    Статус Объект 2 - Не залочен
//
//    Process finished with exit code 0

}
