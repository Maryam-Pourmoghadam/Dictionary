package com.example.dictionary.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(@PrimaryKey(autoGenerate = true)val id:Int,
                var word:String, var meaning:String,var synonyms:String,
                var example:String,var wikipediaLink:String=""
                ,var isFavorite:Boolean=false
)
{
    init {
        wikipediaLink="https://en.wikipedia.org/wiki/$word"

    }
}


