package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

@Suppress("UNREACHABLE_CODE")
class MessegeAdapter(val context: Context, val messegeList:ArrayList<Messege>) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val item_receive = 1
    val item_send = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType==1){
            val view = LayoutInflater.from(context).inflate(R.layout.send_layout,parent,false)
            return SentViewHolder(view)
        }
        else{
            val view = LayoutInflater.from(context).inflate(R.layout.recevier_layout,parent,false)
            return ReceiveViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.javaClass == SentViewHolder::class.java)
        {
            val currentMessege = messegeList[position]
            val viewHolder = holder as SentViewHolder
            holder.send.text = currentMessege.messege

        }
        else
        {
            val viewHolder = holder as ReceiveViewHolder
            val currentMessege = messegeList[position]
            holder.receive.text = currentMessege.messege

        }
    }
    override fun getItemViewType(position: Int): Int {
        val currentMessege = messegeList[position]
        if (FirebaseAuth.getInstance().currentUser!!.uid.equals((currentMessege.senderid))){
            return item_send
        }
        else{
            return item_receive
        }
    }
    override fun getItemCount(): Int {
        return messegeList.size
    }

    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val send = itemView.findViewById<TextView>(R.id.sendmessege)

    }
    class ReceiveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val receive = itemView.findViewById<TextView>(R.id.receviemesssege)
    }
}