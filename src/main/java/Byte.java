import java.io.IOException;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.zip.CRC32;

public class Byte {
    Bit b[] = new Bit[8];
    byte bits[];


    public Byte() throws IOException {
        this.fillByte();
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
        String bits = "";
        for(Bit bit:this.b){
            bits += bit.valueOf();
            bits += " ";
        }
        return bits;
    }
    public void fillByte(){
        for(int i = 0; i<this.b.length; i++){
            b[i] = new Bit();
        }
    }
    public void writeBits() throws IOException {
        this.bits = this.toByteArray();
    }
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject( this.b);
        byte [] data = bos.toByteArray();
        oos.flush();
        return data;
    }
    public String CRC32() throws IOException {
        CRC32 crc32 = new CRC32();
        crc32.update(this.toByteArray());
        String out = Long.toHexString(crc32.getValue());
        crc32 = null;
        System.gc();
        return out;
    }
}
