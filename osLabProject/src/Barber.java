class Barber implements Runnable {

    private final BarberShop barberShop;
    private volatile boolean keepWorking;

    public Barber(BarberShop barberShop) {
        this.barberShop = barberShop;
        this.keepWorking = true;
    }

    private void stopWorking() {
        keepWorking = false;
    }

    @Override
    public void run() {
        while (keepWorking) {
            try {
                barberShop.getHaircut();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                stopWorking();
            }
        }
    }
}