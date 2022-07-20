public class MemorySizeInitializationException extends Exception{
    public MemorySizeInitializationException(String errormessage){
        super(errormessage);
        System.out.println(errormessage);
    }
}
