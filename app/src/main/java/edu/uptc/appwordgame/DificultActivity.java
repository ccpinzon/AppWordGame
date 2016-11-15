package edu.uptc.appwordgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DificultActivity extends AppCompatActivity {
    private Button _btnLvlEasy;
    private Button _btnLvlMedium;
    private Button _btnLvlHard;
    private String loggedUser;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActualUser();
        setContentView(R.layout.activity_dificult);
        beginComponents();
    }

    private void getActualUser() {
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            loggedUser = extras.getString("user");
        }
    }

    private void beginComponents() {
        //lvl facil
        _btnLvlEasy = (Button) findViewById(R.id.btn_lvlEasy);
        _btnLvlEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),GameActivity.class);
                intent.putExtra("user",loggedUser);
                startActivity(intent);
            }
        });

        //lvl medium
        _btnLvlMedium = (Button) findViewById(R.id.btn_lvlMedium);
        _btnLvlHard = (Button) findViewById(R.id.btn_lvlHard);
    }
}
