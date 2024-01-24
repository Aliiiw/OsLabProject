public class Main {
    public static void main(String[] args) {

        Table table = new Table();
        new Thread(new Agent(table)).start();
        new Thread(new Smoker(table, "Tobacco")).start();
        new Thread(new Smoker(table, "Paper")).start();
        new Thread(new Smoker(table, "Matches")).start();
    }
}