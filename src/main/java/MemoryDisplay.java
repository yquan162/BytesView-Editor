import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MemoryDisplay implements Cloneable{
    TextColor color = new TextColor();
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
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
            System.out.println(color.colorString("WARN: ", "YELLOW", false)+" This display instance does not have memory loaded!");
            return;
        }
        System.out.println("Address       0 1 2 3 4 5 6 7");
        for(int i = 0; i < this.memory.size(); i++){
            System.out.println(String.format("0x%08x", i) + "    " + this.memory.getByte(i).showByte());
        }
        System.out.println("------------------------------------------------------------------\n\n");
        if(verbose){
            System.out.println("SHA-256 Checksum: " + this.sha256());
        }

    }
    public void showBytesMemory() throws MemoryAddressDoesNotExistException {
        int remainder = this.memory.size() % 16;
        byte[] textdata = new byte[this.memory.size()];
        if(this.memory == null){
            System.out.println(color.colorString("WARN: ", "YELLOW", false)+" This display instance does not have memory loaded!");
            return;
        }
        System.out.print("Offset        00  01  02  03  04  05  06  07  08  09  0a  0b  0c  0d  0e  0f  Decoded Text");
        for(int i = 0; i < this.memory.size(); i++){
            if(i%16 == 0){
                if(i != 0){
                    for(int j = i - 16; j < i; j++){
                        if(textdata[j] < 32 || textdata[j] > 126)
                        {
                            System.out.print(".");
                        }
                        else{
                            System.out.print((char)textdata[j]);
                        }
                    }
                }
                System.out.print("\n"+String.format("0x%07x0", i/16) + "    ");
            }
            textdata[i] = this.memory.getByte(i).toByte();
            System.out.print(String.format("%02x",this.memory.getByte(i).toByte()) + "  ");
        }
        if(remainder > 0){
            for(int j = 0; j < (16 -(remainder)); j++){
               System.out.print("??  ");
            }
            for(int k = 0; k < remainder; k++){
                if(textdata[k] < 32 || textdata[k] > 126)
                {
                    System.out.print(".");
                }
                else{
                    System.out.print((char)textdata[(this.memory.size() - remainder)+k]);
                }
            }
        }
        System.out.println("\n------------------------------------------------------------------\n\n");
    }
    public String sha256() throws NoSuchAlgorithmException, MemoryAddressDoesNotExistException, IOException {
        if(this.memory == null){
            System.out.println(color.colorString("WARN: ", "YELLOW", false)+" This display instance does not have memory loaded!");
            return "";
        }
        return this.memory.sha256();
    }
    public boolean isEmpty(){
        return this.memory == null;
    }
    public static double entropy(byte[] input) {
        if (input.length == 0) {
            return 0.0;
        }

        /* Total up the occurrences of each byte */
        int[] charCounts = new int[256];
        for (byte b : input) {
            charCounts[b & 0xFF]++;
        }

        double entropy = 0.0;
        for (int i = 0; i < 256; ++i) {
            if (charCounts[i] == 0.0) {
                continue;
            }

            double freq = (double) charCounts[i] / input.length;
            entropy -= freq * (Math.log(freq) / Math.log(2));
        }

        return entropy;
    }
}
