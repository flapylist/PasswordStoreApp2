package com.example.passwordstoreapp2;

public class EditEvent {
    public Password item;
    public int position;

    public EditEvent(Password item,int position){
        this.item=item;
        this.position=position;
    }

}
