import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.CRC32;

public class Byte {
    Bit[] b = new Bit[8];
    byte[] bits;


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
        StringBuilder bits = new StringBuilder();
        for(Bit bit:this.b){
            bits.append(bit.valueOf());
            bits.append(" ");
        }
        return bits.toString();
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
        System.gc();
        return out;
    }
}
