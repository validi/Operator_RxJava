package com.aurora.Operator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SkipOperator extends AppCompatActivity {
    public static String TAG = "MyTag";
    private Observable<Integer> myObservable;

    private DisposableObserver<Integer> myDisposableObserver;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip_operator);
        myObservable = Observable.range(2,50);

        compositeDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .skip(15)
                        .subscribeWith(getObserve()));
    }
    public DisposableObserver getObserve(){
        myDisposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(@NonNull Integer s) {
                Log.i(TAG, "onNext   ----  "+s);
                // txtGreeting.setText(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");

            }
        };

        return myDisposableObserver;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // myDisposableObserver.dispose();
        compositeDisposable.clear();
    }
}