import java.util.concurrent.CountDownLatch;

/**
 * @author lihe
 * @Title:
 * @Description:
 * @date 2018/5/23上午11:15
 */
public class TestHarness {
    public long timeTasks (int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i<nThreads; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try{
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };
            t.start();
        }
        long start =System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) throws InterruptedException {
        TestHarness h = new TestHarness();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int j = 0;
                for (int i = 0; i<1000; i++)
                    j++;

            }
        };
        System.out.println(h.timeTasks(19,r));
    }
}
