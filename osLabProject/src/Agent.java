class Agent implements Runnable {
    private final Table table;
    private final String[] items = {"Tobacco", "Paper", "Matches"};

    public Agent(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Thread.sleep(1000);
                putRandomItems();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Agent interrupted");
        }
    }

    private void putRandomItems() throws InterruptedException {
        int firstItemIndex = (int) (Math.random() * items.length);
        int secondItemIndex;
        do {
            secondItemIndex = (int) (Math.random() * items.length);
        } while (firstItemIndex == secondItemIndex);
        table.putItems(items[firstItemIndex], items[secondItemIndex]);
    }
}