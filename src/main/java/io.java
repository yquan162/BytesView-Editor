import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class io implements Runnable{
    @Override
    public void run(){
        Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler());
        String[] commands = {"help", "chmem", "dispmem", "flush", "multich"
                , "exit", "checksum", "verbose", "mute", "ejectdisp"
                , "ejectint", "loaddisp", "loadint", "newmem","identdisp"
                ,"identint"};
        Memory mem = null;
        MemoryDisplay display = new MemoryDisplay();
        MemoryInteractor interactor = new MemoryInteractor();
        Scanner sc = new Scanner(System.in);
        boolean verbosity = false;
        boolean matches = false;
        int size = 0;
        System.out.println("Thread started, please initialize some memory with \"newmem\"");
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
                if(argv.contains("chmem")){
                    if(argv.split(" ").length == 1){
                        System.out.println("Missing parameters!");
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
                        System.out.println("Missing parameters!");
                    }
                    else {
                        String[] offsets = argv.split(" ")[1].split(",");
                        for(String offset:offsets){
                            interactor.changeMemory(offset, verbosity);
                        }
                    }
                }
                else if(argv.contains("exit")){
                    System.exit(0);
                }
                else if(argv.contains("verbose")){
                    System.out.println("Verbosity turned on");
                    verbosity = true;
                }
                else if(argv.contains("mute")){
                    System.out.println("Verbosity turned off");
                    verbosity = false;
                }
                else if(argv.contains("checksum")){
                    System.out.println("SHA-256 Checksum: " + display.sha256());
                }
                else if(argv.contains("ejectdisp")){
                    display.ejectMemory();
                    System.out.println("Memory in display has been ejected");
                }
                else if(argv.contains("ejectint")){
                    interactor.ejectMemory();
                    System.out.println("Memory in interactor has been ejected");
                }
                else if(argv.contains("loaddisp")){
                    display.loadMemory(mem);
                    System.out.println("Memory in display has been loaded");
                }
                else if(argv.contains("loadint")){
                    interactor.loadMemory(mem);
                    System.out.println("Memory in interactor has been loaded");
                }
                else if(argv.contains("newmem")){
                    if(argv.split(" ").length == 1){
                        System.out.println("Missing parameters!");
                    }
                    else {
                        int sizeb = Integer.parseInt(argv.split(" ")[1]);
                        if(sizeb > 0){
                            mem = new Memory(sizeb);
                            System.out.println("New memory with size " + sizeb + " initialized");
                            if(argv.split(" ")[argv.split(" ").length-1].contains("-a")){
                                System.out.println("Auto swapping of display and interactor is on");
                                display.ejectMemory();
                                interactor.ejectMemory();
                                System.out.println("Memory ejected from display and interactor");
                                mem = new Memory(sizeb);
                                display.loadMemory(mem);
                                interactor.loadMemory(mem);
                                System.out.println("Memory has been loaded into display and interactor");
                                display.showMemory(verbosity);
                            }
                            else{
                                System.out.println("WARN: Display and Interactor have not been switched to new memory.");
                            }
                        }
                        else {
                            System.out.println("Invalid memory size! (min 1)");
                        }
                    }
                }
                else if(argv.contains("identdisp")){
                    if(!display.sha256().equals("")){
                    System.out.println("Display memory checksum: "+display.sha256());
                    }
                }
                else if(argv.contains("identint")){
                    if(!interactor.sha256().equals("")){
                        System.out.println("Interactor memory checksum: "+interactor.sha256());
                    }
                }
                else if(argv.contains("help")){
                    System.out.println("help - shows commands");
                    System.out.println("chmem <offset: 00000000> - changes memory at specified address");
                    System.out.println("dispmem - shows contents of memory");
                    System.out.println("flush - fills mem with all zeros");
                    System.out.println("multich <addr1,addr2,addr3...> - change multiple addresses with one command");
                    System.out.println("exit - exits the program");
                    System.out.println("checksum - prints the sha256 checksum of memory");
                    System.out.println("verbose - turns on additional information");
                    System.out.println("mute - toggle off verbosity");
                    System.out.println("ejectdisp - eject memory from the display");
                    System.out.println("ejectint - eject memory from the interactor");
                    System.out.println("loaddisp - load current virtual memory instance into display");
                    System.out.println("loadint - load current virtual memory instance into interactor");
                    System.out.println("newmem <size> <-a (auto reattaching of display and interactor)>- replaces the instance of memory with a new one");
                    System.out.println("identdisp - shows checksum of currently attached memory");
                    System.out.println("identint - shows checksum of currently attached memory");
                }
                else{
                    System.out.println("The specified command \"" + argv + "\" does not exist.\nType \"help\" for more information\n");
                    for(String match:commands){
                        if(match.contains(argv)){
                            matches = true;
                            break;
                        }
                    }
                    if(matches){
                        System.out.println("You may be trying to invoke the following commands:");
                        for(String s:commands){
                            if(s.contains(argv)){
                                System.out.println(s);
                            }
                        }
                    }
                    matches = false;

                }
            }
        } catch (MemorySizeInitializationException | MemoryAddressDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
