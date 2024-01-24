class Smoker implements Runnable {
    private final Table table;
    private final String ingredient;

    public Smoker(Table table, String ingredient) {
        this.table = table;
        this.ingredient = ingredient;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                table.takeItems(ingredient);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Smoker -> with " + ingredient + " interrupted");
        }
    }
}
