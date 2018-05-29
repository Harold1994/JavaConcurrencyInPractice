import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author  harold
 * @Title:
 * @Description: 使用volatile类型的域来保存取消状态
 * @date 2018/5/29下午5:18
 */

public class PrimeGenerator implements Runnable {
    private final List<BigInteger> primes = new ArrayList<BigInteger>();
    private volatile boolean cancled;
    //持续的枚举素数
    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancled) {
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }
    public void cancel() {
        cancled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<BigInteger>(primes);
    }

    public static void main(String[] args) throws InterruptedException {
        //让素数生成器1s后取消
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }
        System.out.println(generator.get());
    }
}
