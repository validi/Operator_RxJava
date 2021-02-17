package com.aurora.Operator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FromArray extends AppCompatActivity {
    public static String TAG = "MyTag";
    private String[] greetings = {"Hello 1","Hello 2","Hello 3","Hello 4","Hello 5","Hello 6","Hello 7"};
    private Observable<String> myObservable;
    // private Observer<String>myObserver;
    private DisposableObserver<String> myDisposableObserver;
    TextView txtGreeting;
    // private Disposable disposable;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_array);
        myObservable = Observable.fromArray(greetings);



        compositeDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserve()));
    }
    public DisposableObserver getObserve(){
        myDisposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
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