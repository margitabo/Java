import java.rmi.Remote;
import java.rmi.RemoteException;


public interface CalcInterface extends Remote{
	public double add(double a, double b) throws RemoteException;
	 public double subt(double a,double b) throws RemoteException;
	 public double mult(double a,double b) throws RemoteException;
	 public double div(double a,double b) throws RemoteException;

}
