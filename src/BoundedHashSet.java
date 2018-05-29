import sun.plugin2.os.windows.SECURITY_ATTRIBUTES;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * @author harold
 * @Title: BoundedHashSet
 * @Description: 使用Semaphore为容器设置边界
 * @date 2018/5/23下午1:59
 */
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                sem.release();
            }
        }
    }

    public boolean remove(Object obj) {
        boolean wasRemoved = set.remove(obj);
        if (wasRemoved) {
            sem.release();
        }
        return wasRemoved;
    }
}
