package edu.uptc.appwordgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.uptc.appwordgame.Logic.HaldingUsers;
import edu.uptc.appwordgame.Logic.User;
import edu.uptc.appwordgame.Persistence.DatabaseAccess;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        //Intent intent = new Intent(this,LoginActivity.class);
        //startActivity(intent);

        this.listView = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
         databaseAccess.open();
        List<User> users = databaseAccess.getUsers();
        databaseAccess.close();

        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,android.R.layout.simple_list_item_1,users);
        this.listView.setAdapter(adapter);


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
        int id= item.getItemId();
        if (id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
