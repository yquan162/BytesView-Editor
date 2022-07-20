import java.io.IOException;
import java.util.TreeMap;
public class Memory {
    TreeMap<Integer, Byte> map;
    public Memory(TreeMap<Integer, Byte> mem){
        this.map = mem;
    }
    public Memory(int size) throws MemorySizeInitializationException, IOException {
        if(size == 0){
            throw new MemorySizeInitializationException("Memory size cannot be zero.");
        }
        this.map = new TreeMap<Integer, Byte>();
        for(int i = 0; i<size; i++){
            this.map.put(i, new Byte());
        }
    }
    public Byte getByte(int offset) throws MemoryAddressDoesNotExistException{
        if(offset <= this.map.size()){
            return this.map.get(offset);
        }
        else{
            throw new MemoryAddressDoesNotExistException("The referenced memory address does not exist.");
        }
    }
    public void changeByte(int offset, Byte newByte){
        this.map.put(offset, newByte);
    }
    public int size(){
        return this.map.size();
    }
}
