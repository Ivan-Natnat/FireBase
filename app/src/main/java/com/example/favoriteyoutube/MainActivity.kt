package com.example.favoriteyoutube

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.favoriteyoutube.handlers.FaveYTHandler
import com.example.favoriteyoutube.models.FaveYT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var channelNameEd: EditText
    lateinit var channelLinkEd: EditText
    lateinit var rankEd : EditText
    lateinit var reasonEd: EditText
    lateinit var submitButton : Button
    lateinit var gotoRankButton : Button
    lateinit var faveYTHandler: FaveYTHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        channelNameEd = findViewById(R.id.channelName)
        channelLinkEd = findViewById(R.id.channelLink)
        rankEd = findViewById(R.id.rank)
        reasonEd = findViewById(R.id.reason)
        faveYTHandler = FaveYTHandler()


        submitButton = findViewById(R.id.submitBtn)

        submitButton.setOnClickListener{
            val channelName = channelNameEd.text.toString()
            val channelLink = channelLinkEd.text.toString()
            val rank = rankEd.text.toString()
            val reason = reasonEd.text.toString()

            val faveYT =  FaveYT(channelName = channelName, channelLink = channelLink,rank = rank,reason = reason)
            if (faveYTHandler.create(faveYT)){
                Toast.makeText(applicationContext, "Favorite Youtube Channel added", Toast.LENGTH_LONG).show()
            }

        }
        gotoRankButton.setOnClickListener {
            val intent = Intent(this,FavoriteYoutubeChannelActivity::class.java)
            startActivity(intent)
        }
    }
}