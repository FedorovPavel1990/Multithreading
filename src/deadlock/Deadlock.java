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

}