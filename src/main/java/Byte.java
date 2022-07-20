import java.util.Arrays;

public class Byte {
    Bit b[] = new Bit[8];
    boolean Errored;
    public Byte(){
        Arrays.fill(this.b, new Bit());
    }
    public Byte(boolean isErrored){
        this.Errored = isErrored;
    }
    public Byte(boolean value, int index){
        Arrays.fill(this.b, new Bit());
        b[index] = new Bit(value);
    }
    public Bit[] valueOf(){
        return this.b;
    }
    public Bit returnBit(int index){
        return this.b[index];
    }
    public void changeBit(int index, boolean value){
        this.b[index] = new Bit(value);
    }
    public boolean returnState(){
        return this.Errored;
    }
    public String showByte(){
        String bits = "";
        for(Bit bit:b){
            bits += bit.valueOf();
            bits += " ";
        }
        return bits;
    }
}
