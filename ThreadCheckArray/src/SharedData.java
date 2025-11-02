import java.util.ArrayList;

public class SharedData {
    private ArrayList<Integer> array;
    private boolean[] winArray;
    private volatile boolean flag;
    private final int b;

    public SharedData(ArrayList<Integer> array, int b) {
        this.array = array;
        this.b = b;
        this.winArray = new boolean[array.size()];
    }

    public synchronized boolean[] getWinArray() {
        return winArray;
    }

    public synchronized void setWinArray(boolean[] winArray) {
        this.winArray = winArray;
    }

    public ArrayList<Integer> getArray() {
        return array;
    }

    public int getB() {
        return b;
    }
//aa
    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
