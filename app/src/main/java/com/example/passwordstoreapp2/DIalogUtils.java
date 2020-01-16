package com.example.passwordstoreapp2;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DIalogUtils {

    public static Single<Boolean> showInsertAlert(Activity context, MyViewModel model){
        return Single.create(emitter -> {
            View view=View.inflate(context,R.layout.dialog_layout,null);
            EditText etName=view.findViewById(R.id.dialogName);
            EditText etLogin=view.findViewById(R.id.dialogLogin);
            EditText etPassword=view.findViewById(R.id.dialogPassword);

            new AlertDialog.Builder(context)
                    .setTitle(R.string.AddAlertTitle)
                    .setView(view)
                    .setPositiveButton(R.string.SaveField, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Password password=new Password();
                            if(etName!=null && etLogin!=null && etPassword!=null) {
                                password.setName(etName.getText().toString());
                                password.setLogin(etLogin.getText().toString());
                                password.setPassword(etPassword.getText().toString());
                                model.insert(password);
                                emitter.onSuccess(true);
                            }
                        }
                    })
                    .setNegativeButton(R.string.CancelField, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            emitter.onSuccess(false);
                            dialog.cancel();
                        }
                    })
                    .show();
        });
    }

    public static Single<Boolean> showEdittAlert(Activity context,Password password, MyViewModel model){
        return Single.create(emitter -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);

            View view=View.inflate(context,R.layout.dialog_layout,null);
                        EditText etName=view.findViewById(R.id.dialogName);
            EditText etLogin=view.findViewById(R.id.dialogLogin);
            EditText etPassword=view.findViewById(R.id.dialogPassword);
            etName.setText(password.getName());
            etLogin.setText(password.getLogin());
            etPassword.setText(password.getPassword());
            builder.setTitle(R.string.EditAlertTitle)
                    .setView(view)
                    .setPositiveButton(R.string.SaveField, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(etName!=null && etLogin!=null && etPassword!=null) {
                                password.setName(etName.getText().toString());
                                password.setLogin(etLogin.getText().toString());
                                password.setPassword(etPassword.getText().toString());
                                model.update(password);
                                emitter.onSuccess(true);
                            }
                        }
                    })
                    .setNegativeButton(R.string.CancelField, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            emitter.onSuccess(false);
                            dialog.cancel();
                        }
                    });
            AlertDialog alert=builder.create();
            alert.show();
        });
    }

    public static Completable showUndoSnackbar(View view, Password password, MyViewModel model){
        return Completable.create(emitter -> {
            Snackbar snackbar=Snackbar.make(view,"Item deleted",Snackbar.LENGTH_LONG);
            snackbar.setAction("Return delete", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.insert(password);
                }
            })
                    .show();
            emitter.onComplete();
        });
    }
}
