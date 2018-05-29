import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author lihe
 * @Title:
 * @Description:  构建可伸缩缓存
 * @date 2018/5/23下午3:47
 */
interface Computerable<A, V> {
    V compute(A arg) throws InterruptedException;
}

class ExpensiveFunction implements Computerable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        //再经过长时间计算后
        return new BigInteger(arg);
    }
}

//使用HashMap和同步机制来初始化缓存
public class Memorizer1 <A, V> implements Computerable<A, V> {
    private final Map<A,V> cache = new HashMap<A,V>();
    private final Computerable<A, V> c;

    public Memorizer1(Computerable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return  result;
    }
}
//用ConcurrentHashMap代替HashMap
public class Memorizer2<A, V> implements Computerable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
    private final Computerable<A, V> c;

    public Memorizer2(Computerable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return  result;
    }
}

//基于FutureTask的Memorizing封装器
public class Memorizer3<A,V> implements Computerable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computerable<A, V> c;

    public Memorizer3(Computerable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> eval = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f = ft;
            cache.put(arg, ft);
            ft.run();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
           throw launderthrowable(e.getCause());
        }
    }
}

public class Memorizer<A, V> implements Computerable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computerable<A, V> c;

    public Memorizer(Computerable<A, V> c) {
        this.c = c;
    }
    @Override
    public V compute(A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg,f);
            } cache (ExecutionException e) {
                throw launderThrowable(e.getCause());
            }
        }
    }
}