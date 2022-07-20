import java.io.IOException;
import java.util.Map;

public class MemoryInteractor {
    Memory memory;
    public MemoryInteractor(Memory mem){
        this.memory = mem;
    }
    public MemoryInteractor(){
        this.memory = null;
    }
    public void loadMemory(Memory mem){
        this.memory = mem;
    }
    public void ejectMemory(){
        this.memory = null;
    }
    public void changeMemory(String offset) throws MemoryAddressDoesNotExistException, IOException {
        int byteOffset = Integer.parseInt(offset.substring(0,7),16 );
        int bitOffset = Integer.parseInt(offset.substring(7));
        Byte bt = this.memory.getByte(byteOffset);
        bt.changeBit(bitOffset, !bt.returnBit(bitOffset).boolValueOf());
        this.memory.changeByte(byteOffset, bt);
        bt = null;
        System.gc();
    }
    public void flushMemory() throws MemorySizeInitializationException, IOException {
        int i = 0;
        for(Map.Entry<Integer, Byte> entry:this.memory.map.entrySet()){
            this.memory.changeByte(i, new Byte());
            i++;
        }
    }
}
