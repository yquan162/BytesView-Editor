import java.io.IOException;
import java.util.Scanner;

public class io {
    public static void main(String[] args){
        Memory mem;
        MemoryDisplay display = new MemoryDisplay();
        MemoryInteractor interactor = new MemoryInteractor();
        Scanner sc = new Scanner(System.in);
        System.out.print("Initialize memory size(min 1): ");
        int size = sc.nextInt();
        sc.nextLine();
        try {
            mem = new Memory(size);
            display.loadMemory(mem);
            interactor.loadMemory(mem);
            System.out.println("Memory has been loaded into display and interactor");
            display.showMemory();
            String argv = "";
            while(true){
                System.out.print(">");
                argv = sc.nextLine();
                if(argv.contains("chmem")){
                    interactor.changeMemory(argv.split(" ")[1]);
                }
                else if(argv.contains("dispmem")){
                    display.showMemory();
                }
                else if(argv.contains("flush")){
                    interactor.flushMemory();
                }
                else if(argv.contains("reinit")){
                    size = Integer.parseInt(argv.split(" ")[1]);
                    display.ejectMemory();
                    interactor.ejectMemory();
                    System.out.println("Memory ejected from display and interactor");
                    mem = new Memory(size);
                    System.out.println("New memory with size " + size + " initialized");
                    display.loadMemory(mem);
                    interactor.loadMemory(mem);
                    System.out.println("Memory has been loaded into display and interactor");
                    display.showMemory();
                }
                else if(argv.contains("multich")){
                    String[] offsets = argv.split(" ")[1].split(",");
                    for(String offset:offsets){
                        interactor.changeMemory(offset);
                    }
                }
                else if(argv.contains("exit")){
                    System.exit(0);
                }
                else if(argv.contains("help")){
                    System.out.println("help - shows commands");
                    System.out.println("chmem <offset: 00000000> - changes memory at specified address");
                    System.out.println("dispmem - shows contents of memory");
                    System.out.println("flush - fills mem with all zeros");
                    System.out.println("reinit <size> - reinitialize new memory with new size.");
                    System.out.println("multich <addr1,addr2,addr3...> - change multiple addresses with one command");
                    System.out.println("exit - exits the program");
                }
                else{
                    System.out.println("The specified command " + argv + " does not exist.\nType \"help\" for more information");
                }
            }
        } catch (MemorySizeInitializationException | MemoryAddressDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
