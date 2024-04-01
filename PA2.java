import java.util.PriorityQueue;
import java.lang.Math;

public class PA2 {

    private static int processCount;
    private static double clock;
    private static boolean serverIdle;
    private static int readyQueueCount;
    private static double avgArrRate;
    private static double avgServTime;
    private static PriorityQueue<EventNode> eq;

    public static void main(String[] args){
        System.out.println();

        //avgArrRate = Integer.parseInt(args[0]);
        //avgServTime = Double.parseDouble(args[1]);

        avgArrRate = 30;
        avgServTime = 0.04;

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
        clock += (-1.0 / avgArrRate) * Math.log(Math.random());
        System.out.println("Arrival time for first event is :" + clock);
        processCount = 1;
        schedEvent(0, clock, eq); // First EventNode to add
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

            switch (running.getType()) {
                case 0:
                    arrHandler(running);
                    break;
                case 1:
                    depHandler(running);
                    break;
                default:
                    System.out.println("Uhh something went wrong ._.");
            }
            
            eq.remove(running);
            processCount++;
        }
    }

    public static void schedEvent(int type, double t, PriorityQueue<EventNode> e) {
        e.add(new EventNode(type, t));
    }

    public static void arrHandler(EventNode e) {
        if (serverIdle) { // If CPU is NOT busy, make it busy and then schedule the event
            serverIdle = false;
            clock += (-1.0 * avgServTime) * Math.log(Math.random());
            schedEvent(1, clock, eq); // Sched with a SERVICE time
        }
        else { // Else, add it to the Ready Queue until the CPU can execute it
            readyQueueCount++; 
        }
        clock += (-1.0 / avgArrRate) * Math.log(Math.random());
        schedEvent(0, clock, eq); // Sched with an arrival time (add an interarrival time to clock)
    }

    public static void depHandler(EventNode e) {
        if (readyQueueCount == 0) {
            serverIdle = true;
        }
        else {
            readyQueueCount--;
            clock += (-1.0 * avgServTime) * Math.log(Math.random());
            schedEvent(1, clock, eq); // Sched with a SERVICE time
        }
    }
}
