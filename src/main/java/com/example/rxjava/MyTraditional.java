package com.example.rxjava;

public class MyTraditional {

    public static void main(String[] args) {
        System.out.println("getting sum ...");
        int a = getSum(100);

        System.out.println("processing " + a + "  .....");

        independentProcess();
    }

    private static void independentProcess() {
        System.out.println("precessing something else ....");
    }

    private static int getSum(int i) {

        int tot = 0;

        for (int k = 0; k <= i; k++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tot += k;
        }
        return tot;
    }

}
