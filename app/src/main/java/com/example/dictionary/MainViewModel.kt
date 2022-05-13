package com.example.dictionary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dictionary.database.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    val wordsCounterLiveData = MutableLiveData<Int>()

    var allWordsLivedata: LiveData<List<Word?>?>?
    var findEngWordLiveData=MutableLiveData<Word>()
    var findPerWordLiveData=MutableLiveData<Word>()

    init {
        WordRepository.initDB(app)
        allWordsLivedata = WordRepository.getAllWord()
        //wordsCounterLiveData.value=getNumberOfWords().value

    }

    @JvmName("getAllWords1")
    fun getAllWords(): LiveData<List<Word?>?>? {
        return allWordsLivedata
    }

    fun addWord(word: Word) {
        viewModelScope.launch {
            WordRepository.addWord(word)
        }
        wordsCounterLiveData.value = wordsCounterLiveData.value?.plus(1)
    }

    fun deleteWord(wordID: Int) {
        viewModelScope.launch {
            WordRepository.deleteWord(wordID)
        }
        wordsCounterLiveData.value = wordsCounterLiveData.value?.minus(1)
    }

    fun updateWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            WordRepository.updateWord(word)
        }

    }

    fun findEngWordByName(name: String) {
       // var foundedWord = MutableLiveData<Word>()
        viewModelScope.launch {
            val newWord = WordRepository.findEngWordByName(name)
           // foundedWord.value = newWord  ---> this is false doesnt work
            //findEngWordLiveData.postValue(newWord) ---> this also works
            findEngWordLiveData.value=newWord
        }

    }

    fun findPersianWordByName(name: String) {
        viewModelScope.launch {
            findPerWordLiveData.value=WordRepository.findPersianWordByName(name)
        }

    }

    fun findWordByID(id: Int): LiveData<Word?> {
        var searchWord = MutableLiveData<Word?>()
        viewModelScope.launch {
            val newWord = WordRepository.findWordByID(id)
            searchWord.value = newWord
        }
        return searchWord
    }

     fun getNumberOfWords():LiveData<Int>{
        var wordCount = MutableLiveData<Int>()
        viewModelScope.launch {
            wordCount.value=WordRepository.getNumOfWords()
        }
        return wordCount
    }

}