package edu.uptc.appwordgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import edu.uptc.appwordgame.Logic.User;
import edu.uptc.appwordgame.Persistence.DatabaseAccess;

public class GameHardActivity extends AppCompatActivity {

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
    private Button _btn6;
    private Button _btn7;
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
        setContentView(R.layout.activity_game_hard);
        beginComponents();
    }

    private void beginComponents() {
        _btn0 = (Button) findViewById(R.id.btn0_lvlHard);
        _btn1 = (Button) findViewById(R.id.btn1_lvlHard);
        _btn2 = (Button) findViewById(R.id.btn2_lvlHard);
        _btn3 = (Button) findViewById(R.id.btn3_lvlHard);
        _btn4 = (Button) findViewById(R.id.btn4_lvlHard);
        _btn5 = (Button) findViewById(R.id.btn5_lvlHard);
        _btn6 = (Button) findViewById(R.id.btn6_lvlHard);
        _btn7 = (Button) findViewById(R.id.btn7_lvlHard);

        _labelLetras = (TextView) findViewById(R.id.labelLetrasHard);

        _textViewUser = (TextView) findViewById(R.id.text_lvlHardUser);
        _textViewUser.setText(loggedUser);

        _textViewScore = (TextView) findViewById(R.id.text_lvlHardScore);
        _textViewTimer = (TextView) findViewById(R.id.text_lvlHardTimer);

        String word = randmonWord(8);
        Log.d("RANDOM_WORD", word);

        String char0 = word.substring(7, 8);
        String char1 = word.substring(3, 4);
        String char2 = word.substring(5, 6);
        String char3 = word.substring(4, 5);
        String char4 = word.substring(1, 2);
        String char5 = word.substring(0, 1);
        String char6 = word.substring(6, 7);
        String char7 = word.substring(2, 3);

        ArrayList<String> juego = new ArrayList<>();

        _btn0.setText(char0);
        _btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char0.toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn0.setEnabled(false);
            }
        });

        _btn1.setText(char1);
        _btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char1.toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn1.setEnabled(false);
            }
        });

        _btn2.setText(char2);
        _btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char2.toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn2.setEnabled(false);
            }
        });

        _btn3.setText(char3);
        _btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char3.toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn3.setEnabled(false);
            }
        });

        _btn4.setText(char4);
        _btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char4.toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn4.setEnabled(false);
            }
        });

        _btn5.setText(char5);
        _btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char5.toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn5.setEnabled(false);
            }
        });

        _btn6.setText(char6);
        _btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char6.toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn6.setEnabled(false);
            }
        });

        _btn7.setText(char7);
        _btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char0.toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn7.setEnabled(false);
            }
        });
    }

    private String randmonWord(int len) {
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
    public String arrayToString(ArrayList<String> arrayLetras) {
        String out = "";
        for (String letra : arrayLetras) {
            out = out + letra + " ";
        }
        return out;
    }

    private void loadUsers() {
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
        databaseAccess.open();
        users = (ArrayList<User>) databaseAccess.getUsers();
        databaseAccess.close();
    }

    private void loadWords() {
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
        databaseAccess.open();
        words = databaseAccess.getWords();
        databaseAccess.close();
    }

    private void getLoggedUser() {
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            loggedUser = extras.getString("user");
        }
    }


}
