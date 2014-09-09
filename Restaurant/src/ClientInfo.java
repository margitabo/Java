public class ClientInfo implements java.io.Serializable {
	private String name;
	private String lastName;
	static int num = 0;
	
	private int takePlaces;

	public ClientInfo(Integer takePlaces, String name, String lastName) {
		this.name = name;
		this.lastName = lastName;
		this.takePlaces = takePlaces;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}
	public int getPlaces() {
		return takePlaces;
	}

	public void setName(int num){
		this.name = num  + " " + name;
	}
}