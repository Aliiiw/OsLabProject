public class Main {

    private static int iteration = 1;
    private static double efficiencyRatio = 0;

    public static void main(String[] args) throws InterruptedException {

        while (efficiencyRatio <= 2) {
            //start from 100000 according to Documentation
            int count = (int) Math.pow(2, iteration) * 100000;
            long[] dataSet = createAndFill(count);

            long startTimeSerial = System.currentTimeMillis();
            long maxSerial = findPeakSequentially(dataSet);
            long endTimeSerial = System.currentTimeMillis();
            long serialDuration = endTimeSerial - startTimeSerial;

            long startTimeParallel = System.currentTimeMillis();
            long maxParallel = Concurrent.executeConcurrently(dataSet);
            long endTimeParallel = System.currentTimeMillis();
            long parallelDuration = endTimeParallel - startTimeParallel;

            efficiencyRatio = (double) serialDuration / parallelDuration;

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Iteration: ").append(iteration).append("\n");
            stringBuilder.append("Count: ").append(count).append("\n");
            stringBuilder.append("Serial Max: ").append(maxSerial).append("\n");
            stringBuilder.append("Serial Time: ").append(serialDuration).append("ms").append("\n");
            stringBuilder.append("Parallel Max: ").append(maxParallel).append("\n");
            stringBuilder.append("Parallel Time: ").append(parallelDuration).append("ms").append("\n");
            stringBuilder.append("Efficiency Ratio: ").append(efficiencyRatio).append("\n");
            stringBuilder.append("----------------------------------------------------------");
            System.out.println(stringBuilder);
            iteration++;
        }
    }

    private static long findPeakSequentially(long[] dataset) {
        return java.util.Arrays.stream(dataset).max().orElse(Long.MIN_VALUE);
    }

    private static long[] createAndFill(int count) {
        long[] dataSet = new long[count];
        for (int i = 0; i < count; i++) {
            dataSet[i] = (long) (Math.random() * Long.MAX_VALUE);
        }
        return dataSet;
    }
}