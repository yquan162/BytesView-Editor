public class io {
    public static void main(String[] args){
        Memory mem;
        MemoryDisplay display = new MemoryDisplay();
        try {
            mem = new Memory(8);
            display.loadMemory(mem);
        } catch (MemorySizeInitializationException e) {
            throw new RuntimeException(e);
        }
    }
}
