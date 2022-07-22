public class StartThread {
    public static void main(String[] args)
    {
        io i_o = new io();
        Thread thread = new Thread(i_o);
        thread.start();
    }
}
