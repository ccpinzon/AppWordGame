package edu.uptc.appwordgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.uptc.appwordgame.Logic.User;
import edu.uptc.appwordgame.Persistence.DatabaseAccess;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "PRUEBA_MainActivity";
    private ListView listView;
    private ArrayList<User> users;

    private Button _btnLogin;
    private Button _btnSingUp;
    private Button _btnShowScores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        beginComponents();
        super.onCreate(savedInstanceState);
//        Intent intent = new Intent(this,LoginActivity.class);
//        startActivity(intent);
      //  this.listView = (ListView) findViewById(R.id.scoresListView);

        //cargar usuarios al arreglo
        //loadUsers();


    }

    private void beginComponents() {
        _btnLogin = (Button) findViewById(R.id.btn_MainLogin);
        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
            }
        });


        _btnSingUp = (Button) findViewById(R.id.btn_MainSingUp);
        _btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSingUp = new Intent(getApplicationContext(), SingupActivity.class);
                startActivity(intentSingUp);
            }
        });

        _btnShowScores = (Button)  findViewById(R.id.btn_ShowScores);
        _btnShowScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentScores =  new Intent(getApplicationContext(),ScoreActivity.class);
                startActivity(intentScores);
            }
        });

    }

    //metodos users!
    public void loadUsers() {
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
        databaseAccess.open();
        users = (ArrayList<User>) databaseAccess.getUsers();
        databaseAccess.close();

        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);
        this.listView.setAdapter(adapter);
    }

    public User findUser(String nickname) {
        for (User user : users) {
            if (user.getNickName().equals(nickname)) {
                return user;
            }
        }
        return null;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
