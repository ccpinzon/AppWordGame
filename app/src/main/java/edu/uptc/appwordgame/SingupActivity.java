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

public class SingupActivity extends AppCompatActivity {
    private static final String TAG = "SingupActivity";

    private EditText _txtName;
    private EditText _txtUser;
    private EditText _txtPass;
    private EditText _txtReEnterPass;
    private Button _btnSingup;
    private TextView _loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_singup);
        beginComponents();
        super.onCreate(savedInstanceState);

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

        String name = _txtName.getText().toString();
        String user = _txtUser.getText().toString();
        String pass = _txtPass.getText().toString();
        String rePass = _txtReEnterPass.getText().toString();

        // TODO: LOGIA DE CREACIONDE CUENTAS

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSingupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }
    public void onSingupSuccess(){
        _btnSingup.setEnabled(true);
        setResult(RESULT_OK,null);
        finish();
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
}
