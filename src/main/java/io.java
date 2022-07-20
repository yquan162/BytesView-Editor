public class io {
    public static void main(String[] args){
        Memory mem;
        MemoryDisplay display = new MemoryDisplay();
        try {
            mem = new Memory(64);
            display.loadMemory(mem);
            display.showMemory();
        } catch (MemorySizeInitializationException | MemoryAddressDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }
}
