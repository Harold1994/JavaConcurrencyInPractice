import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author harold
 * @Title: BrokenPrimeProducer
 * @Description: 不可靠的取消操作将把生产者置于阻塞的操作中
 * @date 2018/5/29下午5:38
 */
public class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException consumed){

        }
    }

    public void cancel() {
        cancelled = true;
    }
    //消费者
    void consumerPrimes() throws InterruptedException {
        BlockingQueue<BigInteger> primes = new BlockingQueue<BigInteger>() {
            @Override
            public boolean add(BigInteger bigInteger) {

            }

            @Override
            public boolean offer(BigInteger bigInteger) {
                return false;
            }

            @Override
            public void put(BigInteger bigInteger) throws InterruptedException {

            }

            @Override
            public boolean offer(BigInteger bigInteger, long timeout, TimeUnit unit) throws InterruptedException {
                return false;
            }

            @Override
            public BigInteger take() throws InterruptedException {
                return null;
            }

            @Override
            public BigInteger poll(long timeout, TimeUnit unit) throws InterruptedException {
                return null;
            }

            @Override
            public int remainingCapacity() {
                return 0;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public int drainTo(Collection<? super BigInteger> c) {
                return 0;
            }

            @Override
            public int drainTo(Collection<? super BigInteger> c, int maxElements) {
                return 0;
            }

            @Override
            public BigInteger remove() {
                return null;
            }

            @Override
            public BigInteger poll() {
                return null;
            }

            @Override
            public BigInteger element() {
                return null;
            }

            @Override
            public BigInteger peek() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Iterator<BigInteger> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends BigInteger> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {
/**
 * @author harold
 * @Title: BrokenPrimeProducer
 * @Description: 不可靠的取消操作将把生产者置于阻塞的操作中
 * @date 2018/5/29下午5:38
 */
                public class BrokenPrimeProducer extends Thread {
                    private final BlockingQueue<BigInteger> queue;
                    private volatile boolean cancelled = false;

                    public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
                        this.queue = queue;
                    }

                    @Override
                    public void run() {
                        try {
                            BigInteger p = BigInteger.ONE;
                            while (!cancelled) {
                                queue.put(p = p.nextProbablePrime());
                            }
                        } catch (InterruptedException consumed){

                        }
                    }

                    public void cancel() {
                        cancelled = true;
                    }
                    //消费者
                    void consumerPrimes() throws InterruptedException {
                        BlockingQueue<BigInteger> primes = new BlockingQueue<BigInteger>() {
                            @Override
                            public boolean add(BigInteger bigInteger) {

                            }

                            @Override
                            public boolean offer(BigInteger bigInteger) {
                                return false;
                            }

                            @Override
                            public void put(BigInteger bigInteger) throws InterruptedException {

                            }

                            @Override
                            public boolean offer(BigInteger bigInteger, long timeout, TimeUnit unit) throws InterruptedException {
                                return false;
                            }

                            @Override
                            public BigInteger take() throws InterruptedException {
                                return null;
                            }

                            @Override
                            public BigInteger poll(long timeout, TimeUnit unit) throws InterruptedException {
                                return null;
                            }

                            @Override
                            public int remainingCapacity() {
                                return 0;
                            }

                            @Override
                            public boolean remove(Object o) {
                                return false;
                            }

                            @Override
                            public boolean contains(Object o) {
                                return false;
                            }

                            @Override
                            public int drainTo(Collection<? super BigInteger> c) {
                                return 0;
                            }

                            @Override
                            public int drainTo(Collection<? super BigInteger> c, int maxElements) {
                                return 0;
                            }

                            @Override
                            public BigInteger remove() {
                                return null;
                            }

                            @Override
                            public BigInteger poll() {
                                return null;
                            }

                            @Override
                            public BigInteger element() {
                                return null;
                            }

                            @Override
                            public BigInteger peek() {
                                return null;
                            }

                            @Override
                            public int size() {
                                return 0;
                            }

                            @Override
                            public boolean isEmpty() {
                                return false;
                            }

                            @Override
                            public Iterator<BigInteger> iterator() {
                                return null;
                            }

                            @Override
                            public Object[] toArray() {
                                return new Object[0];
                            }

                            @Override
                            public <T> T[] toArray(T[] a) {
                                return null;
                            }

                            @Override
                            public boolean containsAll(Collection<?> c) {
                                return false;
                            }

                            @Override
                            public boolean addAll(Collection<? extends BigInteger> c) {
                                return false;
                            }

                            @Override
                            public boolean removeAll(Collection<?> c) {
                                return false;
                            }

                            @Override
                            public boolean retainAll(Collection<?> c) {
                                return false;
                            }

                            @Override
                            public void clear() {

                            }
                        };
                        BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
                        producer.start();
                        try {
                            while (needMorePrimes()) {
                                consume(primes.take());
                            }
                        } finally {
                            producer.cancel();
                        }
                    }
                }


            }
        };
        BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
        producer.start();
        try {
            while (needMorePrimes()) {
                consume(primes.take());
            }
        } finally {
            producer.cancel();
        }
    }
}
