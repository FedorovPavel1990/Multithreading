package deadlock;

import java.util.concurrent.Semaphore;

/**
 * Здесь мы сделали класс TestObjectWithSemaphore, который наследуется от TestObject и переопределили в нем метод
 * lockAnotherObject() на использование семафора
 */
public class DeadlockFixWithSemaphore {

    public static void main(String args[]) {
        Semaphore semaphore = new Semaphore(1);

        TestObject object = new TestObjectWithSemaphore(semaphore, "Объект 1");
        TestObject anotherObject = new TestObjectWithSemaphore(semaphore, "Объект 2");

        new Thread(() -> object.lockAnotherObject(anotherObject)).start();
        new Thread(() -> anotherObject.lockAnotherObject(object)).start();
    }

}
