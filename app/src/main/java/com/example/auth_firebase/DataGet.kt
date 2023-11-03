package com.example.auth_firebase
// get the data from the firebase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auth_firebase.databinding.ActivityDataGetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DataGet : AppCompatActivity() {
    private lateinit var binding: ActivityDataGetBinding
   private var db=Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataGetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userDataItems = arrayListOf<Userlist>()
        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(userDataItems)
        recyclerView.adapter = adapter
        db= FirebaseFirestore.getInstance()

    db.collection("user")
        .get()
        .addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                val txt1 = document.getString("name")
                val txt2 = document.getString("email")
                val txt3 = document.getString("phone")

                if (txt1 != null && txt2 != null&& txt3 != null) {
                    userDataItems.add(Userlist(txt1, txt2,txt3))
                }
            }
            adapter.notifyDataSetChanged()
        }
        .addOnFailureListener { e ->
            Toast.makeText(this, "Error: $e", Toast.LENGTH_SHORT).show()

        }


        setData()
        binding.delete.setOnClickListener{

            val delete= mapOf(
                "name" to FieldValue.delete(),
                "email" to FieldValue.delete(),
                "phone" to FieldValue.delete()
            )
            val userId=FirebaseAuth.getInstance().currentUser!!.uid
            db.collection("user").document(userId).update(delete).addOnSuccessListener {
                Toast.makeText(this@DataGet,"Success",Toast.LENGTH_LONG).show()

            }.addOnFailureListener {
                Toast.makeText(this@DataGet,"Failed to delete in firestore",Toast.LENGTH_LONG).show()
            }

        }

        binding.update.setOnClickListener {
            val name= binding.ename.text.toString()
            val email=binding.eemail.text.toString()
            val phone=binding.ephone.text.toString()
            val update= mapOf(
                "name" to name,
                "email" to email,
                "phone" to phone
            )
            val userId=FirebaseAuth.getInstance().currentUser!!.uid
            db.collection("user").document(userId).update(update).addOnSuccessListener {
                Toast.makeText(this@DataGet,"Success",Toast.LENGTH_LONG).show()
                binding.ename.text.clear()
                binding.eemail.text.clear()
                binding.ephone.text.clear()

            }.addOnFailureListener {
                Toast.makeText(this@DataGet,"Failed to save in firestore",Toast.LENGTH_LONG).show()
            }

        }
    }

fun setData() {
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    val ref = db.collection("user").document(userId)
    ref.get().addOnSuccessListener {
        if (it.exists()) {
            val name = it.data?.get("name").toString()
            val email = it.data?.get("email").toString()
            val phone = it.data?.get("phone").toString()

            binding.ename.setText(name)
            binding.eemail.setText(email)
            binding.ephone.setText(phone)

        } else {
            Toast.makeText(this@DataGet, "Failed ", Toast.LENGTH_SHORT).show()
        }
    }.addOnFailureListener {
        Toast.makeText(this@DataGet, "Failed to get data", Toast.LENGTH_SHORT).show()
    }
    }
}
