package ContainerSimulation;

public class Entry {
    private int id;
    private long time;


    public Entry(int id) {
        this.id = id;
        this.time = System.currentTimeMillis();
    }


    public long getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
