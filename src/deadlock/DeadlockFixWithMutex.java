package deadlock;

/**
 * Здесь для избежания дедлока мы ожидаем окончание выполнения первого запущенного потока
 */
public class DeadlockFixWithMutex {

    public static void main(String args[]) throws InterruptedException {
        TestObject object = new TestObject("Объект 1");
        TestObject anotherObject = new TestObject("Объект 2");

        Thread thread = new Thread(() -> object.lockAnotherObject(anotherObject));
        Thread anotherThread = new Thread(() -> anotherObject.lockAnotherObject(object));

        thread.start();
        thread.join();
        anotherThread.start();
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