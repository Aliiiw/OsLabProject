import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class RollerCoaster {
    private final int carCapacity;
    private int numberOfPassengersInCar = 0;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition fullCar = lock.newCondition();
    private final Condition emptyCar = lock.newCondition();

    public RollerCoaster(int carCapacity) {
        this.carCapacity = carCapacity;
    }

    public void takeRide(int passengerID) throws InterruptedException {
        lock.lock();
        try {
            while (numberOfPassengersInCar == carCapacity) {
                System.out.println("Passenger " + passengerID + " is waiting for the car ⏳");
                fullCar.await();
            }
            numberOfPassengersInCar++;
            System.out.println("Passenger " + passengerID + " boarded the car 🚙");

            if (numberOfPassengersInCar == carCapacity) {
                emptyCar.signal();
            }

            fullCar.await();

            System.out.println("Passenger " + passengerID + " has finished the ride and is getting off 🏁");

        } finally {
            lock.unlock();
        }
    }

    public void loadPassengers() throws InterruptedException {
        lock.lock();
        try {
            while (numberOfPassengersInCar < carCapacity) {
                System.out.println("Car -> Waiting for passengers to board...");
                emptyCar.await();
            }
            System.out.println("Car -> Ride is starting 🔥");
            System.out.println();
        } finally {
            lock.unlock();
        }
    }

    public void unloadPassengers() {
        lock.lock();
        try {
            numberOfPassengersInCar = 0;
            System.out.println("Car -> All passengers have left the car 🚫");
            fullCar.signalAll();
        } finally {
            lock.unlock();
        }
    }
}