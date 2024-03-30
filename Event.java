public class Event {
    
    private int type;
    private double time;

    public Event() {
        type = 0;
        time = 0;
    }

    public Event(int type, double time) {
        this.type = type;
        this.time = time;
    }

    void setType(int inputType){
        type = inputType;
    }

    void setTime(int inputTime){
        time = inputTime;
    }
    
}
