package com.sd.appnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sd.appnews.ui.ALLNewsActivity;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText    editTextEmail;
    EditText editTextPassword;

    String email;
    String password;
    String TAG_AUTH_SUCC   = "Success Auth";
    String TAG_AUTH_FAIL   = "Fail Auth";
    String TAG_REGIST_SUCC = "Success Regist";
    String TAG_REGIST_FAIL = "Fail Regist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);

        setParameterUser();
    }
    //_________________________________________________________________________

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);

    }
    //_________________________________________________________________________

    public void OnClickAuth(View v)
    {
        getParameterUser();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG_AUTH_SUCC, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG_AUTH_FAIL, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }
    //_________________________________________________________________________

    private void updateUI(FirebaseUser user)
    {
        Intent intent_rss = new Intent(MainActivity.this, ALLNewsActivity.class);
        startActivity(intent_rss);
    }
    //_________________________________________________________________________

    public void OnClickRegister(View v)
    {
        getParameterUser();

        /******************************************************************/
        if (
            (email != null && !email.isEmpty())
            &&
            (password != null && !password.isEmpty())
        ) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG_REGIST_SUCC, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG_REGIST_FAIL, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }
                        }
                    });
        }
        else {
            Log.w(TAG_REGIST_FAIL, "createUserWithEmail:failure"); //, task.getException());
            Toast.makeText(MainActivity.this, "Authentication parameters is empty. ",
                    Toast.LENGTH_SHORT).show();

        }
    }
    //_________________________________________________________________________
    void setParameterUser()
    {
        email       = "man_04@mksat.net";
        editTextEmail.setText(email);

        password    =  "man_04_dev";
        editTextPassword.setText(password);
    }
    //_________________________________________________________________________

    void getParameterUser()
    {
        email       = editTextEmail.getText().toString();

        password    = editTextPassword.getText().toString();
    }
    //_________________________________________________________________________
}