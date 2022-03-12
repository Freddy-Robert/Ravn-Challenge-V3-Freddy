package com.app.ravn_challenge.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.app.ravn_challenge.AllPeopleQuery
import com.app.ravn_challenge.PeopleQuery
import com.app.ravn_challenge.repository.PeopleRepository
import com.app.ravn_challenge.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.file.Files.find
import javax.inject.Inject

/**
 * The ViewModel for [PeopleListFragment] and [PersonDetailFragment]
 */

@HiltViewModel
class PeopleViewModel @Inject constructor(private val repository: PeopleRepository): ViewModel() {

    private val _peopleList by lazy { MutableLiveData<ViewState<ApolloResponse<AllPeopleQuery.Data>>>() }

    val peopleList: LiveData<ViewState<ApolloResponse<AllPeopleQuery.Data>>>
        get() = _peopleList

    private val _person by lazy { MutableLiveData<ViewState<ApolloResponse<PeopleQuery.Data>>>() }

    val person: LiveData<ViewState<ApolloResponse<PeopleQuery.Data>>>
        get() = _person

    fun getPeopleList() = viewModelScope.launch {
        _peopleList.postValue(ViewState.Loading())
        try {
            val response = repository.getAllPeople()
            delay(3000)
            _peopleList.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _peopleList.postValue(ViewState.Error("Error fetching characters"))
        }
    }

    fun getPersonById(id: String) = viewModelScope.launch {
        _person.postValue(ViewState.Loading())
        try {
            val response = repository.getPerson(id)
            _person.postValue(ViewState.Success(response))
        } catch (ae: ApolloException) {
            Log.d("ApolloException", "Failure", ae)
            _person.postValue(ViewState.Error("Error fetching characters"))
        }
    }

    fun searchByName(name: String?) {

    }

}
