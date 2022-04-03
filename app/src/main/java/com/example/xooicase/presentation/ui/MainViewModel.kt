package com.example.xooicase.presentation.ui

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xooicase.common.Resource
import com.example.xooicase.domain.model.DetailsObservableModel
import com.example.xooicase.domain.use_case.TestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val testUseCase: TestUseCase) : ViewModel() {

    val detailsLiveData = MutableLiveData<DetailsObservableModel>()
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<String>()

    fun getDetails() {
        viewModelScope.launch {
            testUseCase.getDetails().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        loadingState.value = true  // servis sürecinde kullanılacak, şuan test data ile çalisiyoruz.
                    }
                    is Resource.Success -> {
                        loadingState.value = false
                        detailsLiveData.value = result.data!!

                    }
                    is Resource.Error -> {
                        loadingState.value = false //
                        errorState.value = result.message?.toString()
                    }
                }
            }
        }
    }


}