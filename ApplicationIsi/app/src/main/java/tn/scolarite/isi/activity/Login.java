package tn.scolarite.isi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import tn.scolarite.isi.R;
import tn.scolarite.isi.service.SharedPreferences;

public class Login extends AppCompatActivity {

    public EditText edt_username;
    public EditText edt_password;
    public TextView err_txt;
    public Button btn_login;
    private SharedPreferences sharedPreferences;
    public String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        err_txt = findViewById(R.id.err_txt);
        btn_login = findViewById(R.id.btn_login);
        if(getIntent().getExtras() != null)
            message = getIntent().getStringExtra("err").toString();
        err_txt.setText(message);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                sharedPreferences.save(username+":"+password, Login.this);
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}