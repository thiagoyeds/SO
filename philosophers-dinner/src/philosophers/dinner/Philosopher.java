package philosophers.dinner;

public class Philosopher implements Runnable {

	private int id;
	private int eatingTime;
	private int thinkingTime;
	private Dinner dinner;
	
	public Philosopher(int id, int eatingTime, int thinkingTime, Dinner dinner) {
		this.id = id;
		this.eatingTime = eatingTime;
		this.thinkingTime = thinkingTime;
		this.dinner = dinner;
		new Thread((Runnable)this, "Philosopher" + id).start();
	}

	@Override
	public void run() {
		while(true) {
			thinking();
			get_cutlery(id);
			eating();
			return_cutlery(id);
		}
	}
	
	private void thinking() {
		try {
			System.out.println("Philosopher " + id + " is thinking");
            Thread.sleep(this.thinkingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	}

	private void get_cutlery(int id) {
		System.out.println("Philosopher " + id + " is trying get cutlery");
		dinner.get_cutlery(id);
	}

	private void eating() {
		try {
			System.out.println("Philosopher " + id + " is eating");
            Thread.sleep(this.eatingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	private void return_cutlery(int id) {
		System.out.println("Philosopher " + id + " is return cutlery");
		dinner.return_cutlery(id);
	}
	
}
