package com.example.dictionary

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.dictionary.database.AppDatabase
import com.example.dictionary.database.Word
import com.example.dictionary.database.WordDao

object WordRepository {
    lateinit var wordDao: WordDao
    lateinit var allWords: LiveData<List<Word>>
    fun initDB(context: Context) {
        val db = AppDatabase.getAppDataBase(context)
        wordDao = db!!.wordDao()
        allWords = wordDao.getAllWords()
    }

    @JvmName("getAllWords1")
    fun getAllWords():LiveData<List<Word>>{
        return allWords
    }

    fun addWord(word:Word){
        wordDao.insertWord(word)
    }

    fun deleteWord(wordName: String){
        wordDao.deleteWord(wordName)
    }

    fun findEngWordByName(name: String):Word{
        return wordDao.findEnglishWord(name)
    }

    fun findPersianWordByName(name: String):Word{
        return wordDao.findPersianWord(name)
    }




}