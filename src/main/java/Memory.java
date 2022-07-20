import java.util.TreeMap;
public class Memory {
    TreeMap<Integer, Byte> map;
    public Memory(){
        this.map = new TreeMap<Integer, Byte>();
    }
    public Memory(TreeMap<Integer, Byte> mem){
        this.map = mem;
    }
    public Memory(int size) throws MemorySizeInitializationException {
        if(size == 0){
            throw new MemorySizeInitializationException("Memory size cannot be zero.");
        }
        Byte b = new Byte();
        this.map = new TreeMap<Integer, Byte>();
        for(int i = 0; i<size; i++){
            this.map.put(i, b);
        }
    }
    public Byte getByte(int offset) throws MemoryAddressDoesNotExistException{
        if(offset <= map.size()){
            return map.get(offset);
        }
        else{
            throw new MemoryAddressDoesNotExistException("The referenced memory address does not exist.");
        }
    }
    public int size(){
        return this.map.size();
    }
}
