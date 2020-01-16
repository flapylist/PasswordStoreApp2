package com.example.passwordstoreapp2;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.passwordstoreapp2.DaggerPasswordComponent;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyViewModel extends ViewModel {
    private PasswordComponent component= DaggerPasswordComponent.builder()
            .passwordDaoModule(new PasswordDaoModule(ReleaseApp.getInstance().getApplicationContext()))
            .build();

    private Repository repository=component.getRepo();

    private CompositeDisposable compositeDisposable=new CompositeDisposable();

    public CompositeDisposable getCompositeDisposable(){
        return compositeDisposable;
    }

    private MutableLiveData<List<Password>> passwordLiveData=new MutableLiveData<>();

    public void insert(Password password){
        repository.insert(password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        getAll();
                        Toast.makeText(ReleaseApp.getInstance().getApplicationContext(),"Item added",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ReleaseApp.getInstance().getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void update(Password password){
        repository.update(password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        getAll();
                        Toast.makeText(ReleaseApp.getInstance().getApplicationContext(),"Item edited",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ReleaseApp.getInstance().getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void delete(Password password){
        repository.delete(password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        getAll();
                        Toast.makeText(ReleaseApp.getInstance().getApplicationContext(),"Item deleted",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ReleaseApp.getInstance().getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public LiveData<List<Password>> getAll(){
        repository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<Password>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Password> list) {
                        passwordLiveData.setValue(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ReleaseApp.getInstance().getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

        return passwordLiveData;
    }
}
