public class Byte implements Cloneable{
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    Bit[] b = new Bit[8];
    byte bits;


    public Byte() {
        this.fillByte();
        this.writeBits();
    }
    public Byte(byte b) {
        StringBuilder bin = new StringBuilder(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
        //pad bin length
        while(bin.length() < 8){
            bin.insert(0, "0");
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
    public void changeBit(int index, boolean value) {
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
    public void fillByte() {
        for(int i = 0; i<this.b.length; i++){
            b[i] = new Bit();
        }
        this.writeBits();
    }
    public void writeBits() {
        this.bits = this.toByte();
    }
    public byte toByte() {
        byte val;
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
