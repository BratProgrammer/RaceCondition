import java.util.stream.IntStream;

public class MemorySynchronizedProblem {
    private int value = 0;

    public void getProblem() {
        IntStream.range(0, 100).forEach(i -> {
            try {
                problem();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void problem() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                IntStream.range(0, 10).forEach(i -> value++);
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                IntStream.range(0, 10).forEach(i -> value--);
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
