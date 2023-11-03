package com.example.auth_firebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.auth_firebase.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor=ContextCompat.getColor(this,R.color.lightgrey)
    // gso (google signing option make gso for google dialogue)
        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(
            com.firebase.ui.auth.R.string.default_web_client_id))
                        .requestEmail().build()
        googleSignInClient=GoogleSignIn.getClient(this,gso)

        auth= Firebase.auth
        binding.SigninButton.setOnClickListener {
            signIn()
        }

        binding.Googlesignin.setOnClickListener {
            val signInClient=googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }
        binding.signup.setOnClickListener{
            val intent=Intent(this@MainActivity,SignUp::class.java)
            startActivity(intent)
            finish()

        }
    }
    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        it->
        if (it.resultCode==Activity.RESULT_OK)
        {

            val task=GoogleSignIn.getSignedInAccountFromIntent(it.data)
            if (task.isSuccessful)
            {
                val account:GoogleSignInAccount?=task.result
                val credential=GoogleAuthProvider.getCredential(account?.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        Toast.makeText(this@MainActivity,"done",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@MainActivity,NewActivity::class.java))
                    }else{
                        Toast.makeText(this@MainActivity,"failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        else
        {
            Toast.makeText(this@MainActivity,"Failed",Toast.LENGTH_LONG).show()
        }
    }

    private fun signIn() {
        val email1=binding.emailEdit.text.toString()
        val pwd1=binding.editpwd.text.toString()
        if (email1.isEmpty()||pwd1.isEmpty())
        {
            Toast.makeText(this@MainActivity,"Enter email and password", Toast.LENGTH_LONG).show()
        }
        else
        {
            auth.signInWithEmailAndPassword(email1,pwd1).addOnCompleteListener {
                if (it.isSuccessful) {
                    // Sign-in successful
                    Toast.makeText(this@MainActivity, "done", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@MainActivity, NewActivity::class.java))
                } else {
                    // Sign-in failed
                    Toast.makeText(this@MainActivity, "Google Sign-In failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
