
public class ThreadExtender extends Thread {
	protected NumberPrinter go;
	public ThreadExtender(String threadName, NumberPrinter g){
		super(threadName);
		this.go = g;
	}
	public void run(){
		for(int i = 100; i<= 199; i = i + 10){
			go.printTenNumbers(i);
			try{
				Thread.sleep((long)(Math.random() * 10));
			}catch(InterruptedException e){}
		}
		System.out.println(this.getName() + " complete");
		
		//Thread.yield();
	}
}
