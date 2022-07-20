import java.io.IOException;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.CRC32;

public class Byte {
    Bit b[] = new Bit[8];
    byte bits[];

    public Byte() throws IOException {
        Arrays.fill(this.b, new Bit());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this.b);
        oos.flush();
        bits = bos.toByteArray();
    }
    public Byte(boolean value, int index) throws IOException {
        Arrays.fill(this.b, new Bit());
        b[index] = new Bit(value);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this.b);
        oos.flush();
        bits = bos.toByteArray();
    }
    public Bit[] valueOf(){
        return this.b;
    }
    public Bit returnBit(int index){
        return this.b[index];
    }
    public void changeBit(int index, boolean value) throws IOException {
        this.b[index] = new Bit(value);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this.b);
        oos.flush();
        bits = bos.toByteArray();
        CRC32();
    }
    public String showByte(){
        String bits = "";
        for(Bit bit:b){
            bits += bit.valueOf();
            bits += " ";
        }
        return bits;
    }
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this.b);
        oos.flush();
        byte [] data = bos.toByteArray();
        return data;
    }
    public String CRC32() {
        CRC32 crc32 = new CRC32();
        crc32.update(this.bits);
        return Long.toHexString(crc32.getValue());
    }
}
