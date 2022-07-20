import java.io.IOException;
import java.util.Scanner;

public class io {
    public static void main(String[] args){
        Memory mem;
        MemoryDisplay display = new MemoryDisplay();
        MemoryInteractor interactor = new MemoryInteractor();
        Scanner sc = new Scanner(System.in);
        System.out.print("Initialize memory size: ");
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
                argv = sc.nextLine();
                if(argv.contains("chmem")){
                    interactor.changeMemory(argv.split(" ")[1]);
                }
                else if(argv.contains("dispmem")){
                    display.showMemory();
                }
                else if(argv.contains("help")){
                    System.out.println("help - shows commands");
                    System.out.println("chmem <offset: 00000000> - changes memory at specified address");
                    System.out.println("dispmem - shows contents of memory");
                }
                else{
                    System.out.println("The specified command " + argv + " does not exist.\nTry \"help\" for more information");
                }
            }
        } catch (MemorySizeInitializationException | MemoryAddressDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
