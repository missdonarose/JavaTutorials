package com.tutorial.threading;

public class BadThreads {

	private static class MessageContainer{
		private static String message;
		
		static synchronized String getMessage()
		{
			return message;
		}
		
		static synchronized void setMessage(String msg)
		{
			message = msg;
		}
	}
    

    private static class CorrectorThread
        extends Thread {

        public void run() {
            try {
                sleep(1000); 
            } catch (InterruptedException e) {}
            // Key statement 1:
            MessageContainer.setMessage("Key statements 1"); 
        }
    }

    public static void main(String args[])
        throws InterruptedException {

       Thread corrector = (new CorrectorThread());
       corrector.start();
       Thread.sleep(1000);
       // message = "Key statement 2.";
       //corrector.join();
        // Key statement 2:
        System.out.println(MessageContainer.getMessage());
    }
}