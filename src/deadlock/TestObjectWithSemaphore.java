package deadlock;

import java.util.concurrent.Semaphore;

public class TestObjectWithSemaphore extends TestObject {

    Semaphore semaphore;

    TestObjectWithSemaphore(Semaphore semaphore, String name) {
        super(name);
        this.semaphore = semaphore;
    }

    @Override
    public void lockAnotherObject(TestObject anotherObject) {
        try {
            semaphore.acquire();
            super.lockAnotherObject(anotherObject);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore.release();
    }
}
