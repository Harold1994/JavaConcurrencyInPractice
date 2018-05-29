import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * @author lihe
 * @Title: PrimeProducer
 * @Description: 通过中断来取消
 * @date 2018/5/29下午6:48
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            //显式执行检测可以使代码对中断有更高的响应性
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
           //允许线程退出
        }
    }

    public void cancel() {
        interrupt();
    }
}
