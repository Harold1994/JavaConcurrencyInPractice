public class NoVisibility {
	private static int number;
	private static boolean ready;
	private static class ReaderThread extends Thread {
		@Override
		public void run() {
			while(!ready) {
				Thread.yield();
				System.out.println("wait");
			}
			System.out.println(number);
		}
	}

	public static void main(String[] args) {
		new ReaderThread().start();
		number = 5;
		ready = true;
	}
}
