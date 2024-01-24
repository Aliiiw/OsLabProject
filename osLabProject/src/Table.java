import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Table {
    private String item1, item2 = "";
    private final Lock lock = new ReentrantLock();
    private final Condition itemsAvailable = lock.newCondition();
    private final Condition itemsTaken = lock.newCondition();
    private boolean available = false;

    public void putItems(String item1, String item2) throws InterruptedException {
        lock.lock();
        try {
            while (available) {
                itemsTaken.await();
            }
            this.item1 = item1;
            this.item2 = item2;
            available = true;
            System.out.println("Agent -> put " + item1 + " and " + item2 + " on the table 🍽️");
            itemsAvailable.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void takeItems(String smokerIngredient) throws InterruptedException {
        lock.lock();
        try {
            while (!(available && !item1.equals(smokerIngredient) && !item2.equals(smokerIngredient))) {
                itemsAvailable.await();
            }
            available = false;
            System.out.println("Smoker -> with " + smokerIngredient + " takes the items and starts smoking 🚬\n");
            clearItems();
            itemsTaken.signal();
        } finally {
            lock.unlock();
        }
    }

    private void clearItems() {
        this.item1 = "";
        this.item2 = "";
    }
}