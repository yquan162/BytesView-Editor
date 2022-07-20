public class MemoryDisplay {
    Memory memory;
    public MemoryDisplay(Memory mem){
        this.memory = mem;
    }
    public MemoryDisplay(){
        this.memory = null;
    }
    public void loadMemory(Memory mem){
        this.memory = mem;
    }
    public void ejectMemory(){
        this.memory = null;
    }
    public void showMemory() throws MemoryAddressDoesNotExistException {
        System.out.println("Address       0 1 2 3 4 5 6 7");
        for(int i = 0; i < this.memory.size(); i++){
            System.out.println(String.format("0x%08X", i) + "    " + this.memory.getByte(i).showByte());
        }
    }
}
