package com.example.favoriteyoutube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.favoriteyoutube.handlers.FaveYTHandler
import com.example.favoriteyoutube.models.FaveYT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class FavoriteYoutubeChannelActivity : AppCompatActivity() {
    lateinit var favoriteChannel: ArrayList<FaveYT>
    lateinit var channelListView: ListView
    lateinit var faveYTHandler : FaveYTHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_youtube_channel)

        channelListView = findViewById(R.id.channelListView)
        favoriteChannel = ArrayList()

    }
    override fun onStart() {
        super.onStart()
        // register a listener
        faveYTHandler.faveYTRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                        it -> val faveYt = it.getValue(FaveYT::class.java)
                        favoriteChannel.add(faveYt!!)
                }
                val adapter = ArrayAdapter<FaveYT>(applicationContext, android.R.layout.simple_expandable_list_item_1,favoriteChannel)
                channelListView.adapter = adapter

            }

            override fun onCancelled(p0: DatabaseError) {
//                TODO("Not yet implemented")
            }

        })
    }


}