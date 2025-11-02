import java.util.ArrayList;
import java.util.Scanner;

public class TestThreadCheckArray {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter array size: ");
        int n = input.nextInt();
        ArrayList<Integer> array = new ArrayList<>();

        System.out.println("Enter numbers for array:");
        for (int i = 0; i < n; i++) {
            array.add(input.nextInt());
        }

        System.out.print("Enter target sum (b): ");
        int b = input.nextInt();

        SharedData sd = new SharedData(array, b);

        Thread t1 = new Thread(new ThreadCheckArray(sd), "thread1");
        Thread t2 = new Thread(new ThreadCheckArray(sd), "thread2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!sd.getFlag()) {
            System.out.println("Sorry, no solution found.");
            return;
        }

        System.out.println("Solution for b = " + sd.getB() + ", n = " + sd.getArray().size());
        System.out.print("I:   ");
        for (int i = 0; i < sd.getArray().size(); i++)
            System.out.print(i + "    ");
        System.out.println();

        System.out.print("A:   ");
        for (int num : sd.getArray())
            System.out.printf("%-5d", num);
        System.out.println();

        System.out.print("C:   ");
        boolean[] chosen = sd.getWinArray();
        int total = 0;
        for (int i = 0; i < chosen.length; i++) {
            System.out.print((chosen[i] ? "1" : "0") + "    ");
            if (chosen[i]) total += sd.getArray().get(i);
        }
        System.out.println();
        System.out.println("Sum of chosen elements = " + total);
    }
}