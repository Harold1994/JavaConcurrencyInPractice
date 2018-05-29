import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author lihe
 * @Title: CellularAotumata
 * @Description: 通过cyclicbarrier协调细胞自动衍生系统中的运算
 * @date 2018/5/23下午2:36
 */
public class CellularAotumata {
    private final Board mainboard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAotumata(Board mainboard) {
        this.mainboard = mainboard;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count,
                new Runnable() {
                    @Override
                    public void run() {
                        mainboard.commitNewValues();
                    }
                });
        this.workers = new Worker[count];
        for (int i=0; i<count; i++)
            workers[i] = new Worker(mainboard.getSubBoard(count, i));
    }

    private class Worker implements Runnable{
        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            while(!board.hasConverged()) {
                for (int i = 0; i < board.getMaxX(); i++){
                    for (int j = 0; i < board.getMaxY(); j++) {
                        board.setNewValue(x,y, compute(x,y));
                    }
                }
                try {
                    barrier.await();
                } catch (InterruptedException ex) {
                    return;
                }
                 catch (BrokenBarrierException ex) {
                    return ;
                 }
            }
        }
    }

    public void start() {
        for (int i = 0; i< workers.length; i++) {
            new Thread(workers[i]).start();
        }
        mainboard.waitForConvergence();
    }
}

