import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.CRC32;

public class Byte implements Cloneable{
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    Bit[] b = new Bit[8];
    byte bits;


    public Byte() throws IOException {
        this.fillByte();
        this.writeBits();
    }
    public Byte(byte b) throws IOException {
        String bin = Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
        //pad bin length
        while(bin.length() < 8){
            bin = "0" + bin;
        }
        for(int i = 0; i < bin.length(); i++){
            this.b[i] = new Bit(Character.getNumericValue(bin.charAt(i)));
        }
        this.writeBits();
    }
    public Bit[] valueOf(){
        return this.b;
    }
    public Bit returnBit(int index){
        return this.b[index];
    }
    public void changeBit(int index, boolean value) throws IOException {
        this.b[index].assignValue(value);
        this.writeBits();
    }
    public String showByte(){
        StringBuilder bits = new StringBuilder();
        for(Bit bit:this.b){
            bits.append(bit.valueOf());
            bits.append(" ");
        }
        return bits.toString();
    }
    public void fillByte() throws IOException {
        for(int i = 0; i<this.b.length; i++){
            b[i] = new Bit();
        }
        this.writeBits();
    }
    public void writeBits() throws IOException {
        this.bits = this.toByte();
    }
    public byte toByte() {
        byte val = 0;
        StringBuilder bin = new StringBuilder();
        for(Bit bit:this.b){
            bin.append(bit.valueOf());
        }
        //twos complement smth idek
        if(bin.charAt(0) == '1'){
            val = java.lang.Byte.parseByte(bin.substring(1), 2);
            val -= 128;
            return val;
        }
        val = java.lang.Byte.parseByte(bin.substring(1), 2);
        return val;
    }
}
