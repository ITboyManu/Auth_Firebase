package com.example.auth_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.auth_firebase.databinding.ActivityNewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor= ContextCompat.getColor(this,R.color.lightgrey)
        var db=Firebase.firestore

        binding.Adddata.setOnClickListener {
           val name= binding.editTextText.text.toString()
            val email=binding.editTextText2.text.toString()
            val phone=binding.editTextText3.text.toString()
            val usermap= hashMapOf(
                "name" to name,
                "email" to email,
                "phone" to phone
            )
            val userId=FirebaseAuth.getInstance().currentUser!!.uid
            db.collection("user").document(userId).set(usermap).addOnSuccessListener {
                Toast.makeText(this@NewActivity,"Success",Toast.LENGTH_LONG).show()
                binding.editTextText.text.clear()
                binding.editTextText2.text.clear()
                binding.editTextText3.text.clear()
            }.addOnFailureListener {
                Toast.makeText(this@NewActivity,"Failed to save in firestore",Toast.LENGTH_LONG).show()
            }

        }

        binding.signout.setOnClickListener {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
                    getString(
                        com.firebase.ui.auth.R.string.default_web_client_id
                    )
                )
                    .requestEmail().build()

            GoogleSignIn.getClient(this, gso).signOut()
            startActivity(Intent(this@NewActivity, MainActivity::class.java))

        }
        binding.getdata.setOnClickListener {
            startActivity(Intent(this@NewActivity,DataGet::class.java))
        }
    }
}