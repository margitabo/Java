public class Restaurant {
	
	private int currentPlaces;
	private int maxPlaces = 5;

	public Restaurant() {
		currentPlaces = maxPlaces;
	}

	public synchronized boolean getTable(ClientInfo c) {
		int takePlaces = c.getPlaces();

		if(takePlaces > maxPlaces ){ 
			System.out.println(c.getName() + " wants to take table for: " + takePlaces);
			System.out.println("Maximum places in the restaurant is 5!"); 
			return false;
			} 
		
		while (takePlaces > currentPlaces)  {
			    System.out.println(c.getName() + " wants to take table for: " + takePlaces);
				System.out.println(c.getName()+ " waiting for table. Try again later." );
				try {
					wait(1000);
				} catch (InterruptedException e) {
					System.err.println(e);
				}
		}
			System.out.println(c.getName()+ " takes a table for: " + takePlaces + " person.");
			currentPlaces-= takePlaces;
			System.out.println("\t\t >>>>> There are: " + currentPlaces + " free places in the restaurant ");
			return true;
		}

	public synchronized void releaseTable(ClientInfo c) {
		int takePlaces = c.getPlaces();
		System.out.println(c.getName()+ " release table");

		currentPlaces+= takePlaces;
		
		System.out.println("\t\t <<<<< There are: " + currentPlaces + " free places.");
		notifyAll();
	}

}
