import java.util.Hashtable;
import java.util.Vector;

public class NoVisibility {
	private static int number;
	private static boolean ready;
	private static class ReaderThread extends Thread {
		@Override
		public void run() {
			while(!ready)
				Thread.yield();
			System.out.println(number);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new ReaderThread().start();
		ready = true;
        number = 5;
	}
}
