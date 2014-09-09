import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class CalcImplementation extends UnicastRemoteObject implements CalcInterface {
	public CalcImplementation() throws RemoteException {
		super();
		//System.out.println("Initializing Server");
		//try{
		//	Naming.rebind(name, this);
		//}
		//catch(Exception e){
		//	System.out.println("Exception occurred: " + e);
		//}
	}

	public double add(double a, double b) throws RemoteException{
		return (a + b);
	}

	public double subt(double a, double b) throws RemoteException{
		return (a - b);
	}

	public double mult(double a, double b) throws RemoteException{
		return (a * b);
	}

	public double div(double a, double b) throws RemoteException{
		return (a / b);
	}
}