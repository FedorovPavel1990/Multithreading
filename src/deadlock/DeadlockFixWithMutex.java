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

}