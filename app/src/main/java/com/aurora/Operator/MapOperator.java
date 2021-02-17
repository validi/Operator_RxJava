package com.aurora.Operator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.aurora.Operator.model.Student;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MapOperator extends AppCompatActivity {

    public static String TAG = "MyTag";
    private Observable<Student> myObservable;

    private DisposableObserver<Student> myDisposableObserver;

    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_operator);


        myObservable = Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Student> emitter) throws Throwable {
                try{
                    ArrayList<Student> students=getStudent();
                    for (int i = 0; i <students.size() ; i++) {
                        emitter.onNext(students.get(i));
                    }
                    emitter.onComplete();
                }catch (Exception e){
                    emitter.onError(e);
                }
            }
        });

        compositeDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Function<Student, Student>() {
                            @Override
                            public Student apply(Student student) throws Throwable {
                                student.setName(student.getName().toUpperCase());
                                return student;
                            }
                        })
                        .subscribeWith(getObserve()));

    }
    public DisposableObserver getObserve(){
        myDisposableObserver = new DisposableObserver<Student>() {
            @Override
            public void onNext(@NonNull Student s) {
                Log.i(TAG, "onNext   ----  "+s.getName().toString());
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