import java.util.PriorityQueue;
import java.util.Comparator;
import java.lang.Math;

public class PA2 {

    private static int processCount = 0;
    private static double clock;
    private static boolean serverIdle;
    private static int readyQueueCount;
    private static PriorityQueue<EventNode> eq;
    private static Comparator<Double> comparator;

    public static void main(String[] args){
        System.out.println("Hello, World!");

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
        //comparator = new // WORK ON THIS
        double t = 0;
        t += Math.random();
        schedEvent(0, t, eq); // First EventNode to add. 1 is default time for now
    }

    public static void Run() {
        double timeElapsed = 0;
        double oldClock = 0;
        //while (processCount <= 10) {
            EventNode running = eq.peek();
            oldClock = clock;
            clock = running.getTime();
            timeElapsed = clock - oldClock;

            System.out.println("Current process: " + processCount);
            System.out.print("Time elapsed: " + timeElapsed);
            System.out.println();

            processCount++;
        //}
    }

    public static void schedEvent(int type, double t, PriorityQueue<EventNode> e) {
        e.add(new EventNode(type, t));
    }
}
