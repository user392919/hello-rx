package com.example.rxjava;

import rx.Observable;
import rx.Subscriber;

public class CountObservable implements Observable.OnSubscribe<Integer> {

    private int value;

    public CountObservable(int value) {
        System.out.println("CountObservable object created ....  by thread: "+ Thread.currentThread().getId());
        this.value = value;
    }

    @Override
    public void call(Subscriber<? super Integer> subscriber) {
        System.out.println("calculating sum ....  by thread: "+ Thread.currentThread().getId());

        int tot = 0;

        for (int k = 0; k<=value; k++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tot+=k;
        }



            subscriber.onNext(tot);
            subscriber.onCompleted();
    }
}
