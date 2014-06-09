
public class ThreadMaker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NumberPrinter go = new NumberPrinter();
		SimpleGUI gu = new SimpleGUI(go);
		
		ThreadExtender t1 = new ThreadExtender("t1", go);
		t1.start();
		
		RunnableImplementer r1 = new RunnableImplementer(go);
		Thread t = new Thread(r1);
		t.start();
		
		for(int i = 0; i<= 99; i = i + 10){	
			go.printTenNumbers(i);
			try {
				Thread.sleep((long)(Math.random() * 10));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("Main Thread complete");
	


	}

}
