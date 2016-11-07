package edu.uptc.appwordgame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SINGUP = 0;


    private EditText txtUser;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView linkSingup;


    public LoginActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_login);
        beginComponents();
        super.onCreate(savedInstanceState);
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
                // TODO: HACER ACTIVITY DE CREACION DE USUARIO
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
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
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
        moveTaskToBack(true);
    }

    public void onLoginSuccess(){
        btnLogin.setEnabled(true);
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


}

