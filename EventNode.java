public class EventNode implements Comparable<EventNode> {
    
    private int type;
    private double time;

    public EventNode() {
        type = 0;
        time = 0;
    }

    public EventNode(int type, double time) {
        this.type = type;
        this.time = time;
    }

    public void setType(int inputType){
        type = inputType;
    }

    public void setTime(int inputTime){
        time = inputTime;
    }

    public int getType() {
        return type;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int compareTo(EventNode e) {
        if (this.getTime() == e.getTime()) return 0;
        else if (this.getTime() > e.getTime()) return 1;
        else return -1;
    }
    
}
