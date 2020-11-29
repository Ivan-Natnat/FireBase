package com.example.favoriteyoutube.handlers

import com.example.favoriteyoutube.models.FaveYT
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FaveYTHandler {
    var database: FirebaseDatabase
    var faveYTRef: DatabaseReference

    init {
        database = FirebaseDatabase.getInstance()
        faveYTRef = database.getReference("favoriteYTs")

    }
    fun create(faveYT: FaveYT):Boolean{
        val id = faveYTRef.push().key
        faveYT.id = id
        faveYTRef.child(id!!).setValue(faveYT)
        return true
    }
    fun update (faveYT: FaveYT): Boolean{
        faveYTRef.child(faveYT.id!!).setValue(faveYT)
        return true
    }
    fun delete(faveYT: FaveYT): Boolean{
        faveYTRef.child(faveYT.id!!).removeValue()
        return true
    }

}