package edu.uptc.appwordgame;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uptc.appwordgame.Logic.User;
import edu.uptc.appwordgame.Persistence.DatabaseAccess;

public class ScoreActivity extends AppCompatActivity {
    public static final String TAG = "SCORES";
    private ArrayList<User> users;
    private ArrayList<String> scores;

    private ListView _ListScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        scores = new ArrayList<>();
        loadScores();
        beginComponents();
    }


    private void beginComponents() {
        _ListScores = (ListView) findViewById(R.id.scoresListView);
        for (String score:scores) {
            Log.d(TAG,score);
        }
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,scores);
        _ListScores.setAdapter(adapter);
    }

    private void loadScores() {
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
        databaseAccess.open();
        users = (ArrayList<User>) databaseAccess.getUsers();

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                String score1 = String.valueOf(user1.getScore());
                String score2 = String.valueOf(user2.getScore());
                return score2.compareTo(score1);
            }
        });
        databaseAccess.close();

        int indexScore = 0;
        for (User user:users) {
            indexScore ++;
            String aux = indexScore +"). "+ user.getName()+"\t -> "+String.valueOf(user.getScore());
            scores.add(aux);
        }

    }
}
