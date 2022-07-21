import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
    public void changeMemory(String offset, boolean verbose) throws MemoryAddressDoesNotExistException, IOException, NoSuchAlgorithmException {
        if(this.memory == null){
            System.out.println("This interactor instance does not have memory loaded!");
            return;
        }
        int byteOffset = Integer.parseInt(offset.substring(0,7),16 );
        int bitOffset = Integer.parseInt(offset.substring(7));
        Byte bt = this.memory.getByte(byteOffset);
        if(verbose){
            String crc = bt.CRC32();
            MemoryDisplay mdisp = new MemoryDisplay(this.memory);
            int state = bt.returnBit(bitOffset).valueOf();
            bt.changeBit(bitOffset, !bt.returnBit(bitOffset).boolValueOf());
            System.out.println("Bit at address 0x" + offset + " changed to " + bt.returnBit(bitOffset).valueOf() +"(was "+ state + ")");
            System.out.println("CRC32 checksum at 0x" + offset.substring(0,7) + "0 has changed to " + bt.CRC32() + "(was " + crc+")");
            System.out.println("SHA-256 checksum of virtual memory has changed.\nType \"checksum\" to see more details.\n");
            crc = null;
            mdisp = null;
        }
        else{
            bt.changeBit(bitOffset, !bt.returnBit(bitOffset).boolValueOf());
        }
        this.memory.changeByte(byteOffset, bt);
        bt = null;
        System.gc();
    }
    public void flushMemory(boolean verbose) throws MemorySizeInitializationException, IOException {
        if(this.memory == null){
            System.out.println("This display interactor does not have memory loaded!");
            return;
        }
        int i = 0;
        for(Map.Entry<Integer, Byte> entry:this.memory.map.entrySet()){
            this.memory.changeByte(i, new Byte());
            i++;
        }
        if(verbose){
            System.out.println("Memory of size "+ this.memory.size()+" flushed.");
        }
    }
}
