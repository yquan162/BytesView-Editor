import java.io.Serializable;

public class Bit implements Serializable {
    boolean value;
    public Bit(){
        this.value = false;
    }
    public Bit(boolean x){
        this.value = x;
    }
    public int valueOf(){
        return (this.value) ? 1 : 0;
    }
    public boolean boolValueOf(){
        return value;
    }
    public void changeValue(){
        this.value = !this.value;
    }
}
