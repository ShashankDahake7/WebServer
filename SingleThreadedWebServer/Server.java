import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server {
    public void run() throws IOException {
        int port = 8010;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                try {
                    Socket acceptedConnection = serverSocket.accept();
                    System.out.println("Connection accepted from client " + acceptedConnection.getRemoteSocketAddress());

                    try (PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
                         BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()))) {

                        // Read client's message
                        String clientMessage = fromClient.readLine();
                        System.out.println("Received from client: " + clientMessage);

                        // Send response to client
                        toClient.println("Hello from the Server");

                    } catch (IOException ex) {
                        System.out.println("Error handling client connection: " + ex.getMessage());
                    }

                } catch (IOException ex) {
                    System.out.println("Error accepting connection: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}