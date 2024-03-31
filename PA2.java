import java.util.PriorityQueue;
import java.util.Comparator;
import java.lang.Math;

public class PA2 {

    private static int processCount;
    private static double clock;
    private static boolean serverIdle;
    private static int readyQueueCount;
    private static int avgArrRate;
    private static double avgServRate;
    private static PriorityQueue<EventNode> eq;
    private static Comparator<Double> comparator;

    public static void main(String[] args){
        System.out.println();

        avgArrRate = Integer.parseInt(args[0]);
        avgServRate = Double.parseDouble(args[1]);

        Init();
        Run();

    }

    // ARR EventNode = 0
    // DEP EventNode = 1

    public static void Init() {
        clock = 0;
        serverIdle = true;
        readyQueueCount = 0;
        eq = new PriorityQueue<>();
        double t = 0;
        t += Math.random();
        processCount = 1;
        schedEvent(0, t, eq); // First EventNode to add. 1 is default time for now
    }

    public static void Run() {
        double timeElapsed = 0;
        double oldClock = 0;
        while (processCount <= 10) {
            EventNode running = eq.peek();
            oldClock = clock;
            clock = running.getTime();
            timeElapsed = clock - oldClock;

            System.out.println("Current process: " + processCount);
            System.out.println("Time elapsed: " + timeElapsed);
            System.out.println();

            processCount++;
        }
    }

    public static void schedEvent(int type, double t, PriorityQueue<EventNode> e) {
        e.add(new EventNode(type, t));
    }

    public static void arrHandler(EventNode e) {
        if (serverIdle) { // If CPU is NOT busy, make it busy and then schedule the event
            serverIdle = false;
            schedEvent(1, clock+1, eq);
        }
        else { // Else, add it to the Ready Queue until the CPU can execute it
            readyQueueCount++; 
        }
        schedEvent(0, clock+2, eq);
    }

    public static void depHandler(EventNode e) {
        if (readyQueueCount == 0) {
            serverIdle = true;
        }
        else {
            readyQueueCount--;
            schedEvent(1, clock+2, eq);
        }
    }
}
