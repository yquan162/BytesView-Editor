import java.io.IOException;
import java.net.Socket;

public class StartThread {
    public static void main(String[] args){
        io i_o = new io();
        Thread thread = new Thread(i_o);
        thread.start();
    }
}
