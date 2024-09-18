package es.fjmarlop.pizzettappfirebase.vistasGenerales.splashScreen.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import es.fjmarlop.pizzettappfirebase.core.DatabaseValues
import es.fjmarlop.pizzettappfirebase.entidades.response.EmployeeResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SplashService @Inject constructor(private val firestore: FirebaseFirestore) {

    suspend fun findByEmailEmployee(emailEmployee: String): EmployeeResponse? {

        val querySnapshot = firestore.collection(DatabaseValues.Collections.EMPLOYEE)
            .whereEqualTo(DatabaseValues.Fields.EMAIL_EMPLOYEE, emailEmployee)
            .get()
            .await()

        for (document in querySnapshot.documents) {
            val employee = document.toObject<EmployeeResponse>()
            if (employee != null) {
                return employee
            }
        }
        return null
    }

}