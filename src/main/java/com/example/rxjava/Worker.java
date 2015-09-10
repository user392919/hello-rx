package com.example.rxjava;

public class Worker implements Runnable {

    private SimpleCallBack simpleCallBack;
    private int value;

    public Worker(int value ,SimpleCallBack callBack) {
        this.simpleCallBack =callBack;
        this.value = value;
    }

    @Override
    public void run() {
        int tot = 0;
        System.out.println("calculating sum ....  by thread: "+ Thread.currentThread().getId());
        for (int k = 0; k<=value; k++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tot+=k;
        }
        simpleCallBack.done(tot);

    }
}
