public class MemoryAddressDoesNotExistException extends Exception{
    public MemoryAddressDoesNotExistException(String errormessage){
        super(errormessage);
        System.out.println(errormessage);
    }
}
