package com.example.auth_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.auth_firebase.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor= ContextCompat.getColor(this,R.color.lightgrey)
        auth = Firebase.auth

        binding.SignUpBotton.setOnClickListener {
            val email = binding.EditEmail.text.toString()
            val pwd = binding.EditPwd.text.toString()
            val confirm = binding.EditCnf.text.toString()
            if ((email.isEmpty()) || (pwd.isEmpty()) || (confirm.isEmpty())) {
                Toast.makeText(this@SignUp, "Enter email and password", Toast.LENGTH_LONG).show()
            } else if (pwd != confirm) {
                Toast.makeText(this@SignUp, " NOt match password", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this@SignUp, "Create account succesfull", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(this@SignUp, "Login failed", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        binding.signin.setOnClickListener{
            startActivity(Intent(this@SignUp,MainActivity::class.java))
            finish()
        }


    }
}