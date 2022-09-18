package PR3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private static Socket clientDialog;
    private static ArrayList<String> arrayList=new ArrayList<>();

    public Server(Socket client) {
        Server.clientDialog = client;
    }

    @Override
    public void run() {

        try {
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());

            while (!clientDialog.isClosed()) {
                String entry = in.readUTF();

                System.out.println("Сообщение от клиента - " + entry);

                if (entry.equalsIgnoreCase("quit")) {
                    System.out.println("Клиент закрыл канал.");
                    break;
                }

                out.writeUTF("Ответ на - " + entry + " - OK");
                out.writeUTF(arrayList.toString());
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