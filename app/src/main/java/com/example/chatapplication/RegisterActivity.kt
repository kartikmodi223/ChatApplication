package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapplication.databinding.RegisterLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: RegisterLayoutBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterLayoutBinding.inflate(layoutInflater)

        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        binding.loginLink.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.signupButton.setOnClickListener {

            val registeremail = binding.SignupEmail.text.toString()
            val registerpassword = binding.SignupPassword.text.toString()
            val registercustomepaaword = binding.SignupComfirmPassword.text.toString()
            val username = binding.username.text.toString()

            if (registeremail.isNotEmpty() && registerpassword.isNotEmpty() && registercustomepaaword.isNotEmpty() && username.isNotEmpty()) {
                if (registerpassword == registercustomepaaword) {
                    mAuth.createUserWithEmailAndPassword(registeremail, registerpassword)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                addUserToDatabase(username, registeremail, mAuth.currentUser!!.uid)
                                val intent = Intent(this, MainActivity::class.java)
                                finish()
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "creditial not valid", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                } else {
                    Toast.makeText(this, "creditial not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "creditial not empthy", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun addUserToDatabase(username: String, registeremail: String, uid: String?) {
        mdbRef = FirebaseDatabase.getInstance().getReference()
        if (uid != null) {
            mdbRef.child("User").child(uid).setValue(User(username,registeremail,uid))
        }
    }
}
