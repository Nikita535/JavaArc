package PR3Normal;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public void sendMessage()
    {
        try{
            Scanner scanner=new Scanner(System.in);

            while (socket.isConnected())
            {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void listenForMessage()
    {
        new Thread(() -> {
            String messageFromGroupChat;
            while (socket.isConnected())
            {
                try {
                    messageFromGroupChat=bufferedReader.readLine();
                    System.out.println(messageFromGroupChat);
                } catch (IOException e) {
                    closeEverything(socket,bufferedReader,bufferedWriter);
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter)
    {
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

    public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        Socket socket1=new Socket("localhost",1234);
        Client client=new Client(socket1);
        client.listenForMessage();
        client.sendMessage();
    }
}
