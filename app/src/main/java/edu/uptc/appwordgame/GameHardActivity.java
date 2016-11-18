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
                juego.add(char7.toUpperCase());
                _labelLetras.setText(arrayToString(juego));
                _btn7.setEnabled(false);
            }
        });

        Vibrator v = (Vibrator) this.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        currentPlayWords = new ArrayList<String>();
        _btnFine = (ImageButton) findViewById(R.id.btnFineHard);
        _btnFine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = cutSpaces(_labelLetras.getText().toString().toLowerCase());
                if (wordExists(word) && (!wordExistsCurrentPlay(word))){
                    currentPlayWords.add(word);
                    score = score + calculateScore(word);
                    Toast.makeText(getBaseContext(), "Existe!", Toast.LENGTH_SHORT).show();
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

        _btnError = (ImageButton) findViewById(R.id.btnErrorHard);
        _btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.removeAll(juego);
                _labelLetras.setText("");
                enableButtons(true);
            }
        });

    }

    private void enableButtons(boolean aBoolean) {
        _btn0.setEnabled(aBoolean);
        _btn1.setEnabled(aBoolean);
        _btn2.setEnabled(aBoolean);
        _btn3.setEnabled(aBoolean);
        _btn4.setEnabled(aBoolean);
        _btn5.setEnabled(aBoolean);
        _btn6.setEnabled(aBoolean);
        _btn7.setEnabled(aBoolean);
    }

    public int calculateScore(String word){
        int len = word.length();
        return len*75;
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

    public String cutSpaces(String word) {
        String[] letters = word.split(" ");
        String out = "";
        for (String letter : letters) {
            out = out + letter;
        }
        return out;
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
    @Override
    public void onBackPressed() {
        count = 0;
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

    public User findUser(String nickname){
        for (User user:users ) {
            if (user.getNickName().equals(nickname)) {
                return user;
            }
        }
        return null;
    }

}
