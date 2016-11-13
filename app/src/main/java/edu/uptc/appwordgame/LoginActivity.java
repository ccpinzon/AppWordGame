package edu.uptc.appwordgame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.uptc.appwordgame.Logic.User;
import edu.uptc.appwordgame.Persistence.DatabaseAccess;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "PruebaLoginActivity";
    private static final int REQUEST_SINGUP = 0;

    private EditText txtUser;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView linkSingup;


    private ArrayList<User> users;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_login);
        beginComponents();
        super.onCreate(savedInstanceState);

        loadUsers();
    }



    private void beginComponents() {

        txtUser = (EditText) findViewById(R.id.input_user);
        txtPassword = (EditText) findViewById(R.id.input_password);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                login();
            }
        });

        linkSingup = (TextView) findViewById(R.id.link_singup);
        linkSingup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(),SingupActivity.class);
                startActivityForResult(intent,REQUEST_SINGUP);
                // finish();
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

    }



    private void login() {
        Log.d(TAG,"LOGIN");

        if (!validateInputs()){
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        String user = txtUser.getText().toString();
        String pass = txtPassword.getText().toString();

        // TODO: HACER LOGICA DEL LOGIN

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (findUser(user)!=null &&  findUser(user).getPassword().equals(generateMD5(pass))) {
                            Toast.makeText(getBaseContext(),"Usuario y contraseña correctos",Toast.LENGTH_SHORT).show();

                            onLoginSuccess();
                        }else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);


    }

    public String generateMD5(String text) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.reset();
        m.update(text.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);

        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }
        return hashtext;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SINGUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    public void onLoginSuccess(){
        btnLogin.setEnabled(true);
        // TODO: LLEVA A LA ACTIVITY DEL JUEGO
        Intent intent = new Intent(getApplicationContext(),DificultActivity.class);
        intent.putExtra("user",txtUser.getText().toString());
        startActivity(intent);
        finish();
    }
    public void onLoginFailed(){
        Toast.makeText(getBaseContext(),"Error al logearse",Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }

    public boolean validateInputs(){
        boolean valid = true;

        String user = txtUser.getText().toString();
        String pass = txtPassword.getText().toString();

        if (user.isEmpty()) {
            txtUser.setError("Ingrese usuario vlaido");
        }
        else {
            txtUser.setError(null);
        }
        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10 ){
            txtPassword.setError("Ingrese entre 4 y 10 caracteres alfanumericos");
            valid = false;
        }
        else {
            txtPassword.setError(null);
        }
        return valid;

    }


    //bd ,methods

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
}
