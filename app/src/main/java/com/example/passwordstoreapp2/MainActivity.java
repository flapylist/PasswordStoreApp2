package com.example.passwordstoreapp2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;


public class MainActivity extends AppCompatActivity {

    List<Password> passwordList;
    PasswordRecyclerAdapter1 adapter;
    RecyclerView recyclerView;
    Activity context;
    FloatingActionButton floatActBtn;
    LinearLayoutManager layoutManager;
    MyViewModel model;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_listview);
        EventBus.getDefault().register(this);
        context = this;
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(context);
        floatActBtn = findViewById(R.id.floating_action_button);
        floatActBtn.setOnClickListener(view -> {
            DIalogUtils.showInsertAlert(context, model)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getAll().observe(this, this::refreshData);
    }

        public void refreshData(List<Password> passwords){
            passwordList=passwords;
            adapter=new PasswordRecyclerAdapter1(context,passwordList);

            recyclerView.setLayoutManager(layoutManager);

            ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
            itemTouchHelper.attachToRecyclerView(recyclerView);


            DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);

            refreshList(recyclerView,adapter);
    }

    public void refreshList(RecyclerView list, RecyclerView.Adapter adapter){
        if(list==null) return;

        int bottomItem=0;
        int topItem=0;
        LinearLayoutManager linearLayoutManager=(LinearLayoutManager)list.getLayoutManager();
        if(layoutManager!=null){
            bottomItem=linearLayoutManager.findFirstVisibleItemPosition();
        }
        View v=list.getChildAt(0);
        topItem= (v==null) ? 0 : v.getTop();
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if(layoutManager!=null) linearLayoutManager.scrollToPositionWithOffset(bottomItem,topItem);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEditEvent(EditEvent event){
        DIalogUtils.showEdittAlert(context,event.item,model)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteEvent(DeleteEvent event){
        Password password=passwordList.get(event.position);
        model.delete(password);

        DIalogUtils.showUndoSnackbar(recyclerView,password,model)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
