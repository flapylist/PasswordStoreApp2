package com.example.passwordstoreapp2;

import android.content.Context;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordstoreapp2.databinding.ListView1Binding;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PasswordRecyclerAdapter1 extends RecyclerView.Adapter<PasswordRecyclerAdapter1.ViewHolder> {
    private LayoutInflater inflater;
    private List<Password> passwordList;
    private Context mcontext;

    PasswordRecyclerAdapter1(Context context, List<Password> passwordList) {
        this.passwordList=passwordList;
        inflater = LayoutInflater.from(context);
        mcontext = context;
    }

    public PasswordRecyclerAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListView1Binding binding= DataBindingUtil.inflate(inflater,R.layout.list_view1,parent,false);
        return new ViewHolder(binding);
    }

    public void onBindViewHolder(final PasswordRecyclerAdapter1.ViewHolder holder, final int position){
        holder.bind(passwordList.get(position));

        holder.setViewOnClick(passwordList.get(position),position);

        holder.binding.chkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(holder.binding.chkPassword.isChecked())
                holder.binding.tvPassword.setTransformationMethod(null);
                if(!holder.binding.chkPassword.isChecked())
                    holder.binding.tvPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
        }
        );
    }


    @Override
    public int getItemCount(){
try {
    return passwordList.size();
}catch (NullPointerException e){
    Log.d("TAG",e.toString());
    return 0;
}
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ListView1Binding binding;

        public ViewHolder(ListView1Binding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void bind(Password password){
            binding.setPassword(password);
            binding.executePendingBindings();
        }

        public void setViewOnClick(final Password item, final int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new EditEvent(item,position));
                }
            });
        }
    }

    public Context getContext(){
        return mcontext;
    }

}
