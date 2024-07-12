package es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.data

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import es.fjmarlop.pizzettappfirebase.R
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthService @Inject constructor(
    private val fireBaseAuth: FirebaseAuth,
    private val context: Context
) {

    suspend fun createAccount(email: String, password: String): FirebaseUser? {
        return suspendCancellableCoroutine { continuation ->
            fireBaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    continuation.resume(it.user)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    fun deleteAccount(): Boolean {
        return fireBaseAuth.currentUser?.delete()?.isSuccessful ?: false
    }


    suspend fun initSession(email: String, password: String): FirebaseUser? {
        return fireBaseAuth.signInWithEmailAndPassword(email, password).await().user
    }

    suspend fun recoveryPassword(email: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            fireBaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    fun checkLoggedUser(): Boolean {
        return getCurrentUser() != null
    }

    fun getCurrentUser() = fireBaseAuth.currentUser

    fun logOut() {
        fireBaseAuth.signOut()
    }

    fun getGoogleClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)

    }

    private suspend fun completeRegisterWithCredential(credential: AuthCredential): FirebaseUser? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            fireBaseAuth.signInWithCredential(credential)
                .addOnSuccessListener { cancellableContinuation.resume(it.user) }
                .addOnFailureListener { cancellableContinuation.resumeWithException(it) }
        }
    }

    suspend fun loginWithGoogle(idToken: String): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return completeRegisterWithCredential(credential)
    }

    suspend fun loginWithTwitter(activity: Activity): FirebaseUser? {
        val provider = OAuthProvider.newBuilder("twitter.com").build()
        return initRegisterWithProvider(activity, provider)
    }

    suspend fun loginAnonymously(): FirebaseUser? {
        return fireBaseAuth.signInAnonymously().await().user
    }

    private suspend fun initRegisterWithProvider(
        activity: Activity, provider: OAuthProvider
    ): FirebaseUser? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            fireBaseAuth.pendingAuthResult?.addOnSuccessListener {
                cancellableContinuation.resume(it.user)
            }?.addOnFailureListener { e ->
                cancellableContinuation.resumeWithException(e)
            } ?: completeRegisterWithProvider(activity, provider, cancellableContinuation)
        }
    }

    private fun completeRegisterWithProvider(
        activity: Activity,
        provider: OAuthProvider,
        cancellableContinuation: CancellableContinuation<FirebaseUser?>
    ) {
        fireBaseAuth.startActivityForSignInWithProvider(activity, provider).addOnSuccessListener {
            cancellableContinuation.resume(it.user)
        }.addOnFailureListener { cancellableContinuation.resumeWithException(it) }
    }

}



