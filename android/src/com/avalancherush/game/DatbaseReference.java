package com.avalancherush.game;

import android.util.Log;

import com.avalancherush.game.Singletons.GameThread;
import com.avalancherush.game.Singletons.MultiPlayerGameThread;
import com.badlogic.gdx.Game;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static android.content.ContentValues.TAG;

import static java.lang.Math.round;

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
                String keyName = snapshot.getKey();
                String value = snapshot.getValue().toString();
                if(keyName.equals("playerA")){
                    server.playerA = value;
                } else if (keyName.equals("playerB")) {
                    server.playerB = value;
                } else if (keyName.equals("playerAStatus")) {
                    server.playerAStatus = value;
                } else if (keyName.equals("playerBStatus")) {
                    server.playerBStatus = value;
                } else if (keyName.equalsIgnoreCase("playerAScore")) {
                    server.playerAScore = round(Float.parseFloat(value));
                } else if (keyName.equalsIgnoreCase("playerBScore")) {
                    server.playerBScore = round(Float.parseFloat(value));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String keyName = snapshot.getKey();
                String value = snapshot.getValue().toString();
                if(keyName.equals("playerA")){
                    server.playerA = value;
                } else if (keyName.equals("playerB")) {
                    server.playerB = value;
                } else if (keyName.equals("playerAStatus")) {
                    server.playerAStatus = value;
                } else if (keyName.equals("playerBStatus")) {
                    server.playerBStatus = value;
                } else if (keyName.equalsIgnoreCase("playerAScore")) {
                    server.playerAScore = round(Float.parseFloat(value));
                } else if (keyName.equalsIgnoreCase("playerBScore")) {
                    server.playerBScore = round(Float.parseFloat(value));
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

    @Override
    public void ScoreChangeListener() {
        GameThread instance = GameThread.getInstance();
        DatabaseReference myRef = database.getReference("score1");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().toString().equalsIgnoreCase("name")){
                    instance.score1Name = snapshot.getValue().toString();
                }
                else if(snapshot.getKey().toString().equalsIgnoreCase("score")){
                    instance.score1Score = snapshot.getValue().toString();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
        myRef = database.getReference("score2");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().toString().equalsIgnoreCase("name")){
                    instance.score2Name = snapshot.getValue().toString();
                }
                else if(snapshot.getKey().toString().equalsIgnoreCase("score")){
                    instance.score2Score = snapshot.getValue().toString();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
        myRef = database.getReference("score3");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().toString().equalsIgnoreCase("name")){
                    instance.score3Name = snapshot.getValue().toString();
                }
                else if(snapshot.getKey().toString().equalsIgnoreCase("score")){
                    instance.score3Score = snapshot.getValue().toString();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
    public void idChangeListener(String key) {
        DatabaseReference myRef = database.getReference(key);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MultiPlayerGameThread.getInstance().setGameId(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}
