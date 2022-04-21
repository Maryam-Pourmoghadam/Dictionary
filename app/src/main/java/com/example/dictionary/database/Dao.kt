package com.example.dictionary.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao{

@Query("SELECT * FROM Word ORDER BY word ASC")
fun getAllWords():LiveData<List<Word>>

@Query("SELECT COUNT(*)FROM Word")
fun wordCount():LiveData<Int>

@Insert(onConflict = OnConflictStrategy.REPLACE)
fun insertWord(word: Word)

@Query("DELETE FROM Word WHERE word=:wordName")
fun deleteWord(wordName:String)

@Update
fun updateWord(word: Word)

@Query("DELETE FROM Word")
fun deleteTable()

}