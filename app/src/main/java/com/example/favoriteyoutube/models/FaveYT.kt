package com.example.favoriteyoutube.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class FaveYT (var id:String? ="", var channelName:String? ="",var channelLink:String? ="",var rank:String? = "",var reason:String? =""){

    override fun toString(): String {
        return "Rank: $rank - Name: $channelName Reason of the Rank: $reason Youtuble Link : $channelLink"
    }

}