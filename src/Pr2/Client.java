package Pr2;

import Pr2.Remote.SquareComparison;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {


    public static final String UNIQUE_BINDING_NAME="server.squareComparison";

    public static void main(String[] args) throws RemoteException, NotBoundException {

        //Получаем реестр удаленных объектов по порту 8080
        final Registry registry = LocateRegistry.getRegistry(8080);

        //Получаем нужный нам объект. Важно, что мы приводим тип к интерфейсу,
        // а не классу, так как работа RMI основана на использовании прокси, который
        //Работает на основе интерфейсов
        SquareComparison squareComparison = (SquareComparison) registry.lookup(UNIQUE_BINDING_NAME);

        System.out.println(squareComparison.squareComparison(1,2,1));

    }
}
