
public class RunnableImplementer extends Thread{
	protected NumberPrinter go;
	public RunnableImplementer(NumberPrinter g){
		this.go = g;
	}
	
	public void run(){
		for(int i = 200; i<= 299; i = i + 10){
			go.printTenNumbers(i);
			try {
				Thread.sleep((long)(Math.random() * 10));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(this.getName() + " complete");
	}
}
