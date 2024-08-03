package es.fjmarlop.pizzettappfirebase.core.repositories

import com.google.firebase.firestore.FirebaseFirestore
import es.fjmarlop.pizzettappfirebase.entidades.response.EmployeeResponse
import javax.inject.Inject

class EmployeeRepository @Inject constructor(
    firestore: FirebaseFirestore,
    employee: Class<EmployeeResponse>,
) : CrudRepository<EmployeeResponse>(firestore, employee) {


}
