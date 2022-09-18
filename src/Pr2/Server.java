package Pr2;

import Pr2.Remote.SquareComparisonServer;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    //Уникальное имя удаленного объекта, по которому клиент будет искать сервер
    public static final String UNIQUE_BINDING_NAME="server.squareComparison";

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {

        //Создаём объект для вычисления корней
        final SquareComparisonServer server=new SquareComparisonServer();

        //Registry - реестр удаленных объектов, короче хранилище всех удаленных объектов по порту 8080
        final Registry registry= LocateRegistry.createRegistry(8080);

        //Основная часть, где спрятан весь процесс обращения к серверу, передачи данных и тд.
        Remote stub = UnicastRemoteObject.exportObject(server,0);
        //Кладем ее в реестр удаленных объектов, чтобы могли вызывать методы из него
        registry.bind(UNIQUE_BINDING_NAME,stub);

        //Долгое засыпание main метода сервера для того, чтобы он ждал, пока придёт команда от клиента
        Thread.sleep(Integer.MAX_VALUE);
    }
}
