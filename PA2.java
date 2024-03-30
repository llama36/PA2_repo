public class PA2 {

    private double clock;
    private boolean serverIdle;
    private int readyQueueCount;

    public static void main(String[] args){
        System.out.println("Hellow, World!");
    }

    public void Init(){
        clock = 0;
        serverIdle = true;
        readyQueueCount = 0;
        

    }
}
