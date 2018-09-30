package com.vivek.captston;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    EditText editTextEmail,editTextPassword;
    ProgressBar progressBar;
    Button login,signUp;
    String Email;
    String password;
    TextView forgetpassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail=(EditText)findViewById(R.id.username);
        editTextPassword=(EditText)findViewById(R.id.password);
        // progressBar=findViewById(R.id.progressBar);
        findViewById(R.id.buttonsignup).setOnClickListener(this);
        findViewById(R.id.buttonlogin).setOnClickListener(this);
        findViewById(R.id.forget_password_textView).setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
        }

    private void userLogin(){
        Email=editTextEmail.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        if(!validation()) {
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //      progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    //  Log.d(TAG, "signInWithEmail:success");
                    Toast.makeText(getApplication(),"Successful authentication",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    //   Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(getApplication(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

private void sendEmailVerification(){

}

    private boolean validation(){
        boolean valid =true;

        if(Email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            valid =false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextEmail.setError("Please Enter valid Email address");
            editTextEmail.requestFocus();
            valid =false;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Please Enter the password");
            editTextPassword.requestFocus();
            valid=false;
        }
        if(password.length()<6){
            editTextPassword.setError("Minimum password length is 6");
            editTextPassword.requestFocus();
            valid=false;
        }

        return valid; }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonlogin:
                userLogin();
                // Toast.makeText(getApplication(),"User successfully login",Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonsignup:
                Intent intent=new Intent(this,SignUp.class);
                startActivity(intent);
                break;
            case R.id.forget_password_textView:
                Intent intent1 =new Intent(this,ForgotPasswordModule.class);
                startActivity(intent1);
                break;
                default:
                break;

        }




        //int id=v.getId();
        ///if(id==R.id.buttonlogin){

        //}

    }

}
