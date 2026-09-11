package com.thesystem.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesystem.core.domain.Character
import com.thesystem.core.domain.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AppViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    private val _character = MutableStateFlow(Character(id = "local"))
    val character: StateFlow<Character> = _character.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _character.value = characterRepository.load()
        }
    }
}
