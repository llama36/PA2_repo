import java.util.PriorityQueue;

public class PA2 {

    private double clock;
    private boolean serverIdle;
    private int readyQueueCount;
    private PriorityQueue<Event> eq;

    public static void main(String[] args){
        System.out.println("Hellow, World!");
    }

    // ARR event = 0
    // DEP event = 1

    public void Init(){
        clock = 0;
        serverIdle = true;
        readyQueueCount = 0;
        eq = new PriorityQueue<>();
        schedEvent(0, 1, eq); // First event to add. 1 is default time for now
    }

    public void schedEvent(int type, double t, PriorityQueue<Event> e) {
        e.add(new Event(type, t));
    }
}
