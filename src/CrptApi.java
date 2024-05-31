
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CrptApi {

    private final TimeUnit timeUnit;
    private final int requestLimit;
    private final AtomicInteger requestCounter;
    private final Object lock = new Object();

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.timeUnit = timeUnit;
        this.requestLimit = requestLimit;
        this.requestCounter = new AtomicInteger(0);
    }

    public void createDocument(String document, String signature) {
        synchronized (lock) {
            if (requestCounter.get() == requestLimit) {
                try {
                    lock.wait(timeUnit.toMillis(1));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            // Simulate API request
            System.out.println("Making API call with document: " + document + " and signature: " + signature);

            requestCounter.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        CrptApi crptApi = new CrptApi(TimeUnit.SECONDS, 3);

        // Example API calls exceeding limit
        crptApi.createDocument("document1", "signature1");
        crptApi.createDocument("document2", "signature2");
        crptApi.createDocument("document3", "signature3");
        crptApi.createDocument("document4", "signature4");
        crptApi.createDocument("document5", "signature5");
    }
}



