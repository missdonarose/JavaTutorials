package com.tutorial.threading;

public class ThreadOnceAgain {
	
	/**
	 * 
	 * Prints thread name and a status message 
	 * @param status
	 */
	public void printStatus(String status)
	{
		System.out.format("%s: %s\n",Thread.currentThread().getName(),status);
	}
	
	private class WorkerThread implements Runnable
	{
		String[] myMessages = {"My frst message","My second message","My third message","My fourth message"};
		
		public void run()
		{
			printStatus("Hello from me");
			try{
			for(int i=0; i<4;i++)
			{
				Thread.sleep(500);
				printStatus(myMessages[i]);
			}
			}catch(InterruptedException ie)
			{
				System.out.println("I was interrupted");
				return;
			}
		}
	}
	
	public static void main(String... arg) throws InterruptedException
	{
		long waitingTime = 1000;
		long startTime = System.currentTimeMillis();
		
		ThreadOnceAgain mainClass = new ThreadOnceAgain();
		Thread worker = new Thread(mainClass.new WorkerThread());
		mainClass.printStatus("Hello from me");
		worker.start();
		
		do{
		worker.join(1000);
		mainClass.printStatus("Completed waiting");
		}while((System.currentTimeMillis() - startTime) <waitingTime);
		
		//Waiting time exhausted
		if(worker.isAlive())
			worker.interrupt();
		
		worker.join();
		mainClass.printStatus("Finished waiting");
	}

}
