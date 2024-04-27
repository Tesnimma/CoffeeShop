package ClientSide;

import  java.rmi.*;

public interface TableReservationRemote extends Remote{
    public boolean isTableAvailable(int tableIndex) throws RemoteException;
    public  void ReserveTable(int tableIndex) throws RemoteException;
    public  int GetTableNumber() throws RemoteException;
}
