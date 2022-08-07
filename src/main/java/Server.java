import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    private int port = 6969;
    public Server() throws IOException {
        TextColor color = new TextColor();
        server = new ServerSocket(this.port);
        System.out.println(color.colorString("INFO: ", "BLUE", false)+"Server started on port "+this.port);
        System.out.println(color.colorString("INFO: ", "BLUE", false)+"Connection waiting...");
        socket = server.accept();
        System.out.println(color.colorString("INFO: ", "BLUE", false)+"Connection OK");
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(color.colorString("SERVER: ", "GREEN", false)+"Socket accept");
        String line = "";
        while (!line.equals("shutdown")){
            line = in.readUTF();
            System.out.println(color.colorString("INFO: ", "BLUE", false)+"In: "+ line);
            out.writeUTF(color.colorString("SERVER: ", "GREEN", false)+"Received");
        }
        System.out.println(color.colorString("INFO: ", "BLUE", false)+"Closing connection");
        socket.close();
        in.close();
        out.close();
        System.exit(0);
    }
    public static void main(String args[]) throws IOException {
        Server server = new Server();
    }
}
