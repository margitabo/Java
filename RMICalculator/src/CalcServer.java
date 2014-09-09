import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalcServer {
	public static void main(String args[]) {
		try {
			//create a local instance of the object
			//CalcImplementation server = new CalcImplementation("calcServer");
			CalcImplementation server = new CalcImplementation();
			Naming.rebind("calcServer", server);
			System.out.println("Server Ready");
		} catch (RemoteException RE) {
			System.out.println("Remote Server Error:" + RE.getMessage());
			System.exit(0);
	} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}