package com.example.genesis.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genesis.domain.GetConfigsUseCase
import com.example.genesis.remote.BookDataModel
import com.example.genesis.remote.RCDataModel
import com.example.genesis.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LibraryViewModel @Inject constructor(
    val getConfigsUseCase: GetConfigsUseCase
) : ViewModel(){
    private val _screenState: MutableStateFlow<State<RCDataModel,Nothing>> =
        MutableStateFlow(State.Loading)
    val screenState: Flow<State<RCDataModel,Nothing>> = _screenState.filterNotNull()

    val list = mutableSetOf<String>()

    init {
        viewModelScope.launch {
            val config = getConfigsUseCase()
            _screenState.emit(
                State.Content(data = config)
            )
        }
    }

}
