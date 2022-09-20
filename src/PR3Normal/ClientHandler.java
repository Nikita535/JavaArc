package PR3Normal;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public static ArrayList<String> clientMessages=new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private final Timer myTimer = new Timer();

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientHandlers.add(this);
//            broadCastMessage();
            allMessages();
        } catch (IOException e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    @Override
    public void run() {
        String messageFromClient;

        while(socket.isConnected())
        {
            try{
                messageFromClient=bufferedReader.readLine();
                clientMessages.add(messageFromClient);
                broadCastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;
            }
        }
    }


    public void allMessages(){
        myTimer.schedule(new TimerTask() {
            public void run() {
                broadCastMessage(clientMessages.toString());
            }
        }, 0, 15000);
    }
    public void broadCastMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientMessages.isEmpty()) {
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }
    }

    public void removeClientHandler()
    {
        clientHandlers.remove(this);
//        broadCastMessage();
    }

    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter)
    {
        removeClientHandler();
        try
        {
            if (bufferedReader!=null)
            {
                bufferedReader.close();
            }
            if (bufferedWriter!=null)
            {
                bufferedWriter.close();
            }
            if (socket!=null)
            {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}