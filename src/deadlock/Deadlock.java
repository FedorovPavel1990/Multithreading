package deadlock;

/**
 * Получаем deadlock
 */
public class Deadlock {

    public static void main(String args[]) {
        TestObject object = new TestObject("Объект 1");
        TestObject anotherObject = new TestObject("Объект 2");

        new Thread(() -> object.lockAnotherObject(anotherObject)).start();
        new Thread(() -> anotherObject.lockAnotherObject(object)).start();
    }

//    Результат выполнения:
//
//    Thread-1 собирается залочить Объект 2
//    Thread-0 собирается залочить Объект 1
//    Thread-1 залочил Объект 2
//    Thread-0 залочил Объект 1
//    Статус Объект 2 - Залочен
//    Статус Объект 1 - Залочен
//    Thread-1 что-то делает с Объект 2
//    Thread-0 что-то делает с Объект 1
//    Thread-1 собирается залочить Объект 1
//    Thread-0 собирается залочить Объект 2

}