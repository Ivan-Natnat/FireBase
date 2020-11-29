package com.example.favoriteyoutube

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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
    lateinit var favoriteChannel: ArrayList<FaveYT>
    lateinit var channelListView : ListView
    lateinit var faveYTEd : FaveYT



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        channelNameEd = findViewById(R.id.channelName)
        channelLinkEd = findViewById(R.id.channelLink)
        rankEd = findViewById(R.id.rank)
        reasonEd = findViewById(R.id.reason)

        faveYTHandler = FaveYTHandler()
        favoriteChannel = ArrayList()
        favoriteChannel.iterator()

        channelListView = findViewById(R.id.ytListView)
        gotoRankButton = findViewById(R.id.gotoRankBtn)
        submitButton = findViewById(R.id.submitBtn)

        submitButton.setOnClickListener{
            val channelName = channelNameEd.text.toString()
            val channelLink = channelLinkEd.text.toString()
            val rank = rankEd.text.toString()
            val reason = reasonEd.text.toString()

            if (submitButton.text.toString() =="Add"){
                val faveYT =  FaveYT(
                    channelName = channelName,
                    channelLink = channelLink,
                    rank = rank,
                    reason = reason
                )
                if (faveYTHandler.create(faveYT)){
                    Toast.makeText(
                        applicationContext,
                        "Favorite Youtube Channel added",
                        Toast.LENGTH_LONG
                    ).show()
                 }

            }
            else if(submitButton.text.toString() == "Update"){
                val faveYT= FaveYT(
                    id = faveYTEd.id,
                    channelName = channelName,
                    channelLink = channelLink,
                    rank = rank,
                    reason = reason
                )
                if (faveYTHandler.update(faveYT)){
                    Toast.makeText(
                        applicationContext,
                        "Favorite Youtube Channel up to date",
                        Toast.LENGTH_LONG
                    ).show()
                    clearfield()
                }
            }

        }
        registerForContextMenu(channelListView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when(item.itemId){
            R.id.editYTchannel -> {
                faveYTEd = favoriteChannel[info.position]
                channelNameEd.setText(faveYTEd.channelName)
                channelLinkEd.setText(faveYTEd.channelLink)
                rankEd.setText(faveYTEd.rank)
                reasonEd.setText(faveYTEd.reason)
                submitButton.setText("Update")
                true
            }
            R.id.deleteYTchannel -> {
                if (faveYTHandler.delete(favoriteChannel[info.position])) {
                    Toast.makeText(
                        applicationContext,
                        "Favorite Youtube Channel deleted",
                        Toast.LENGTH_LONG
                    ).show()
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    override fun onStart() {
        super.onStart()
        // register a listener
        faveYTHandler.faveYTRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                favoriteChannel.clear()
                snapshot.children.forEach { it ->
                    val faveYt = it.getValue(FaveYT::class.java)
                    favoriteChannel.add(faveYt!!)
                }
                val adapter = ArrayAdapter<FaveYT>(
                    applicationContext,
                    android.R.layout.simple_list_item_1,
                    favoriteChannel
                )
                channelListView.adapter = adapter

            }

            override fun onCancelled(p0: DatabaseError) {
//                TODO("Not yet implemented")
            }

        })
    }
    fun clearfield(){
        channelNameEd.text.clear()
        channelLinkEd.text.clear()
        rankEd.text.clear()
        reasonEd.text.clear()
        submitButton.setText("Add")
    }


}