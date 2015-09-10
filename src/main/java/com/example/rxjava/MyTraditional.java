package com.example.rxjava;

public class MyTraditional {

    public static void main(String[] args) {
        System.out.println("getting sum ... by thread: "+ Thread.currentThread().getId() );
        int a = getSum(100);

        System.out.println("processing dependent process " + a + "  ..... by thread: "+ Thread.currentThread().getId());

        independentProcess();
    }

    private static void independentProcess() {
        System.out.println("precessing something else .... by thread: "+ Thread.currentThread().getId());
    }

    private static int getSum(int i) {

        int tot = 0;
        System.out.println("calculating sum ....  by thread: "+ Thread.currentThread().getId());
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
