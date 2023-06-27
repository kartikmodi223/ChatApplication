package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapplication.databinding.PasswordForgoteLayoutBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotePassword  :AppCompatActivity() {

    private lateinit var binding: PasswordForgoteLayoutBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= PasswordForgoteLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.passwordReset.setOnClickListener {

            var email = binding.passwordResetEmail.text.toString()


            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "enter valid email ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}