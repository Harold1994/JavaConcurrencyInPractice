package JVM.loadClass;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author harold
 * @Title:
 * @Description: 在单线程Executor中任务发生死锁
 * @date 2018/6/1下午6:19
 */
public class ThreadDeadlocl {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    public class RenderpageTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = executorService.submit(new loadFileTask("head.txt"));
            footer = executorService.submit(new loadFileTask("foot.txt"));
            //将发生死锁——由于任务在等待子任务完成
            return header.get() + footer.get();
        }
    }

}
