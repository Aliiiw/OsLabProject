public class Main {
    public static void main(String[] args) {

        final int carCapacity = 5;
        final int numberOfPassengers = 12;

        final RollerCoaster rollerCoaster = new RollerCoaster(carCapacity);

        Thread carThread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    rollerCoaster.loadPassengers();
                    Thread.sleep(2000);
                    rollerCoaster.unloadPassengers();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        carThread.start();

        for (int i = 0; i < numberOfPassengers; i++) {
            final int passengerID = i;
            Thread passengerThread = new Thread(() -> {
                try {
                    while (!Thread.interrupted()) {
                        rollerCoaster.takeRide(passengerID);
                        Thread.sleep((long) (Math.random() * 2000));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            passengerThread.start();
        }
    }
}
