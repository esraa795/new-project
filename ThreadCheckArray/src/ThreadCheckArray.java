import java.util.ArrayList;

public class ThreadCheckArray implements Runnable {
    private SharedData sd;
    private ArrayList<Integer> array;
    private int b;
    private boolean[] localWinArray;
    private boolean found;

    public ThreadCheckArray(SharedData sd) {
        this.sd = sd;
        this.array = sd.getArray();
        this.b = sd.getB();
        this.localWinArray = new boolean[array.size()];
        this.found = false;
    }

    private void rec(int n, int sum) {
        if (sd.getFlag()) return; // מישהו כבר מצא פתרון
        if (n < 0) return;

        if (sum == 0) {
            found = true;
            synchronized (sd) {
                if (!sd.getFlag()) { // רק הראשון יעדכן
                    sd.setFlag(true);
                    sd.setWinArray(localWinArray.clone());
                }
            }
            return;
        }

        if (n == 0) {
            if (array.get(0) == sum) {
                localWinArray[0] = true;
                found = true;
                synchronized (sd) {
                    if (!sd.getFlag()) {
                        sd.setFlag(true);
                        sd.setWinArray(localWinArray.clone());
                    }
                }
            }
            return;
        }

        // כוללים את האיבר הנוכחי
        localWinArray[n] = true;
        rec(n - 1, sum - array.get(n));
        if (found || sd.getFlag()) return;

        // לא כוללים את האיבר הנוכחי
        localWinArray[n] = false;
        rec(n - 1, sum);
    }

    @Override
    public void run() {
        // thread אחד יתחיל לכלול את האיבר האחרון, והשני לא
        if (Thread.currentThread().getName().equals("thread1")) {
            localWinArray[array.size() - 1] = true;
            rec(array.size() - 2, b - array.get(array.size() - 1));
        } else {
            rec(array.size() - 1, b);
        }
    }
}
