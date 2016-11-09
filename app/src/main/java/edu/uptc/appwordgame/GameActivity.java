package edu.uptc.appwordgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import edu.uptc.appwordgame.Persistence.DatabaseAccess;

public class GameActivity extends AppCompatActivity {
    private ArrayList<String> words;
    private ImageButton _btnFine;
    private Button _btn0;
    private Button _btn1;
    private Button _btn2;
    private Button _btn3;

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

        //palabra aleatoria
        String word = randomWordBd(4);
        Log.d("RANDMON",word);
        _btn0.setText(word.substring(2,3));
        _btn1.setText(word.substring(3,4));
        _btn2.setText(word.substring(0,1));
        _btn3.setText(word.substring(1,2));


        _btnFine = (ImageButton) findViewById(R.id.btnFine);
        _btnFine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wordExists("ajhsdjshdbf2")){
                    Toast.makeText(getBaseContext(), "Existe!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(), "No existe!", Toast.LENGTH_LONG).show();
                }
            }
        });
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
