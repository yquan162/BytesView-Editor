import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.io.ByteArrayOutputStream;

public class MemoryDisplay {
    private Memory memory;
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
    public void showMemory(boolean verbose) throws MemoryAddressDoesNotExistException, IOException, NoSuchAlgorithmException {
        if(this.memory == null){
            System.out.println("This display instance does not have memory loaded!");
            return;
        }
        System.out.println("Address       0 1 2 3 4 5 6 7    CRC32");
        for(int i = 0; i < this.memory.size(); i++){
            System.out.println(String.format("0x%07X0", i) + "    " + this.memory.getByte(i).showByte() + "   " + this.memory.getByte(i).CRC32());
        }
        System.out.println("------------------------------------------------------------------\n\n");
        if(verbose){
            System.out.println("SHA-256 Checksum: " + this.sha256());
        }

    }
    public String sha256() throws NoSuchAlgorithmException, MemoryAddressDoesNotExistException, IOException {
        if(this.memory == null){
            System.out.println("This display instance does not have memory loaded!");
            return new String();
        }
        return this.memory.sha256();
    }
    public boolean isEmpty(){
        return (this.memory == null) ? true : false;
    }
}
