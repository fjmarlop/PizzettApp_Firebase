package es.fjmarlop.pizzettappfirebase.vistasGestion.categoriasGestion.mainCategorias.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.categoriasGestion.mainCategorias.domain.MainCategoriasDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainCategoriaViewmodel @Inject constructor(private val domain: MainCategoriasDomain) :
    ViewModel() {

    private val _uiState = MutableStateFlow(MainCategoriasUiState())
    val uiState: Flow<MainCategoriasUiState> = _uiState.onStart { getAllCategories() }


    private fun getAllCategories() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { domain.getAllCategories() }
            result.collect { categories ->
                _uiState.value = _uiState.value.copy(categorias = categories)
            }
        }
    }

}

