package edu.uptc.appwordgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import edu.uptc.appwordgame.Persistence.DatabaseAccess;

public class GameActivity extends AppCompatActivity {
    private ArrayList<String> words;
    private ImageButton _btnFine;
    private ImageButton _btnError;
    private Button _btn0;
    private Button _btn1;
    private Button _btn2;
    private Button _btn3;
    private TextView _labelLetras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadWords();
        setContentView(R.layout.activity_game);
        beginComponents();

    }

    private void beginComponents() {

        _btn0 = (Button) findViewById(R.id.btn0_lvlEasy);
        _btn1 = (Button) findViewById(R.id.btn1_lvlEasy);
        _btn2 = (Button) findViewById(R.id.btn2_lvlEasy);
        _btn3 = (Button) findViewById(R.id.btn3_lvlEasy);
        _labelLetras = (TextView) findViewById(R.id.labelLetras);

        //palabra aleatoria
        String word = randomWordBd(4);
        Log.d("RANDMON",word);

        String char0 = word.substring(2,3);
        String char1 = word.substring(3,4);
        String char2 = word.substring(0,1);
        String char3 = word.substring(1,2);


        ArrayList<String> juego =  new ArrayList<>();
        _btn0.setText(char0);
        _btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char0);
                _labelLetras.setText(arrayToString(juego));
                _btn0.setEnabled(false);
            }
        });
        _btn1.setText(char1);
        _btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char1);
                _labelLetras.setText(arrayToString(juego));
                _btn1.setEnabled(false);
            }
        });
        _btn2.setText(char2);
        _btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char2);
                _labelLetras.setText(arrayToString(juego));
                _btn2.setEnabled(false);
            }
        });
        _btn3.setText(char3);
        _btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.add(char3);
                _labelLetras.setText(arrayToString(juego));
                _btn3.setEnabled(false);
            }
        });



        _btnFine = (ImageButton) findViewById(R.id.btnFine);
        _btnFine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = cutSpaces(_labelLetras.getText().toString());
                if (wordExists(word)){
                    Toast.makeText(getBaseContext(), "Existe!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "No existe!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        _btnError = (ImageButton) findViewById(R.id.btnError);
        _btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juego.removeAll(juego);
                _labelLetras.setText("");

                _btn0.setEnabled(true);
                _btn1.setEnabled(true);
                _btn2.setEnabled(true);
                _btn3.setEnabled(true);

            }
        });
    }

    public String cutSpaces(String word){
        String[] letters = word.split(" ");
        String out = "";
        for (String letter:letters){
            out = out + letter;
        }
        return out;
    }

    public String arrayToString(ArrayList<String> arrayLetras){
        String out = "";
        for (String letra: arrayLetras) {
            out = out + letra + " ";
        }
        return out;
    }

    public String randomWord(int len){
        String AB = "aaaaaaabbbbccccddddeeeeeeeeeefghiiiiiiijklmnññooooooooopqrrrrrrrrstuuuuuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
    public String randomWordBd(int len){
        ArrayList<String> wordsCuts = new ArrayList<>();
        String randmonWord= "";
        for (String word: words) {
            if (word.length()==len){
                wordsCuts.add(word);
            }
        }
        int lenWordCuts = wordsCuts.size();
        Random rn = new Random();
        randmonWord = wordsCuts.get(rn.nextInt(lenWordCuts - 0 + 1) +0);
        return randmonWord;
    }


    public void loadWords(){
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
        databaseAccess.open();
        words = databaseAccess.getWords();
        databaseAccess.close();
    }

    public boolean wordExists(String wordSearch){
        for (String word:words) {
            if(wordSearch.equals(word)){
                return true;
            }
        }
        return false;
    }
}
