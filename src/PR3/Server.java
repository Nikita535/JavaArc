package PR3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable{
    private static Socket clientDialog;
    private static ArrayList<String> arrayList=new ArrayList<>();

    public Server(Socket client) {
        Server.clientDialog = client;
    }

    @Override
    public void run() {

        try {
            PrintWriter out = new PrintWriter(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());

            while (!clientDialog.isClosed()) {
                String entry = in.readUTF();

                arrayList.add(entry);
                System.out.println("Сообщение от клиента - " + entry);

                if (entry.equalsIgnoreCase("quit")) {
                    System.out.println("Клиент закрыл канал.");
                    break;
                }



                System.out.println(arrayList.toString());
                out.println(arrayList.toString());

                out.flush();


            }

            in.close();
            out.close();
            clientDialog.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}