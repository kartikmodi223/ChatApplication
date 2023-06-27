package com.example.chatapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@Suppress("DEPRECATION")
class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var MessegeAdapter:MessegeAdapter
    private lateinit var messegeList:ArrayList<Messege>
    private lateinit var mdbRef: DatabaseReference

    private var Room_Recevier : String? = null
    private var Room_sender : String? = null
    private val pickImage = 100
    private var imageUri: Uri? = null

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mdbRef =FirebaseDatabase.getInstance().reference

        val img = findViewById<ImageView>(R.id.img)
        img.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        val name = intent.getStringExtra("name")
        val recever_id = intent.getStringExtra("Uid")

        val sender_id = FirebaseAuth.getInstance().currentUser!!.uid
        Room_sender = recever_id + sender_id
        Room_Recevier =   sender_id + recever_id

        supportActionBar!!.title = name

        mdbRef.child("Chats").child(Room_sender!!).child("messege").addValueEventListener(object :ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                messegeList.clear()
                for (postsnapshot in snapshot.children){
                    val messege = postsnapshot.getValue(Messege::class.java)
                    if (messege != null) {
                        messegeList.add(messege)
                    }
                    MessegeAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAG", "onCancelled: $error")
            }
        })

        findViewById<TextView>(R.id.sendmessege)
        findViewById<TextView>(R.id.receviemesssege)
        val send_button = findViewById<ImageView>(R.id.send)
        val messegeBox = findViewById<EditText>(R.id.write_messege)
        chatRecyclerView = findViewById(R.id.mainarea)



        messegeList = ArrayList()
        MessegeAdapter = MessegeAdapter(this,messegeList)

        chatRecyclerView.layoutManager =LinearLayoutManager(this)
        chatRecyclerView.adapter = MessegeAdapter

        send_button.setOnClickListener {
            val messege = messegeBox.text.toString()
            val messegeObject = Messege(messege, sender_id)


            mdbRef.child("Chats").child(Room_sender!!).child("messege").push()
                .setValue(messegeObject).addOnCompleteListener {
                    mdbRef.child("Chats").child(Room_Recevier!!).child("messege").push()
                        .setValue(messegeObject)
                }
            messegeBox.setText("")
        }
    }
}


