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

    public ClientHandler(Socket socket) throws IOException {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientHandlers.add(this);
                allMessagesFor15Sec();
        } catch (IOException e) {
            closeAllFlow(socket,bufferedReader,bufferedWriter);
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
                serverAnswer(messageFromClient);
            } catch (IOException e) {
                try {
                    closeAllFlow(socket,bufferedReader,bufferedWriter);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
        }
    }


    public void allMessagesFor15Sec(){
        myTimer.schedule(new TimerTask() {
            public void run() {
                try {
                    serverAnswer(clientMessages.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 15000);
    }
    
    public void serverAnswer(String message) throws IOException {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientMessages.isEmpty()) {
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeAllFlow(socket,bufferedReader,bufferedWriter);
            }
        }
    }


    public void closeAllFlow(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter) throws IOException {
        clientHandlers.remove(this);
        
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
        } 
    }
