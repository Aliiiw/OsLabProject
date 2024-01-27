public class Main {
    public static void main(String[] args) {

        final int chairs = 5;
        BarberShop barberShop = new BarberShop(chairs);
        Thread barber = new Thread(new Barber(barberShop));
        barber.start();

        final int numberOfCustomers = 20;
        for (int i = 0; i < numberOfCustomers; i++) {
            Thread customerThread = Customer.createCustomerThread(barberShop);
            customerThread.start();
            try {
                Thread.sleep((long) ((Math.random() + 1) * 1000)); //have at least 1-second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
