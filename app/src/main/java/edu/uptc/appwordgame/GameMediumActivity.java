package edu.uptc.appwordgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import edu.uptc.appwordgame.Logic.User;
import edu.uptc.appwordgame.Persistence.DatabaseAccess;

public class GameMediumActivity extends AppCompatActivity {

    private ArrayList<String> words;
    private ArrayList<String> currentPlayWords;
    private ImageButton _btnFine;
    private ImageButton _btnError;
    private Button _btn0;
    private Button _btn1;
    private Button _btn2;
    private Button _btn3;
    private Button _btn4;
    private Button _btn5;

    private TextView _labelLetras;
    private String loggedUser;
    private int score = 0;
    private TextView _textViewUser;
    private TextView _textViewScore;
    private TextView _textViewTimer;
    private int count = 30;

    private ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoggedUser();
        loadWords();
        loadUsers();
        setContentView(R.layout.activity_game_medium);
        beginComponents();
    }

    private void beginComponents() {
        _btn0 = (Button) findViewById(R.id.btn0_lvlMedium);
        _btn1 = (Button) findViewById(R.id.btn1_lvlMedium);
        _btn2 = (Button) findViewById(R.id.btn2_lvlMedium);
        _btn3 = (Button) findViewById(R.id.btn3_lvlMedium);
        _btn4 = (Button) findViewById(R.id.btn4_lvlMedium);
        _btn5 = (Button) findViewById(R.id.btn5_lvlMedium);
        _labelLetras = (TextView) findViewById(R.id.labelLetrasMedium);

        _textViewUser = (TextView) findViewById(R.id.text_lvlMediumUser);
        _textViewUser.setText(loggedUser);

        _textViewScore = (TextView) findViewById(R.id.text_lvlMediumScore);
        _textViewTimer = (TextView) findViewById(R.id.text_lvlMediumTimer);

        //palabra aleatoria
        String word = randomWordBd(6);
        Log.d("RANDOMWORD", word);

        String char0 = word.substring(4, 5);
        String char1 = word.substring(3, 4);
        String char2 = word.substring(5, 6);
        String char3 = word.substring(1, 2);
        String char4 = word.substring(0, 1);
        String char5 = word.substring(2, 3);

        ArrayList<String> juego = new ArrayList<>();
        _btn0.setText(char0);
        _btn1.setText(char1);
        _btn2.setText(char2);
        _btn3.setText(char3);
        _btn4.setText(char4);
        _btn5.setText(char5);

    }


    private void getLoggedUser() {
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            loggedUser = extras.getString("user");
        }
    }

    public void loadWords() {
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
        databaseAccess.open();
        words = databaseAccess.getWords();
        databaseAccess.close();
    }
    private void loadUsers() {
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
        databaseAccess.open();
        users = (ArrayList<User>) databaseAccess.getUsers();
        databaseAccess.close();
    }

    public User findUser(String nickname){
        for (User user:users ) {
            if (user.getNickName().equals(nickname)) {
                return user;
            }
        }
        return null;
    }

    public String randomWordBd(int len) {
        ArrayList<String> wordsCuts = new ArrayList<>();
        String randmonWord = "";
        for (String word : words) {
            if (word.length() == len) {
                wordsCuts.add(word);
            }
        }
        int lenWordCuts = wordsCuts.size();
        Random rn = new Random();
        randmonWord = wordsCuts.get(rn.nextInt(lenWordCuts - 0 + 1) + 0);
        return randmonWord;
    }
}
