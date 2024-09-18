package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.newProducto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.core.utils.Utils
import es.fjmarlop.pizzettappfirebase.entidades.model.ProductModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.newProducto.domain.NewProductDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewProductoViewModel @Inject constructor(
    private val domain: NewProductDomain,
    private val utils: Utils
) : ViewModel() {


    private val _uiState = MutableStateFlow(NewProductoUiState())
    val uiState: StateFlow<NewProductoUiState> = _uiState


    fun resetUiState() {
        _uiState.value = NewProductoUiState()
    }

    fun setName(name: String) {
        _uiState.value = _uiState.value.copy(
            nombreProducto = name.lowercase()
                .replace(Regex("\\b[a-z]")) { it.value.uppercase() }
        )
    }

    fun setDescription(description: String) {
        _uiState.value = _uiState.value.copy(
            descriptionProduct = description.lowercase().replaceFirstChar { it.uppercase() })
    }

    fun setUrlImg(urlImg: String) {
        _uiState.value = _uiState.value.copy(urlImgProducto = urlImg)
    }

    fun createProduct(msg: String, navigate: () -> Unit) {

        val pr = ProductModel(
            idProduct = System.currentTimeMillis().toString(),
            nameProduct = _uiState.value.nombreProducto,
            descriptionProduct = _uiState.value.descriptionProduct,
            urlImgProduct = _uiState.value.urlImgProducto
        )

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { domain.createProduct(pr) }
            if (!result) { utils.msgToastShort(msg) } else { navigate() }
        }
    }

}