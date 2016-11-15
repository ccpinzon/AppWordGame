package edu.uptc.appwordgame;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private Button _btnTryagain;
    private String comesMain = "0";

    private ListView _ListScores;
    private String loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        scores = new ArrayList<>();
        loadScores();
        getLoggedUser();
        //Toast.makeText(getBaseContext(), "Usuario : " + loggedUser, Toast.LENGTH_SHORT).show();
        beginComponents();
    }


    private void beginComponents() {
        _ListScores = (ListView) findViewById(R.id.scoresListView);
        for (String score:scores) {
            Log.d(TAG,score);
        }
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,scores);
        _ListScores.setAdapter(adapter);

        getDataMain();
        _btnTryagain = (Button) findViewById(R.id.btnTryAgain);
        if (comesMain.equals("1")){
            _btnTryagain.setVisibility(View.GONE);
        }else if (comesMain.equals("0")){
            _btnTryagain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentLvls = new Intent(getApplicationContext(), DificultActivity.class);
                    intentLvls.putExtra("user",loggedUser);
                    startActivity(intentLvls);
                }
            });
        }
    }


    public void getDataMain(){
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            comesMain = extras.getString("comesMain");
        }
    }

    private void getLoggedUser() {
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            loggedUser = extras.getString("user");
        }
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
