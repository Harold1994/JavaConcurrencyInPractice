import com.sun.scenario.effect.ImageData;
import sun.jvm.hotspot.debugger.Page;

import java.util.concurrent.*;

/**
 * @author harold
 * @Title:
 * @Description: 使用CompletionService实现页面渲染
 * @date 2018/5/24上午11:17
 */
public class Renderer {
    public final ExecutorService executor;
    Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        List<ImageInfo> info = scanForImageInfo(source);
        CompletionService <ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
        for (final ImageInfo imageInfo : info) {
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });
        }
        renderText(source);
        try {
            for (t = 0, n = info.size(); t<n; t++) {
                Future<ImageData> f = completionService.take();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }
    //在指定时间内获取广告时间
    Page renderPageWithAd() throws InterruptedException {
        long endNanos = System.nanoTime() + TIME_BUGET;
        Future<Ad> f = executor.submit(new FetchAdTask());
        //在等待广告时显示页面
        Page page = renderPageBody();
        Ad ad;
        try {
            //只等待指定时间长度
            long timeLeft = endNanos - System.nanoTime();
            ad = f.get(timeLeft, TimeUnit.NANOSECONDS);
        } catch (ExecutionException e) {
            ad = DEFAULT_AD;
        } catch (TimeoutException e) {
            ad = DEFAULT_AD;
            f.cancel(true);
        }
        page.setAd(ad);
        return page;
    }
}
