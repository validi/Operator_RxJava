package com.aurora.Operator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilterOperator extends AppCompatActivity {

    public static String TAG = "MyTag";
    private Observable<Integer> myObservable;

    private DisposableObserver<Integer> myDisposableObserver;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_operator);
        myObservable = Observable.range(5, 50);

        compositeDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<Integer>() {
                            @Override
                            public boolean test(Integer integer) throws Throwable {
                                if (integer % 2 == 0)
                                    return true;
                                return false;
                            }
                        })
                        .subscribeWith(getObserve()));
    }

    public DisposableObserver getObserve() {
        myDisposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(@NonNull Integer s) {
                Log.i(TAG, "onNext   ----  " + s);
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