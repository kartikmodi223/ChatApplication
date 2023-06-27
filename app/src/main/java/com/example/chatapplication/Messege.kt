package com.example.chatapplication

class Messege {
var messege: String? =null
    var senderid : String? = null

    constructor(){}
    constructor(messege: String? , senderid :String?){
        this.messege = messege
        this.senderid = senderid
    }
}