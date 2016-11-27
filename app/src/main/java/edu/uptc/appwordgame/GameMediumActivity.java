package edu.uptc.appwordgame;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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

    int contChangeWords = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoggedUser();
        loadWords();
        loadUsers();

        setContentView(R.layout.activity_game_medium);

        beginComponents();
        createTimer();
    }

    private void createTimer() {

        final MediaPlayer mp = MediaPlayer.create(this,R.raw.click);
        final MediaPlayer mp2 = MediaPlayer.create(this,R.raw.click);
        final MediaPlayer mp3 = MediaPlayer.create(this,R.raw.click);


        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        _textViewTimer.setText(""+count);
                        count--;
                        mp.start();
                        if (count <=5){
                            mp2.start();
                            if (count <=3 )
                                mp3.start();
                        }
                        if (count < 0){
                            Intent intent = new Intent(getApplicationContext(),ScoreActivity.class);
                            intent.putExtra("comesMain","0");
                            intent.putExtra("user",loggedUser);
                            startActivity(intent);
                            saveScore(loggedUser);
                            mp.stop();
                            mp2.stop();
                            mp3.stop();
                            finish();
                            T.cancel();
                        }
                    }
                });
            }
        }, 1000, 1000);
    }
    private void saveScore(String user) {
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
        databaseAccess.open();
        User us = findUser(user);
        if (us.getScore()<=score) {
            databaseAccess.addScore(findUser(user), score);
        }
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


        ArrayList<String> juego = new ArrayList<>();

        changeWord();
        _btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(_btn0.getText().toString().toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn0.setEnabled(false);
            }
        });
        _btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(_btn1.getText().toString().toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn1.setEnabled(false);
            }
        });
        _btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(_btn2.getText().toString().toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn2.setEnabled(false);
            }
        });
        _btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(_btn3.getText().toString().toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn3.setEnabled(false);
            }
        });
        _btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(_btn4.getText().toString().toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn4.setEnabled(false);
            }
        });
        _btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(_btn5.getText().toString().toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn5.setEnabled(false);
            }
        });

        Vibrator v = (Vibrator) this.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);

        currentPlayWords = new ArrayList<String>();
        _btnFine = (ImageButton) findViewById(R.id.btnFineMedium);
        _btnFine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = cutSpaces(_labelLetras.getText().toString().toLowerCase());
                if (wordExists(word) && (!wordExistsCurrentPlay(word))){
                    currentPlayWords.add(word);
                    score = score+calculateScore(word);
                    Toast.makeText(getBaseContext(), "Existe!", Toast.LENGTH_SHORT).show();
                    if (word.length()>=4)
                        contChangeWords = contChangeWords +1;
                    if (contChangeWords>=2){
                        changeWord();
                        count = count + 8 ;
                        contChangeWords = 0;
                    }
                }else if (wordExistsCurrentPlay(word)){
                    Toast.makeText(getBaseContext(), "Ya agregada", Toast.LENGTH_SHORT).show();
                    long[] pattern = { 100, 100,100, 100, 100};
                    v.vibrate(pattern , -1);
                }else{
                    Toast.makeText(getBaseContext(), "No existe!", Toast.LENGTH_SHORT).show();
                    v.vibrate(500);
                }
                _labelLetras.setText("");
                juego.removeAll(juego);
                enableButtons(true);
                _textViewScore.setText(String.valueOf(score));
            }
        });

        _btnError = (ImageButton) findViewById(R.id.btnErrorMedium);
        _btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.removeAll(juego);
                _labelLetras.setText("");
                enableButtons(true);
            }
        });
    }

    private void changeWord() {
        String word = randomWordBd(6);
        Log.d("RANDOM_WORD", word);

        String char0 = word.substring(4, 5);
        String char1 = word.substring(3, 4);
        String char2 = word.substring(5, 6);
        String char3 = word.substring(1, 2);
        String char4 = word.substring(0, 1);
        String char5 = word.substring(2, 3);

        _btn0.setText(char0);
        _btn1.setText(char1);
        _btn2.setText(char2);
        _btn3.setText(char3);
        _btn4.setText(char4);
        _btn5.setText(char5);

    }

    public void enableButtons(Boolean aBoolean) {
        _btn0.setEnabled(aBoolean);
        _btn1.setEnabled(aBoolean);
        _btn2.setEnabled(aBoolean);
        _btn3.setEnabled(aBoolean);
        _btn4.setEnabled(aBoolean);
        _btn5.setEnabled(aBoolean);
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
    public String arrayToString(ArrayList<String> arrayLetras) {
        String out = "";
        for (String letra : arrayLetras) {
            out = out + letra + " ";
        }
        return out;
    }
    public String cutSpaces(String word) {
        String[] letters = word.split(" ");
        String out = "";
        for (String letter : letters) {
            out = out + letter;
        }
        return out;
    }

    public boolean wordExists(String wordSearch) {
        for (String word : words) {
            if (wordSearch.equals(word)) {
                return true;
            }
        }
        return false;
    }
    public boolean wordExistsCurrentPlay(String wordSearch) {
        for (String word : currentPlayWords) {
            if (wordSearch.equals(word)) {
                return true;
            }
        }
        return false;
    }
    public int calculateScore(String word){
        int len = word.length();
        return len*75;
    }
    @Override
    public void onBackPressed() {
        count = 0;
    }
}