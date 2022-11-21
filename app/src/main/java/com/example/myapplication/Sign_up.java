package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Sign_up extends AppCompatActivity{
    private EditText edttaikhoan, edtmatkhau;
    private Button layout_signUp;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        anhxa();
        mAuth = FirebaseAuth.getInstance();
        layout_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp_user();
            }
        });

    }

    private void SignUp_user() {
        String email = edttaikhoan.getText().toString().trim();
        String pass = edtmatkhau.getText().toString().trim();

        if(email.isEmpty()){
            edttaikhoan.setError("Nhập Email");
            edttaikhoan.findFocus();
            return;
        }else if (pass.isEmpty()){
            edtmatkhau.setError("Nhập Password");
            edtmatkhau.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User( email,pass);
                            FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(Sign_up.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();

                                            }else {
                                                Toast.makeText(Sign_up.this,"Đăng ký không thành công, Đăng ký lại!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }else {
                            Toast.makeText(Sign_up.this,"Đăng ký không thành công, Đăng ký lại!",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    //anh xa
    private void anhxa(){
        edttaikhoan=  findViewById(R.id.edittexttaikhoan);
        edtmatkhau=  findViewById(R.id.edittextmatkhau);
        layout_signUp=  findViewById(R.id.buttonCreat);
    }
}