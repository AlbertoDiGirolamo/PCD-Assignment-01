package pcd.lab02.lost_updates;


public class Worker extends Thread {
	
	private UnsafeCounter counter;
	private int ntimes;
	private Object lock;

	public Worker(UnsafeCounter c, int ntimes){
		counter = c;
		this.ntimes = ntimes;
	}
	public Worker(UnsafeCounter c, int ntimes, Object lock){
		counter = c;
		this.ntimes = ntimes;
		this.lock = lock;
	}
	
	public void run(){
		synchronized (lock){
			for (int i = 0; i < ntimes; i++){
				counter.inc();
			}
		}

	}
}
