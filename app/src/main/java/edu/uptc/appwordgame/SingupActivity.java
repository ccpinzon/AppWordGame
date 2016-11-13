package edu.uptc.appwordgame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import edu.uptc.appwordgame.Logic.User;
import edu.uptc.appwordgame.Persistence.DatabaseAccess;

public class SingupActivity extends AppCompatActivity {
    private static final String TAG = "SingupActivity";

    private EditText _txtName;
    private EditText _txtUser;
    private EditText _txtPass;
    private EditText _txtReEnterPass;
    private Button _btnSingup;
    private TextView _loginLink;

    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_singup);
        beginComponents();
        super.onCreate(savedInstanceState);
        loadUsers();

    }



    private void beginComponents() {
        _txtName = (EditText) findViewById(R.id.input_name);
        _txtUser = (EditText) findViewById(R.id.input_nickname);
        _txtPass = (EditText) findViewById(R.id.input_password);
        _txtReEnterPass = (EditText) findViewById(R.id.input_reEnterPassword);

        _btnSingup = (Button) findViewById(R.id.btn_signup);
        _btnSingup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                singup();
            }
        });

        _loginLink = (TextView) findViewById(R.id.link_login);
        _loginLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_out,R.anim.push_left_in);
            }
        });
    }

    private void singup() {
        Log.d(TAG,"CREAR CUENTA");

        if (!validate()){
            onSingupFailed();
            return;
        }

        _btnSingup.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SingupActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creando Cuenta...");
        progressDialog.show();

        final String name = _txtName.getText().toString();
        final String user = _txtUser.getText().toString();
        final String pass = _txtPass.getText().toString();
        String rePass = _txtReEnterPass.getText().toString();



        final DatabaseAccess databaseAccess = new DatabaseAccess(this);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        databaseAccess.open();
                        if(findUser(user)!=null){
                            onSingupFailed();
                        }else {
                            databaseAccess.addUser(new User(null,name,user,generateMD5(pass),0));
                            users.removeAll(users);
                            loadUsers();
                            onSingupSuccess();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);




    }
    public void onSingupSuccess(){
        _btnSingup.setEnabled(true);
        setResult(RESULT_OK,null);
        this.finish();
    }
    public void onSingupFailed(){
        Toast.makeText(getBaseContext(), "Error al crear Cuenta", Toast.LENGTH_LONG).show();
        _btnSingup.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public boolean validate(){
        boolean valid = true;

        String name = _txtName.getText().toString();
        String user = _txtUser.getText().toString();
        String pass = _txtPass.getText().toString();
        String rePass = _txtReEnterPass.getText().toString();

        if (name.isEmpty() || name.length() < 3){
            _txtName.setError("Minimo 3 caracteres");
            valid = false;
        }else{
            _txtName.setError(null);
        }

        if (user.isEmpty() || user.length() <3){
            _txtUser.setError("Minimo 3 caracteres");
        }else{
            _txtUser.setError(null);
        }

        if (pass.isEmpty() || pass.length()  < 4 || pass.length() > 10){
            _txtPass.setError("Contraseña invalida");
            valid = false;
        }else{
            _txtPass.setError(null);
        }
        if (rePass.isEmpty() || rePass.length()  < 4 || rePass.length() > 10){
            _txtReEnterPass.setError("Contraseña invalida");
            valid = false;
        }else{
            _txtReEnterPass.setError(null);
        }

        if (!pass.equals(rePass)){
            valid = false;
            Toast.makeText(getBaseContext(),"Las contraseñas no coiniciden",Toast.LENGTH_LONG).show();
        }

        return valid;
    }

    //bd methods

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

    //md5generate
    public String generateMD5(String text)  {
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
}
