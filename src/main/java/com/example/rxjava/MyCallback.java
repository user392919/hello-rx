package com.example.rxjava;

public class MyCallback {


    public static void main(String[] args) {
        System.out.println("getting sum ... by thread: "+ Thread.currentThread().getId() );

        getSum(100, new SimpleCallBack() {
            @Override
            public void done(int a) {
                System.out.println("processing dependent process " + a + "  ..... by thread: "+ Thread.currentThread().getId());
            }

        });

        independentProcess();

    }

    private static void getSum(int i, SimpleCallBack simpleCallBack) {
        Worker worker = new Worker(i, simpleCallBack);
        Thread t = new Thread(worker);
        t.start();
    }


    private static void independentProcess() {
        System.out.println("precessing something else .... by thread: "+ Thread.currentThread().getId());
    }


}



