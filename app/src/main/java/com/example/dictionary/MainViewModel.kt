package com.example.dictionary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dictionary.database.Word

class MainViewModel (app: Application) : AndroidViewModel(app){
    val wordsCounterLiveData=MutableLiveData(0)

    init {
            WordRepository.initDB(app)
    }

    fun getAllWords(): LiveData<List<Word>> {
        return WordRepository.getAllWords()
    }

    fun addWord(word: Word){
        WordRepository.addWord(word)
        wordsCounterLiveData.value=wordsCounterLiveData.value?.plus(1)
    }

    fun deleteWord(wordName: String){
        WordRepository.deleteWord(wordName)
        wordsCounterLiveData.value=wordsCounterLiveData.value?.minus(1)
    }

    fun findEngWordByName(name: String): Word {
        return WordRepository.findEngWordByName(name)
    }

    fun findPersianWordByName(name: String): Word {
        return WordRepository.findPersianWordByName(name)
    }

}