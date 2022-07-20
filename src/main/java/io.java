import java.io.IOException;

public class io {
    public static void main(String[] args){
        Memory mem;
        MemoryDisplay display = new MemoryDisplay();
        MemoryInteractor interactor = new MemoryInteractor();
        try {
            mem = new Memory(64);
            display.loadMemory(mem);
            display.showMemory();
            interactor.loadMemory(mem);
            interactor.changeMemory("00000036");
            display.showMemory();
        } catch (MemorySizeInitializationException | MemoryAddressDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
