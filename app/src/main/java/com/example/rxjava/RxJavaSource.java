package com.example.rxjava;

import android.graphics.Bitmap;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @time 2020/4/28 14:38
 * @desc
 */
public class RxJavaSource {


    public void init() {

        // 一、创建被观察者  Observable

        //Observable.OnSubscribe 通知观察者，调用 call() 方法

        final Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        });


        /*
        1.  OnSubscribe//用于通知观察者

        2.  Observable#create()

        >  此方法用于将 OnSubscribe对象赋值给 Observable 的成员变量 onSubscribe ,并返回一个被观察对象

         */


        //二、 创建观察者
        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String o) {

            }
        };


        // 三 、订阅
        observable.subscribe(observer);
    /*

         1. Subscriber 和 Observer关系

        Subscriber 实现了 Observer接口

        Subscriber 同时实现了 Subscription 接口 ,Subscription 里面有个两个接口方法 unsubscribe() isUnsubscribed();
        所有的观察者都实现了 Subscription 接口

         Subscriber# add(Subscription s)调用 SubscriptionList#add(Subscription s)
         Subscriber# unsubscribe()调用 SubscriptionList#unsubscribe()


        2.订阅
          Observable#subscribe(Observer observer)会将 observer转成 Subscription 对象。

          然后触发 Subscriber.onStart()方法

          最后触发 Observable.onSubscribe # call()方法 开始事件传递


         */


    /*源码解析*/

    /*
     observable.subscribe(observer);

     ```
     Observable # subscribe()

     //observer 我们实现的观察者对象
     public final Subscription subscribe(final Observer<? super T> observer) {
        if (observer instanceof Subscriber) {
            return subscribe((Subscriber<? super T>)observer);
        }

        //如果我们的观察者对象 不是 Subscriber 的 子类 ，则创建一个 Subscriber 对象，并回调给observer对象的方法
        return subscribe(new Subscriber<T>() {

            @Override
            public void onCompleted() {
                observer.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                observer.onError(e);
            }

            @Override
            public void onNext(T t) {
                observer.onNext(t);
            }

        });
    }

     ```


     ```
    Observable # subscribe(Subscriber<? super T> subscriber)

      public final Subscription subscribe(Subscriber<? super T> subscriber) {

        //subscriber 自定的观察者对象
        //this 当前的被观察者对象
        return Observable.subscribe(subscriber, this);
      }

      private static <T> Subscription subscribe(Subscriber<? super T> subscriber, Observable<T> observable) {

            subscriber.onStart();
            if (!(subscriber instanceof SafeSubscriber)) {
            subscriber = new SafeSubscriber<T>(subscriber);
            }
            try {

            //observable.onSubscribe 创建观察者时定义的内部内，其call()方法就是我们实现的的；
            // subscriber 是下面的自己定义的观察者对象
            hook.onSubscribeStart(observable, observable.onSubscribe).call(subscriber);
            return hook.onSubscribeReturn(subscriber);
             } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            try {
                subscriber.onError(hook.onSubscribeError(e));
            } catch (Throwable e2) {
                Exceptions.throwIfFatal(e2);
                hook.onSubscribeError(r);
                throw r;
            }
            return Subscriptions.unsubscribed();
        }
    }

     ```
     */



    }


    private void init1() {

        //四 操作符
        /*
       1、 变换：就是将事件序列中的对象或整个序列进行加工处理

       2、 map 操作符 ：将一个事件转换成另外一个事件

       3、flatMap
            + 将传入的事件对象转换成一个Observable；
            + 这是不会直接发送这个Observable,而是将这个Observable激活让它自己开始发送事件；
            + 每个创建出来的Observable发送的事件，都被汇入同一个Observable
         */

    }


    public static void map() {

        //返回一个观察者对象，并给  OnSubscribe<T> onSubscribe 对象赋值

        Observable.OnSubscribe<String> subscribe =  new Observable.OnSubscribe<String>() {

            //通知观察者2触发事件
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //subscriber 是map操作符间接创建的的观察者对象 ，
                //onNext()方法会调用 OperatorMap 的转换方法
                subscriber.onNext("dddd");
                subscriber.onCompleted();
            }
        };


        final Observable<String> observable = Observable.create(subscribe);//1


        Func1<String, Bitmap> func1 = new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                return null;
            }
        };


        final Observable<Bitmap> observable1 = observable.map(func1);
        //2 lift方法 会重新创建一个可观察对象，并设置 OnSubscribe 对象 赋值给 onSubscribe；



        Subscriber<Bitmap> subscriberLast = new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Bitmap bitmap) {
            }
        };





       observable1.subscribe(subscriberLast);//3


        /*
        1、 Observable#map()

         //返回一个新的被观察者对象
          public final <R> Observable<R> map(Func1<? super T, ? extends R> func) {
               //OperatorMap 构造方法传入 Func1 对象，在OperatorMap 对象中调用 call() 方法
                return lift(new OperatorMap<T, R>(func));
            }


         OperatorMap#call()

           // 返回一个观察者对象
           // Subscriber<? super R> o  入参为 subscriberLast 观察者
             @Override
            public Subscriber<? super T> call(final Subscriber<? super R> o) {
                return new Subscriber<T>(o) {
                    @Override
                    public void onCompleted() {
                        o.onCompleted();
                    }
                    @Override
                    public void onError(Throwable e) {
                        o.onError(e);
                    }
                    @Override
                    public void onNext(T t) {
                        try {
                            //  transformer就是外部传入的func对象，调用call方法就是我们转换的代码
                            // t 就是 ddd
                           // transformer.call(t) 就是调用 func1 中call(String s)

                           //之后就用 subscriberLast 观察者的 onNext() 方法
                            o.onNext(transformer.call(t));
                        } catch (Throwable e) {
                            Exceptions.throwOrReport(e, this, t);
                        }
                    }

                };
            }



        2、Observable# lift()
            public final <R> Observable<R> lift(final Operator<? extends R, ? super T> operator) {
                    return new Observable<R>(new OnSubscribe<R>() {

                        //订阅的时候 观察者对象会调用这个方法
                        //observable1 调用该方法
                        // Subscriber<? super R> o 入参为 subscriberLast 观察者
                        @Override
                        public void call(Subscriber<? super R> o) {
                            try {
                                //放回一个包装后的 观察者对象，包装了func1 操作符
                                // 在 观察者对象 onNext()方法中
                                Subscriber<? super T> st = hook.onLift(operator).call(o);
                                try {
                                    st.onStart();

                                    // onSubscribe 是 observable 对象中成员变量 onSubscribe
                                    //调用 call()方法 也是 上面 subscribe 中的 call()方法
                                   //传入 封装后的 Subscriber st
                                    onSubscribe.call(st);

                                //之后 就是
                                封装后的 Subscriber#next("ddd)方法被调用；
                                //




                                } catch (Throwable e) {
                                    Exceptions.throwIfFatal(e);
                                    st.onError(e);
                                }
                            } catch (Throwable e) {
                                Exceptions.throwIfFatal(e);
                                o.onError(e);
                            }
                        }
                    });
                }



         */


    }


    public static void switchThread(){

        //线程切换

        Observable.just("haha")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                });
    }



}
