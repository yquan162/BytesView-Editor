import java.io.IOException;

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
        Byte bt = memory.getByte(byteOffset);
        bt.changeBit(bitOffset, !bt.returnBit(bitOffset).boolValueOf());
        memory.changeByte(byteOffset, bt);
    }
}
