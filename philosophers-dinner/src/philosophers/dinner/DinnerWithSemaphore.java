package philosophers.dinner;

import java.util.concurrent.Semaphore;

public class DinnerWithSemaphore implements Dinner {

	private Semaphore mutex;
	private PhilosopherState[] philosophersState;
	private Semaphore[] philosophers;
	private int philosopherNumber;
	
	public DinnerWithSemaphore(int philosopherNumber) {
		this.philosopherNumber = philosopherNumber;
		this.mutex = new Semaphore(1);
		this.philosophersState = new PhilosopherState[this.philosopherNumber];
		this.philosophers = new Semaphore[this.philosopherNumber];
		
		for(int i = 0; i < this.philosopherNumber; i++) {
			this.philosophersState[i] = PhilosopherState.THINKING;
			this.philosophers[i] = new Semaphore(0);
		}
		
	}
	
	private int fromTheRight(int id) {
		return (id + 1) % this.philosopherNumber;
	}
	
	private int fromTheLeft(int id) {
		return (this.philosopherNumber + id - 1) % this.philosopherNumber;
	}
	
	public void get_cutlery(int philosopherId) {
		mutex.release();
		philosophersState[philosopherId] = PhilosopherState.HUNGRY;
		
		int philosopherIdFromTheLeft = fromTheLeft(philosopherId);
		int philosopherIdFromTheRight = fromTheRight(philosopherId);
		
		if(philosophersState[philosopherIdFromTheLeft] != PhilosopherState.EATING
				&& philosophersState[philosopherIdFromTheRight] != PhilosopherState.EATING) {
			philosophersState[philosopherId] = PhilosopherState.EATING;
			philosophers[philosopherId].release();
	        
		}
		try {
			mutex.acquire();
        }catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
		try {
			philosophers[philosopherId].acquire();
			System.out.println("Philosopher " + philosopherId + " is get cutlery with success");
		}catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
		
			
	}

	@Override
	public void return_cutlery(int philosopherId) {
		mutex.release();
		philosophersState[philosopherId] = PhilosopherState.THINKING;
		
		int philosopherIdFromTheLeft = fromTheLeft(philosopherId);
		int philosopherIdFromTheLeft2 = fromTheLeft(philosopherIdFromTheLeft);
		int philosopherIdFromTheRight = fromTheRight(philosopherId);
		int philosopherIdFromTheRight2 = fromTheRight(philosopherIdFromTheRight);
		
		if(philosophersState[philosopherIdFromTheLeft] == PhilosopherState.HUNGRY
				&& philosophersState[philosopherIdFromTheLeft2] != PhilosopherState.EATING) {
			philosophersState[philosopherIdFromTheLeft] = PhilosopherState.EATING;
			philosophers[philosopherIdFromTheLeft].release();
	        
		}
		if(philosophersState[philosopherIdFromTheRight] == PhilosopherState.HUNGRY
				&& philosophersState[philosopherIdFromTheRight2] != PhilosopherState.EATING) {
			philosophersState[philosopherIdFromTheRight] = PhilosopherState.EATING;
			philosophers[philosopherIdFromTheRight].release();
	        
		}
		try {
			mutex.acquire();
        }catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
	}
	
}
