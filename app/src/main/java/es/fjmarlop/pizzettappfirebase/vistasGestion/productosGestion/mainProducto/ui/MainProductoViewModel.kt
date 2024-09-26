package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.mainProducto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.mainProducto.domain.MainProductoDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainProductoViewModel @Inject constructor(private val domain: MainProductoDomain) :
    ViewModel() {

        private val _uiState = MutableStateFlow(MainProductoUiState())
        val uiState: StateFlow<MainProductoUiState> = _uiState

    fun getAllProducts() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { domain.getAllProducts() }
            result.collect { products -> _uiState.value = _uiState.value.copy(products = products) }
        }
    }

    fun resetSearch() {
        _uiState.value = _uiState.value.copy(searchText = "")
    }

    fun setTextSearch(search: String) {
        _uiState.value = _uiState.value.copy(searchText = search)
    }
}