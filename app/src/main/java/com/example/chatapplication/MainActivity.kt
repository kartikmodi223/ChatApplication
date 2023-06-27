package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth =FirebaseAuth.getInstance()
//        printHashKey(this)
        //Click on sign in button

        binding.forgotePasswordLink.setOnClickListener {
            var intent =Intent(this,ForgotePassword::class.java)
            startActivity(intent)
        }

        binding.LoginButton.setOnClickListener {
            var email = binding.Email.text.toString()
            var password = binding.Password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty())
            {
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if( it.isSuccessful)
                    {
                        var intent =Intent(this,DashboardActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Creditial not match", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this, "Creditial should not empthy", Toast.LENGTH_SHORT).show()
            }


        }

        binding.signupLink.setOnClickListener {
            val intent =Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }


}