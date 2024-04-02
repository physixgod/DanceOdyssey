package devnatic.danceodyssey.RealTimeNotifications;

public class RealNotifications {
    private int count;
    public RealNotifications(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public void increment() {
        this.count++;
    }
}
