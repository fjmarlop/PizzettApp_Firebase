package es.fjmarlop.pizzettappfirebase.core.repositories

import com.google.firebase.firestore.FirebaseFirestore
import es.fjmarlop.pizzettappfirebase.core.DatabaseValues
import es.fjmarlop.pizzettappfirebase.core.repositories.mainRepository.CrudRepository
import es.fjmarlop.pizzettappfirebase.entidades.response.ProductResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(
    firestore: FirebaseFirestore,
    product: Class<ProductResponse>
): CrudRepository<ProductResponse>(firestore, product) {

    suspend fun createProduct(product: ProductResponse): Boolean {
        return try {
            val newProduct = hashMapOf(
                DatabaseValues.Fields.ID_PRODUCT to product.idProducto,
                DatabaseValues.Fields.NAME_PRODUCT to product.nombreProducto,
                DatabaseValues.Fields.DESCRIPTION_PRODUCT to product.descripcionProducto,
                DatabaseValues.Fields.IMG_PRODUCT to product.urlImgProducto,
                DatabaseValues.Fields.CATEGORY_PRODUCT to product.categoria
            )
            insert(DatabaseValues.Collections.PRODUCTS, newProduct)
        } catch (e: Exception) {
            false
        }
    }
}