package philosophers.dinner;

public class MainPhilosophersDinner {

	private static int PHILOSOPHERS_NUMBER = 6;
	private static int[] PHILOSOPHERS_EATING_TIME = {100,100,100,100,100,100};
	private static int[] PHILOSOPHERS_THINKING_TIME = {100,100,100,100,100,100};
	
	public static void main(String[] args) {
		
		// Using Semaphore
		Dinner dinner = new DinnerWithSemaphore(PHILOSOPHERS_NUMBER);
		
		// Using Monitor
		//Dinner dinner = new DinnerWithMonitor(PHILOSOPHERS_NUMBER);
		
		for (int i = 0; i < PHILOSOPHERS_NUMBER; i++) {
			new Philosopher(i, PHILOSOPHERS_EATING_TIME[i], PHILOSOPHERS_THINKING_TIME[i], dinner);
		}
	}
	
}
