import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author harold
 * @Title: ReaderThread
 * @Description: 通过改写interrupt方法将给标准的取消操作封装在Thread中
 * @date 2018/6/1下午1:12
 */
public class ReaderThread  extends Thread {
    private final Socket socket;
    private final InputStream inputStream;

    public ReaderThread(Socket socket, InputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
    }

    @Override
    public void interrupt() {//既能处理标准中断，又能关闭底层套接字
        try {
            socket.close();
        } catch (IOException e) {
        }
        finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        byte [] mbuffer = new byte[128];
        while (true) {
            try {
                int count = inputStream.read(mbuffer);
                if (count < 0)
                    break;
                else if (count > 0) {
                    processBuffer(buf, count);
                }
            } catch (IOException e) {
                /*
                允许线程退出
                 */
            }
        }
    }
}
