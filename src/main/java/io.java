import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class io implements Runnable{
    @Override
    public void run(){
        TextColor color = new TextColor();
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
            String argv;
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
                else if(argv.contains("exit") || argv.contains("shutdown")){
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
                }
                else if(argv.contains("purge")){
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Memory has been purged\n");
                    mem = null;
                    System.gc();
                }
                else if(argv.contains("tobin")){
                    assert mem != null;
                    mem.toBinaryFile();
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Saved to binary file in root dir\n");
                }
                else if(argv.contains("totext")){
                    assert mem != null;
                    interactor.writeBytesToFile();
                    System.out.println(color.colorString("INFO: ", "BLUE", false)+"Saved to text file in root dir\n");
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
                    System.out.println("Shannon Entropy: " + display.entropy(mem.toByteArray()));

                }
                else if(argv.contains("loadfile")){
                    String path = argv.split(" ")[1];
                    mem = interactor.fromFile(path);
                    if(mem != null){
                        System.out.println(color.colorString("\nINFO: ", "BLUE", false)+"file "+path+" loaded");
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
        } catch (MemorySizeInitializationException | MemoryAddressDoesNotExistException | IOException |
                 NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
