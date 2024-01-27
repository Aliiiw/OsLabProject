class BarberShop {
    private int numberOfFreeChairs;
    private boolean isBarberSleeping = true;

    public BarberShop(int numberOfFreeChairs) {
        this.numberOfFreeChairs = numberOfFreeChairs;
    }

    public synchronized void enterBarberShop(int customerID) {
        if (numberOfFreeChairs > 0) {
            System.out.println("Customer " + customerID + " sits on waiting chair \uD83E\uDE91");
            numberOfFreeChairs--;
            if (isBarberSleeping) {
                isBarberSleeping = false;
                notifyAll();
            }
        } else {
            System.out.println("Customer " + customerID + " leaves as no chair is free \uD83D\uDED1");
        }
    }

    public synchronized void getHaircut() throws InterruptedException {
        while (isBarberSleeping) {
            wait();
        }
        Thread.sleep(2000);
        if (numberOfFreeChairs < 5) {
            notifyAll();
        } else {
            isBarberSleeping = true;
        }
    }

    public synchronized void leaveBarbershop() {
        numberOfFreeChairs++;
        System.out.println("One customer leaves the barbershop after haircut \uD83D\uDE0E ");
    }
}
