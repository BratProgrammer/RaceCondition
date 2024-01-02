import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class FixedMemorySynchronizedProblem {

    //Atomic классы имеют встроенную синхронизацию
    private AtomicInteger value = new AtomicInteger(0);

    public void getfixedProblem() {


        IntStream.range(0, 100).forEach(i -> {
            try {
                fixedProblem();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void fixedProblem() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                IntStream.range(0, 10).forEach(i -> value.incrementAndGet());
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                IntStream.range(0, 10).forEach(i -> value.decrementAndGet());
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();


        // В одном потоке мы уменьшаем value на 10, в другом увеличивам на 10
        // Результат должен быть 0, но появляются -1
        // Это из-за того, что кэш потока не успевает синхронизироваться с пямятью главного потока
        // volatile эту пролему не решит, так как данное ключевое слово просто учащает синхронизацию, но гарантии не даёт.

        System.out.println(value);
    }
}
