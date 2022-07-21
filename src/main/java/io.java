import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Scanner;

public class io {
    public static void main(String[] args){
        String[] commands = {"help", "chmem", "dispmem", "flush", "reinit", "multich", "exit", "checksum", "verbose", "mute", "ejectdisp", "ejectint", "loaddisp", "loadint", "newmem"};
        Memory mem;
        MemoryDisplay display = new MemoryDisplay();
        MemoryInteractor interactor = new MemoryInteractor();
        Scanner sc = new Scanner(System.in);
        boolean verbosity = false;
        boolean matches = false;
        int size = 0;
        try {
            while(size < 1){
                System.out.print("Initialize memory size(min 1): ");
                size = sc.nextInt();
                sc.nextLine();
                if(size < 1){
                    System.out.println("Invalid memory size! (min 1)");
                }
            }
            mem = new Memory(size);
            display.loadMemory(mem);
            interactor.loadMemory(mem);
            System.out.println("Memory has been loaded into display and interactor");
            display.showMemory(verbosity);
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
                else if(argv.contains("reinit")){
                    if(argv.split(" ").length == 1){
                        System.out.println("Missing parameters!");
                    }
                    else{
                        size = Integer.parseInt(argv.split(" ")[1]);
                        display.ejectMemory();
                        interactor.ejectMemory();
                        System.out.println("Memory ejected from display and interactor");
                        mem = new Memory(size);
                        System.out.println("New memory with size " + size + " initialized");
                        display.loadMemory(mem);
                        interactor.loadMemory(mem);
                        System.out.println("Memory has been loaded into display and interactor");
                        display.showMemory(verbosity);
                    }
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
                        }
                        else {
                            System.out.println("Invalid memory size! (min 1)");
                        }
                    }
                }
                else if(argv.contains("help")){
                    System.out.println("help - shows commands");
                    System.out.println("chmem <offset: 00000000> - changes memory at specified address");
                    System.out.println("dispmem - shows contents of memory");
                    System.out.println("flush - fills mem with all zeros");
                    System.out.println("reinit <size> - reinitialize new memory with new size.");
                    System.out.println("multich <addr1,addr2,addr3...> - change multiple addresses with one command");
                    System.out.println("exit - exits the program");
                    System.out.println("checksum - prints the sha256 checksum of memory");
                    System.out.println("verbose - turns on additional information");
                    System.out.println("mute - toggle off verbosity");
                    System.out.println("ejectdisp - eject memory from the display");
                    System.out.println("ejectint - eject memory from the interactor");
                    System.out.println("loaddisp - load current virtual memory instance into display");
                    System.out.println("loadint - load current virtual memory instance into interactor");
                    System.out.println("newmem <size>- replaces the instance of memory with a new one");
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
