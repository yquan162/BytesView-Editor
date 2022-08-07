import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class io implements Runnable{
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    @Override
    public void run(){
        TextColor color = new TextColor();
        try {
            socket = new Socket("localhost", 6969);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(color.colorString("INFO: ", "BLUE", false)+"Connection OK");
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(in.readUTF());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler());
        String[] commands = {"help", "chmem", "dispmem", "flush", "multich"
                , "exit", "verbose", "mute", "ejectdisp", "ejectint"
                , "loaddisp", "loadint", "newmem","identdisp","identint"
                , "hookmem", "purge", "status"};
        Memory mem = null;
        MemoryDisplay display = new MemoryDisplay();
        MemoryInteractor interactor = new MemoryInteractor();
        Scanner sc = new Scanner(System.in);
        boolean verbosity = false;
        boolean matches = false;
        System.out.println(color.colorString("INFO: ", "BLUE", false)+"Thread started, please initialize some memory with \"newmem\"");
        try {
            String argv = "";
            while(true){
                if(verbosity){
                    System.out.print("<-v>");
                }
                else {
                    System.out.print(">");
                }
                argv = sc.nextLine().toLowerCase();
                out.writeUTF(argv);
                System.out.println(in.readUTF());
                if(argv.contains("chmem")){
                    if(argv.split(" ").length == 1){
                        System.out.println(color.colorString("WARN: ", "YELLOW", false)+"Missing parameters!");
                    }
                    else{
                        interactor.changeMemory(argv.split(" ")[1], verbosity);
                    }
                }
                else if(argv.contains("dispmem")){
                    display.showMemory(verbosity);
                }
                else if(argv.contains("flush")){
                    interactor.flushMemory(verbosity);
                }
                else if(argv.contains("multich")){
                    if(argv.split(" ").length == 1){
                        System.out.println(color.colorString("WARN: ", "YELLOW", false)+"Missing parameters!");
                    }
                    else {
                        String[] offsets = argv.split(" ")[1].split(",");
                        for(String offset:offsets){
                            interactor.changeMemory(offset, verbosity);
                        }
                    }
                }
                else if(argv.contains("exit")){
                    in.close();
                    out.close();
                    socket.close();
                    System.exit(0);
                }
                else if(argv.contains("verbose")){
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Verbosity turned on");
                    verbosity = true;
                }
                else if(argv.contains("mute")){
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Verbosity turned off");
                    verbosity = false;
                }
                else if(argv.contains("ejectdisp")){
                    display.ejectMemory();
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Memory in display has been ejected");
                }
                else if(argv.contains("ejectint")){
                    interactor.ejectMemory();
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Memory in interactor has been ejected");
                }
                else if(argv.contains("loaddisp")){
                    display.loadMemory(mem);
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Memory in display has been loaded");
                }
                else if(argv.contains("loadint")){
                    interactor.loadMemory(mem);
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Memory in interactor has been loaded");
                }
                else if(argv.contains("newmem")){
                    if(argv.split(" ").length == 1){
                        System.out.println(color.colorString("WARN: ", "YELLOW", false)+"Missing parameters!");
                    }
                    else {
                        int sizeb = Integer.parseInt(argv.split(" ")[1]);
                        if(sizeb > 0){
                            mem = new Memory(sizeb);
                            System.out.println(color.colorString("INFO: ", "BLUE", false)+"New memory with size " + sizeb + " initialized");
                            if(argv.split(" ")[argv.split(" ").length-1].contains("-a")){
                                System.out.println(color.colorString("INFO: ", "BLUE", false)+"Auto swapping of display and interactor is on");
                                display.ejectMemory();
                                interactor.ejectMemory();
                                System.out.println(color.colorString("INFO: ", "BLUE", false)+"Memory ejected from display and interactor");
                                mem = new Memory(sizeb);
                                display.loadMemory(mem);
                                interactor.loadMemory(mem);
                                System.out.println(color.colorString("INFO: ", "BLUE", false)+"Memory has been loaded into display and interactor\n");
                                display.showBytesMemory();
                            }
                            else{
                                System.out.println(color.colorString("WARN: ", "YELLOW", false)+"Display and Interactor have not been switched to new memory.");
                            }
                        }
                        else {
                            System.out.println(color.colorString("WARN: ", "YELLOW", false)+"Invalid memory size! (min 1)");
                        }
                    }
                } else if (argv.contains("showbytes")) {
                    display.showBytesMemory();
                } else if(argv.contains("identdisp")){
                    if(!display.sha256().equals("")){
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Display memory checksum: "+display.sha256());
                    }
                }
                else if(argv.contains("identint")){
                    if(!interactor.sha256().equals("")){
                        System.out.println(color.colorString("INFO: ", "BLUE", false)+"Interactor memory checksum: "+interactor.sha256());
                    }
                }
                else if(argv.contains("hookmem")){
                    display.ejectMemory();
                    interactor.ejectMemory();
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Memory ejected from display and interactor");
                    display.loadMemory(mem);
                    interactor.loadMemory(mem);
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Memory has been loaded into display and interactor\n");
                    display.showBytesMemory();
                }
                else if(argv.contains("purge")){
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Memory has been purged\n");
                    mem = null;
                }
                else if(argv.contains("status")){
                    System.out.println(color.colorString("\nINFO: ", "BLUE", false)+"\nDisplay\n-----------------------");
                    System.out.println("isEmpty: " + display.isEmpty());
                    if(!display.isEmpty()){
                        System.out.println("Memory checksum: " + display.sha256());
                    }
                    System.out.println("\nInteractor\n-----------------------");
                    System.out.println("isEmpty: " + interactor.isEmpty());
                    if(!interactor.isEmpty()){
                        System.out.println("Memory checksum: " + interactor.sha256());
                    }
                    if(mem != null){
                        System.out.println("\nCurrent memory checksum: " + mem.sha256());
                    }
                    else {
                        System.out.println(color.colorString("\nWARN: ", "YELLOW", false)+"There is no memory that has been initialized.\n");
                    }

                }
                else if(argv.contains("help")){
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"\nhelp - shows commands");
                    System.out.println("chmem <offset: 00000000> - changes memory at specified address");
                    System.out.println("dispmem - shows contents of memory");
                    System.out.println("flush - fills mem with all zeros");
                    System.out.println("multich <addr1,addr2,addr3...> - change multiple addresses with one command");
                    System.out.println("exit - exits the program");
                    System.out.println("verbose - turns on additional information");
                    System.out.println("mute - toggle off verbosity");
                    System.out.println("ejectdisp - eject memory from the display");
                    System.out.println("ejectint - eject memory from the interactor");
                    System.out.println("loaddisp - load current virtual memory instance into display");
                    System.out.println("loadint - load current virtual memory instance into interactor");
                    System.out.println("newmem <size> <-a (auto reattaching of display and interactor)>- replaces the instance of memory with a new one");
                    System.out.println("identdisp - shows checksum of currently attached memory");
                    System.out.println("identint - shows checksum of currently attached memory");
                    System.out.println("hookmem - detaches then reattaches memory");
                    System.out.println("purge - discards active memory");
                    System.out.println("status - shows the status of the memory and interfaces");
                }
                else{
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"The specified command \"" + argv + "\" does not exist.\nType \"help\" for more information\n");
                    for(String match:commands){
                        if(match.contains(argv)){
                            matches = true;
                            break;
                        }
                    }
                    if(matches){
                        System.out.println(color.colorString("INFO: ", "BLUE", false)+"You may be trying to invoke the following commands:");
                        for(String s:commands){
                            if(s.contains(argv)){
                                System.out.println(s);
                            }
                        }
                    }
                    matches = false;

                }
            }
        } catch (MemorySizeInitializationException | MemoryAddressDoesNotExistException | IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
