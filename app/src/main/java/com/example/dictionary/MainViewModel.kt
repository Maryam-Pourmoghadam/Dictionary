package com.example.dictionary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dictionary.database.Word

class MainViewModel(app: Application) : AndroidViewModel(app) {
    val wordsCounterLiveData = MutableLiveData(0)
    var allWordsLivedata: LiveData<List<Word?>?>?

    init {
        WordRepository.initDB(app)
        wordsCounterLiveData.value = getNumberOfWords()
        allWordsLivedata = WordRepository.getAllWord()
    }

    @JvmName("getAllWords1")
    fun getAllWords(): LiveData<List<Word?>?>? {
        return allWordsLivedata
    }

    fun addWord(word: Word) {
        WordRepository.addWord(word)
        wordsCounterLiveData.value = wordsCounterLiveData.value?.plus(1)
    }

    fun deleteWord(wordID: Int) {
        WordRepository.deleteWord(wordID)
        wordsCounterLiveData.value = wordsCounterLiveData.value?.minus(1)
    }

    fun updateWord(word: Word) {
        WordRepository.updateWord(word)
    }

    fun findEngWordByName(name: String): Word {
        return WordRepository.findEngWordByName(name)
    }

    fun findPersianWordByName(name: String): Word {
        return WordRepository.findPersianWordByName(name)
    }

    fun findWordByID(id: Int): Word {
        return WordRepository.findWordByID(id)
    }

    fun getNumberOfWords(): Int? {
        return WordRepository.getNumOfWords()
    }

}