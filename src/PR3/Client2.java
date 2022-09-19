package PR3;

import java.io.*;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8080);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             BufferedReader ois = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Клиент подключился.");

            while (!socket.isOutputShutdown()) {
                if (br.ready()) {

                    String clientCommand = br.readLine();

                    oos.writeUTF(clientCommand);
                    oos.flush();

                    if (clientCommand.equalsIgnoreCase("quit")) {
                        System.out.println("quit...");
                        break;
                    }
                    Thread.sleep(2000);

                    if (ois.read()>-1)
                        System.out.println("Ответ сервера: ["+ois.readLine());
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
