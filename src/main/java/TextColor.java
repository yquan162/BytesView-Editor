public class TextColor {
    public TextColor(){
    }
    public String colorString(String content, String color, boolean bg){
        String colored_text;
        String reset = "\u001B[0m";
        //bg true : bg false
        switch(color){
            case "BLACK" -> colored_text = (bg) ? "\u001B[40m" : "\u001B[30m";
            case "RED" -> colored_text = (bg) ? "\u001B[41m" : "\u001B[31m";
            case "GREEN" -> colored_text = (bg) ? "\u001B[42m" : "\u001B[32m";
            case "YELLOW" -> colored_text = (bg) ? "\u001B[43m" : "\u001B[33m";
            case "BLUE" -> colored_text = (bg) ? "\u001B[44m" : "\u001B[34m";
            case "PURPLE" -> colored_text = (bg) ? "\u001B[45m" : "\u001B[35m";
            case "CYAN" -> colored_text = (bg) ? "\u001B[46m" : "\u001B[36m";
            case "WHITE" -> colored_text = (bg) ? "\u001B[47m" : "\u001B[37m";
            default -> colored_text = content;
        }
        return colored_text + content + reset;
    }
}
