package com.example.demoapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoapp.core.State
import com.example.demoapp.data.apiresponses.Data
import com.example.demoapp.repository.DemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val demoRepository: DemoRepository
) : ViewModel() {

    var userList = MutableLiveData<ArrayList<Data>>()

    fun fetchDemo() {
        viewModelScope.launch {
            demoRepository.getDemo().catch {
                Log.e("TAG", "fetchDemo: $this")
            }.collect { response ->
                when (response) {
                    is State.Loading -> Log.d("TAG", "Loading")
                    is State.Success -> {
                        Log.d("TAG", "Success")
                        userList.value = response.data.data


                    }
                    is State.ResponseError -> Log.e("TAG", "Response Error")
                    is State.ExceptionError -> Log.e("TAG", "Local Exception")
                }
            }
        }
    }

}