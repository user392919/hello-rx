package com.example.rxjava;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MyRxJava {



    public static void main(String[] args) {

        System.out.println("getting sum ... by thread: "+ Thread.currentThread().getId() );

//        observeOnNewThread();

        subOnNewThread();

        independentProcess();

    }

    private static void observeOnNewThread() {
        Observable.create(new CountObservable(100)).observeOn(Schedulers.newThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer a) {
                System.out.println("processing dependent process " + a + "  ..... by thread: "+ Thread.currentThread().getId());
            }
        });
    }

    private static void subOnNewThread() {
        Observable.create(new CountObservable(100)).subscribeOn(Schedulers.newThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer a) {
                System.out.println("processing dependent process " + a + "  ..... by thread: " + Thread.currentThread().getId());
            }
        });
    }

    private static void independentProcess() {
        System.out.println("precessing something else .... by thread: "+ Thread.currentThread().getId());
    }


}
