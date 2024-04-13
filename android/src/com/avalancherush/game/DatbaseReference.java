package com.avalancherush.game;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatbaseReference implements FirebaseInterface{
    FirebaseDatabase database;
    public DatbaseReference(){
        database = FirebaseDatabase.getInstance("https://avalanche-rush-ntnu-6fbfc-default-rtdb.europe-west1.firebasedatabase.app/");
    }
    @Override
    public void setValueToServerDataBase(String id, String key, String value) {

        DatabaseReference serverReference = database.getReference(id);
        DatabaseReference myRef = serverReference.child(key);
        myRef.setValue(value);
    }

    @Override
    public void serverChangeListener(Server server) {
        DatabaseReference myRef = database.getReference(server.id);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                System.out.println(snapshot);
                String keyName = snapshot.getKey();
                String value = snapshot.getValue().toString();
                if(keyName.equals("playerA")){
                    server.playerA = value;
                    System.out.println("server.playerA Values changed to"+ server.playerA);
                } else if (keyName.equals("playerB")) {
                    server.playerB = value;
                } else if (keyName.equals("playerAStatus")) {
                    server.playerAStatus = value;
                } else if (keyName.equals("playerBStatus")) {
                    server.playerBStatus = value;
                } else if (keyName.equals("playerAScore")) {
                    server.playerAScore = (int)snapshot.getValue();
                } else if (keyName.equals("playerBScore")) {
                    server.playerBScore = (int)snapshot.getValue();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                System.out.println("onChildAdded");
                System.out.println(snapshot);
                System.out.println(previousChildName);
                String keyName = snapshot.getKey();
                String value = snapshot.getValue().toString();
                if(keyName.equals("playerA")){
                    server.playerA = value;
                    System.out.println("server.playerA Values changed to"+ server.playerA);
                } else if (keyName.equals("playerB")) {
                    server.playerB = value;
                } else if (keyName.equals("playerAStatus")) {
                    server.playerAStatus = value;
                } else if (keyName.equals("playerBStatus")) {
                    server.playerBStatus = value;
                } else if (keyName.equals("playerAScore")) {
                    server.playerAScore = (int)snapshot.getValue();
                } else if (keyName.equals("playerBScore")) {
                    server.playerBScore = (int)snapshot.getValue();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void setValueToDataBase(String key, String value){
        DatabaseReference myRef = database.getReference(key);
        myRef.setValue(value);
    }

}
