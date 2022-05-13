package com.example.dictionary.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao{

@Query("SELECT * FROM Word ORDER BY word ASC")
fun getAllWords():LiveData<List<Word?>?>?

@Query("SELECT COUNT(*)FROM Word")
fun wordCount():LiveData<Int>

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertWord(word: Word)

@Query("DELETE FROM Word WHERE id=:wordID")
suspend fun deleteWord(wordID:Int)

@Update
suspend fun updateWord(word: Word)

@Query("DELETE FROM Word")
suspend fun deleteTable()

@Query("SELECT * FROM Word WHERE word IN (:engWord)" )
suspend fun findEnglishWord(engWord:String):Word

@Query("SELECT * FROM Word WHERE meaning IN(:persianWord)")
suspend fun findPersianWord(persianWord:String):Word

@Query("SELECT * FROM Word WHERE id IN (:wordID)")
suspend fun findByID(wordID:Int):Word

@Query("SELECT COUNT(*) FROM Word")
suspend fun getNumberOfWords():Int?
}