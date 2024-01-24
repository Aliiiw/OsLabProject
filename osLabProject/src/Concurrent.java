import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

class Concurrent implements Runnable {
    private final long[] dataset;
    private final int start;
    private final int end;
    private final AtomicLong currentPeak;

    Concurrent(long[] dataset, int start, int end, AtomicLong currentPeak) {
        this.dataset = dataset;
        this.start = start;
        this.end = end;
        this.currentPeak = currentPeak;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            long item = dataset[i];
            currentPeak.accumulateAndGet(item, Math::max);
        }
    }

    static long executeConcurrently(long[] dataset) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(cores);
        AtomicLong maximumValue = new AtomicLong(Long.MIN_VALUE);

        int chunkSize = dataset.length / cores;
        for (int i = 0; i < cores; i++) {
            int start = i * chunkSize;
            int end = (i == cores - 1) ? dataset.length : (start + chunkSize);
            service.submit(new Concurrent(dataset, start, end, maximumValue));
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);

        return maximumValue.get();
    }
}
