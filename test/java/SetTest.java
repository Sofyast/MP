import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;         //?

public class SetTest {
    @Test
    public void add() throws InterruptedException {          //?
        Set<Integer> integerSet = new SetImpl<>();
        ArrayList<Thread> threadArrayList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            int tx = i;
            threadArrayList.add(new Thread(() -> assertTrue(integerSet.add(tx))));         //?
        }
        for (Thread th :
                threadArrayList) {
            th.start();
        }
        for (Thread th :
                threadArrayList) {
            th.join();
        }
        assertFalse(integerSet.add(1));
    }

    @Test
    public void remove() throws InterruptedException {             //?
        Set<Integer> integerSet = new SetImpl<>();                 //?
        ArrayList<Thread> threadArrayList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            integerSet.add(i);
        }
        for (int i = 1; i < 5; i++) {
            int tx = i + 2;
            threadArrayList.add(new Thread(() -> assertTrue(integerSet.remove(tx))));
        }
        for (Thread th :
                threadArrayList) {
            th.start();
        }
        for (Thread th :
                threadArrayList) {
            th.join();
        }

    }


    @Test
    public void contains() throws InterruptedException {         
        Set<Integer> integerSet = new SetImpl<>();
        ArrayList<Thread> threadArrayList = new ArrayList<>();
        Random rnd = new Random();
        int rndMax = 10;
        for (int i = 0; i < 10; i++) {
            integerSet.add(i);
        }
        for (int i = 1; i < 5; i++) {
            int tx = rnd.nextInt(rndMax);
            threadArrayList.add(new Thread(() -> assertTrue(integerSet.contains(tx))));       //?
        }
        for (Thread th :
                threadArrayList) {
            th.start();
        }
        for (Thread th :
                threadArrayList) {
            th.join();
        }
    }

    @Test
    public void isEmpty() {
        Set<Integer> integerSet = new SetImpl<>();
        ArrayList<Thread> threadArrayList = new ArrayList<>();         //?


    }
}
