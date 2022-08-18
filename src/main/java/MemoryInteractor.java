import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class MemoryInteractor implements Cloneable{
    TextColor color = new TextColor();
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    private Memory memory;
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
            System.out.println(color.colorString("WARN: ", "YELLOW", false)+" This interactor instance does not have memory loaded!");
            return;
        }
        int byteOffset = Integer.parseInt(offset.substring(0,7),16 );
        int bitOffset = Integer.parseInt(offset.substring(7));
        Byte bt = this.memory.getByte(byteOffset);
        if(verbose){
            int state = bt.returnBit(bitOffset).valueOf();
            bt.changeBit(bitOffset, !bt.returnBit(bitOffset).boolValueOf());
            System.out.println("Bit at address 0x" + offset + " changed to " + bt.returnBit(bitOffset).valueOf() +"(was "+ state + ")");
            System.out.println("SHA-256 checksum of virtual memory has changed.\nType \"checksum\" to see more details.\n");
        }
        else{
            bt.changeBit(bitOffset, !bt.returnBit(bitOffset).boolValueOf());
        }
        this.memory.changeByte(byteOffset, bt);
        System.gc();
    }
    public void flushMemory(boolean verbose) throws MemorySizeInitializationException, IOException {
        if(this.memory == null){
            System.out.println(color.colorString("WARN: ", "YELLOW", false)+" This interactor instance does not have memory loaded!");
            return;
        }
        int i = 0;
        for(Map.Entry<Integer, Byte> ignored :this.memory.map.entrySet()){
            this.memory.changeByte(i, new Byte());
            i++;
        }
        if(verbose){
            System.out.println("Memory of size "+ this.memory.size()+" flushed.");
        }
    }
    public String sha256() throws NoSuchAlgorithmException, MemoryAddressDoesNotExistException, IOException {
        if(this.memory == null){
            System.out.println(color.colorString("WARN: ", "YELLOW", false)+" This interactor instance does not have memory loaded!");
            return "";
        }
        return this.memory.sha256();
    }
    public boolean isEmpty(){
        return this.memory == null;
    }
    public Memory fromFile(String path) throws IOException {
        File in = new File(System.getProperty("user.dir")+"/"+path);
        FileInputStream input = new FileInputStream(in);
        byte[] bytes = input.readAllBytes();
        System.out.println(color.colorString("INFO: ", "BLUE", false)+"loaded file "+System.getProperty("user.dir")+"/"+path);
        input.close();
        return new Memory(bytes);
    }
}
