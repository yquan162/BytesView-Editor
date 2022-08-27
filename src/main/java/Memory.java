import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
public class Memory implements Cloneable{
    TextColor color = new TextColor();
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    TreeMap<Integer, Byte> map;
    public Memory(TreeMap<Integer, Byte> mem){
        this.map = mem;
    }
    public Memory(byte[] b) throws IOException {
        this.map = new TreeMap<>();
        this.fromByteArray(b);
    }
    public Memory(int size) throws MemorySizeInitializationException, IOException {
        if(size == 0){
            throw new MemorySizeInitializationException(color.colorString("FATAL: ", "RED", false)+"Memory size cannot be zero.");
        }
        this.map = new TreeMap<>();
        for(int i = 0; i<size; i++){
            this.map.put(i, new Byte());
        }
    }
    public Byte getByte(int offset) throws MemoryAddressDoesNotExistException{
        if(offset <= this.map.size()){
            return this.map.get(offset);
        }
        else{
            throw new MemoryAddressDoesNotExistException(color.colorString("FATAL: ", "RED", false)+"The referenced memory address does not exist.");
        }
    }
    public void changeByte(int offset, Byte newByte){
        this.map.put(offset, newByte);
    }
    public int size(){
        return this.map.size();
    }
    public String sha256() throws NoSuchAlgorithmException, MemoryAddressDoesNotExistException, IOException {
        final MessageDigest md = MessageDigest.getInstance("SHA-256");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        final StringBuilder hexString = new StringBuilder();
        int i = 0;
        for(Map.Entry<Integer, Byte> ignored :this.map.entrySet()){
            out.write(this.getByte(i).toByte());
            i++;
        }
        final byte[] hash = md.digest(out.toByteArray());
        for (byte b : hash) {
            final String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public void toBinaryFile() throws IOException, MemoryAddressDoesNotExistException, NoSuchAlgorithmException {
        File bin = new File(System.getProperty("user.dir")+"/memory-"+this.sha256().substring(0,8)+ ".bin");
        System.out.println(color.colorString("INFO: ", "BLUE", false)+"Writing to memory-"+this.sha256().substring(0,8)+ ".bin");
        bin.createNewFile();
        FileOutputStream fos = new FileOutputStream(bin);
        ArrayList<java.lang.Byte> bytes = new ArrayList<>();
        int i = 0;
        for(Map.Entry<Integer, Byte> ignored :this.map.entrySet()){
            bytes.add(this.getByte(i).toByte());
            i++;
        }
        byte[] out = new byte[bytes.size()];
        for(int j = 0; j < bytes.size(); j++){
            out[j] = bytes.get(j);
        }
        fos.write(out);
        fos.close();
    }
    public void fromByteArray(byte[] b) {
        int i = 0;
        for(byte bytes : b){
            this.map.put(i, new Byte(bytes));
            i++;
        }
    }
    public byte[] toByteArray(){
        byte[] bytes = new byte[this.map.size()];
        for(int i = 0; i < this.map.size(); i++){
            bytes[i] = this.map.get(i).toByte();
        }
        return bytes;
    }

}
