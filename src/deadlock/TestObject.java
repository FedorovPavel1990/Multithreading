package deadlock;

public class TestObject {

    private final String name;
    private String status = "Не залочен";

    public TestObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public synchronized void lockAnotherObject(TestObject anotherObject) {
        log("%s собирается залочить %s", Thread.currentThread().getName(), name);
        lock();
        doSomethingWithAnotherObject(anotherObject);
        unlock();
    }

    private void lock() {
        status = "Залочен";
        log("%s залочил %s", Thread.currentThread().getName(), name);
        log("Статус %s - %s", name, status);
    }

    private void unlock() {
        status = "Не залочен";
        log("%s разлочил %s", Thread.currentThread().getName(), name);
        log("Статус %s - %s", name, status);
    }

    private void doSomethingWithAnotherObject(TestObject anotherObject) {
        log("%s что-то делает с %s", Thread.currentThread().getName(), name);
        log("%s собирается залочить %s", Thread.currentThread().getName(), anotherObject.getName());
        synchronized (anotherObject) {
            anotherObject.lock();
            anotherObject.doSomething();
            anotherObject.unlock();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doSomething() {
        log("%s что-то делает с %s", Thread.currentThread().getName(), name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void log(String message, Object... parameters) {
        System.out.printf(message + '\n', parameters);
    }
}
