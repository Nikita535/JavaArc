package Pr2.Remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Класс Remote - просто маркер, который показывает, что методы данного интерфейса могут быть вызваны удаленно
//Все методы должны обязательно кидать исключение RemoteException
public interface SquareComparison extends Remote {

    String squareComparison(int a,int b,int c) throws RemoteException;
}
