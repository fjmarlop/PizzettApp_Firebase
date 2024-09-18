package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.newProducto.domain

import es.fjmarlop.pizzettappfirebase.entidades.model.ProductModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.newProducto.data.NewProductService
import javax.inject.Inject

class NewProductDomain @Inject constructor(
    private val newProductService: NewProductService
) {


    suspend fun createProduct(product: ProductModel): Boolean {
       val pr = product.toResponse()
        return newProductService.createProduct(pr)
    }
}