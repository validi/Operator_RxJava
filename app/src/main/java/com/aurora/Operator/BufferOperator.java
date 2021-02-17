package com.aurora.Operator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.aurora.Operator.model.Student;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BufferOperator extends AppCompatActivity {

    public static String TAG = "MyTag";
    private Observable<Integer> myObservable;

    private DisposableObserver<List<Integer>> myDisposableObserver;

    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer_operator);


        myObservable = Observable.range(1,25);


        compositeDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .buffer(4)
                        .subscribeWith(getObserve())
        );


    }
    public DisposableObserver getObserve(){
        myDisposableObserver = new DisposableObserver<List<Integer>>() {
            @Override
            public void onNext(@NonNull List<Integer> s) {
                Log.i(TAG, "onNext");
                for(Integer i:s){
                    Log.i(TAG, "onNext   ----  "+i);
                }
              //  Log.i(TAG, "onNext   ----  "+s.getName().toString());
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
    public ArrayList<Student> getStudent(){
        ArrayList<Student> students=new ArrayList<>();

        Student student=new Student();
        student.setName("ali");
        student.setEmail("Ali@gmail.com");
        student.setAge(20);
        student.setRegisterDate("2021-2-12");
        students.add(student);

        student=new Student();
        student.setName("reza");
        student.setEmail("Reza@gmail.com");
        student.setAge(21);
        student.setRegisterDate("2021-12-11");
        students.add(student);

        student=new Student();
        student.setName("maryam");
        student.setEmail("maryam@gmail.com");
        student.setAge(25);
        student.setRegisterDate("2020-2-12");
        students.add(student);

        student=new Student();
        student.setName("akbar");
        student.setEmail("akbar@gmail.com");
        student.setAge(32);
        student.setRegisterDate("2018-3-19");
        students.add(student);

        student=new Student();
        student.setName("darya");
        student.setEmail("darya@gmail.com");
        student.setAge(23);
        student.setRegisterDate("2017-2-12");
        students.add(student);

        student=new Student();
        student.setName("asma");
        student.setEmail("asma@gmail.com");
        student.setAge(18);
        student.setRegisterDate("2020-11-12");
        students.add(student);

        return students;
    }
}