import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author lihe
 * @Title:
 * @Description:
 * @date 2018/5/22下午4:43
 */
public class HiddenIterator {
    public static final Set<Integer> set = new HashSet<Integer>();
    public synchronized void add (Integer i) {
        set.add(i);
    }

    public synchronized final void remove(Integer i) {
        set.remove(i);
    }

    public void addTenThings() {
        Random r = new Random();
        for (int i = 0; i<10; i++) {
            add(r.nextInt());
        }
        System.out.println("Debug: add ten tings into set" + set);
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                HiddenIterator hi = new HiddenIterator();
                hi.addTenThings();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                HiddenIterator hi = new HiddenIterator();
                hi.addTenThings();
            }
        });
        t1.start();
        t2.start();
    }
}
