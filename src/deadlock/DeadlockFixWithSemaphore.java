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
