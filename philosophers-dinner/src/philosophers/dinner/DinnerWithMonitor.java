package philosophers.dinner;

public class DinnerWithMonitor implements Dinner {

	private PhilosopherState[] philosophersState;
	private Integer[] philosophers;
	private int philosopherNumber;
	
	public DinnerWithMonitor(int philosopherNumber) {
		this.philosopherNumber = philosopherNumber;
		this.philosophersState = new PhilosopherState[this.philosopherNumber];
		this.philosophers = new Integer[this.philosopherNumber];
		
		for(int i = 0; i < this.philosopherNumber; i++) {
			this.philosophersState[i] = PhilosopherState.THINKING;
			this.philosophers[i] = new Integer(0);
		}
	}
	
	private int fromTheRight(int id) {
		return (id + 1) % this.philosopherNumber;
	}
	
	private int fromTheLeft(int id) {
		return (this.philosopherNumber + id - 1) % this.philosopherNumber;
	}
	
	public void get_cutlery(int philosopherId) {
		philosophersState[philosopherId] = PhilosopherState.HUNGRY;
		
		int philosopherIdFromTheLeft = fromTheLeft(philosopherId);
		int philosopherIdFromTheRight = fromTheRight(philosopherId);
		
		synchronized (philosophers[philosopherId]) {
			if(philosophersState[philosopherIdFromTheLeft] != PhilosopherState.EATING
					&& philosophersState[philosopherIdFromTheRight] != PhilosopherState.EATING) {
				philosophersState[philosopherId] = PhilosopherState.EATING;
			}
			
			while(philosophersState[philosopherId] != PhilosopherState.EATING) {
				try {
                    this.philosophers[philosopherId].wait();
                    System.out.println("Philosopher " + philosopherId + " is get cutlery with success");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
		}
	}

	@Override
	public void return_cutlery(int philosopherId) {
		philosophersState[philosopherId] = PhilosopherState.THINKING;
		
		int philosopherIdFromTheLeft = fromTheLeft(philosopherId);
		int philosopherIdFromTheLeft2 = fromTheLeft(philosopherIdFromTheLeft);
		int philosopherIdFromTheRight = fromTheRight(philosopherId);
		int philosopherIdFromTheRight2 = fromTheRight(philosopherIdFromTheRight);
		
		if(philosophersState[philosopherIdFromTheLeft] == PhilosopherState.HUNGRY
				&& philosophersState[philosopherIdFromTheLeft2] != PhilosopherState.EATING) {
			philosophersState[philosopherIdFromTheLeft] = PhilosopherState.EATING;
			synchronized (philosophers[philosopherIdFromTheLeft]) {
				philosophers[philosopherIdFromTheLeft].notify();
            }
		}
		if(philosophersState[philosopherIdFromTheRight] == PhilosopherState.HUNGRY
				&& philosophersState[philosopherIdFromTheRight2] != PhilosopherState.EATING) {
			philosophersState[philosopherIdFromTheRight] = PhilosopherState.EATING;
			synchronized (philosophers[philosopherIdFromTheRight]) {
				philosophers[philosopherIdFromTheRight].notify();
            }
		}
	}

}
