import java.util.PriorityQueue;
import java.lang.Math;

    // ARR EventNode = 0
    // DEP EventNode = 1

public class PA2 {

    private static final int TOTAL_PROCESSES = 10000;
    private static double clock;
    private static boolean serverIdle;
    private static int readyQueueCount;
    private static double avgArrRate;
    private static double avgServTime;
    private static PriorityQueue<EventNode> eq;
    private static int depCount;

    private static double numProcsInRQ;
    private static double totalTurnaroundTime;
    private static double totalBusyTime;

    public static void main(String[] args){
        System.out.println();

        avgArrRate = Integer.parseInt(args[0]);
        avgServTime = Double.parseDouble(args[1]);

        Init();
        Run();

    }

    public static void Init() {
        clock = 0;
        serverIdle = true;
        readyQueueCount = 0;
        eq = new PriorityQueue<>();
        double t = (-1.0 / avgArrRate) * Math.log(Math.random());
        numProcsInRQ = 0;
        totalTurnaroundTime = 0;
        totalBusyTime = 0;
        schedEvent(0, t, eq); // First EventNode to add
    }

    public static void Run() {
        double timeElapsed = 0;
        double oldClock = 0;
        while (depCount <= TOTAL_PROCESSES) {
            EventNode running = eq.peek();
            oldClock = clock;
            clock = running.getTime();
            timeElapsed = clock - oldClock;

            numProcsInRQ += readyQueueCount * timeElapsed;

            if (running.getType() == 1) {
                depCount++;
            }

            if (!serverIdle) {
                totalBusyTime += timeElapsed; 
            }

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
        }

        double avgTurnaroundTime = totalTurnaroundTime / ((double) TOTAL_PROCESSES);
        System.err.println("Avg turnaround time: " + avgTurnaroundTime);

        double totalThroughput = ((double) TOTAL_PROCESSES) / clock;
        System.out.println("Total throughput: " + totalThroughput);

        double avgUtilization = totalBusyTime / clock;
        System.out.println("Avg CPU Utilization: " + avgUtilization);

        double avgNumProcsInRQ = numProcsInRQ / clock;
        System.out.println("Avg number of processes in RQ: " + avgNumProcsInRQ);

        System.out.println();
    }

    public static void schedEvent(int type, double t, PriorityQueue<EventNode> e) {
        e.add(new EventNode(type, t));
    }

    public static void arrHandler(EventNode e) {
        if (serverIdle) { // If CPU is NOT busy, make it busy and then schedule the event
            serverIdle = false;
            double addS = (-1.0 * avgServTime) * Math.log(Math.random());
            schedEvent(1, clock+addS, eq); // Sched with a SERVICE time
        }
        else { // Else, add it to the Ready Queue until the CPU can execute it
            readyQueueCount++; 
        }
        double addA = (-1.0 / avgArrRate) * Math.log(Math.random());
        schedEvent(0, clock+addA, eq); // Sched with an arrival time (add an interarrival time to clock)
    }

    public static void depHandler(EventNode e) {
        if (readyQueueCount == 0) {
            serverIdle = true;
        }
        else {
            readyQueueCount--;
            double addS = (-1.0 * avgServTime) * Math.log(Math.random());
            totalTurnaroundTime += addS;
            schedEvent(1, clock+addS, eq); // Sched with a SERVICE time
        }
    }
}
