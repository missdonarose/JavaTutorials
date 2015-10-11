package com.tutorial.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerExample {

	private static final String DONE= "DONE";
	private static final String NO_MESSAGES = "No messages";
	
	/**
	 * IMplementation using locks
	 * @author dhanil
	 *
	 */
	public static class LockedDropBox
	{
		private List<String> messages = new ArrayList<String>();
		
		private final Lock lock = new ReentrantLock();
		public static Boolean locked = false;
		
		public Boolean getlock()
		{
			locked = lock.tryLock();
			System.out.println("locked. lock ="+locked+" by "+Thread.currentThread().getName());
			return locked;
		}
		
		public void releaseLock()
		{
			if(locked)
				try{
					lock.unlock();
					locked = false;
					System.out.println("Unlocked. lock ="+locked+" by "+Thread.currentThread().getName());
				}catch(IllegalMonitorStateException ime)
			{
					System.out.println("Exception while Unlocking. lock ="+locked+" by "+Thread.currentThread().getName());
			ime.printStackTrace();		
			}
			else
				System.out.println("Lock =false already "+Thread.currentThread().getName());
			
		}
		
		public void put(String message)
		{
			this.messages.add(message);
		}
		
		public String get()
		{
			String gotMessage;
			if(this.messages.size()>0)
			{
				gotMessage = messages.remove(0);
				return gotMessage;
			}
			else
				return NO_MESSAGES;
				
		}
	}
	
	public class DropBox
	{
		boolean empty = true;
		String message=null;
		
		/**
		 * Put new message only if empty. And set empty=false;
		 * @param newMessage
		 */
		public synchronized void put(String newMessage)
		{
			while(!empty)
			{
				try{
					wait(1000);
				}catch(InterruptedException ie)
				{
					System.out.println("I have been interrupted: "+Thread.currentThread().getName());
				}
			}
			this.message = newMessage;
			empty = false;
		}
		
		/**
		 * Retrieves message. Waits if empty
		 * @return
		 */
		public synchronized String get()
		{
			while(empty)
			{
				try{
					wait(1000);
				}catch(InterruptedException ie)
				{
					System.out.println("I have been interrupted: "+Thread.currentThread().getName());
				}
			}
			empty = true;
			return this.message;
		}
		
	}
	
	public class Producer implements Runnable
	{
		LockedDropBox dropBox;
		String[] messages = {"One","Two","Three"};
		
		public Producer(LockedDropBox dropBox)
		{
			this.dropBox = dropBox;
		}

		@Override
		public void run() 
		{
			
		for(int i=0;i<3;)
		{
			
				
				if(dropBox.lock.tryLock())
				{
					System.out.println("Producer acquired lock and putting message");
					dropBox.put(messages[i]);
					i++;
					dropBox.lock.unlock();
					synchronized(dropBox){
					try{
						dropBox.notifyAll();
						System.out.println("Notified");
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		
		dropBox.put(DONE);
		System.out.println("Completed putting all messages by"+Thread.currentThread().getName());
		}
		
		
	}
	
	public class Consumer implements Runnable
	{
		LockedDropBox dropBox;
		
		public Consumer(LockedDropBox dropBox)
		{
			this.dropBox = dropBox;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String message = null;
			
			
				while(!DONE.equals(message))
				{
				
				if(dropBox.lock.tryLock())
				{
					message = dropBox.get();
					dropBox.lock.unlock();
				}else{
					try{
						 synchronized(dropBox){
						 System.out.println("Consumer waiting because no messages encountered");
						 dropBox.wait(3000);
						 }
					 }catch (InterruptedException e) {
					System.out.println("Consumer: I was woken up. Let me continue work.");		
					 }
				}
				 
				} 
			
			
			System.out.println("Fnished reading all messages."+Thread.currentThread().getName());
		}
		
	}

	public static void main(String... args)
	{
		ProducerConsumerExample example = new ProducerConsumerExample(); 
		LockedDropBox dropBox = new LockedDropBox();
		Thread producer = new Thread(example.new Producer(dropBox));
		producer.start();
		
		Thread consumer = new Thread(example.new Consumer(dropBox));
		consumer.start();
	}
}
