import java.util.concurrent.atomic.AtomicInteger;

class Customer implements Runnable {
    private static final AtomicInteger customerCount = new AtomicInteger(0);
    private final int customerID;
    private final BarberShop barberShop;

    private Customer(BarberShop barberShop) {
        this.customerID = customerCount.incrementAndGet();
        this.barberShop = barberShop;
    }

    public static Thread createCustomerThread(BarberShop barberShop) {
        return new Thread(new Customer(barberShop));
    }

    @Override
    public void run() {
        barberShop.enterBarberShop(this.customerID);
        barberShop.leaveBarbershop();
    }
}
