package com.quanticheart.core.base.data.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.quanticheart.core.base.data.user.mapper.NewUserFirebasePayloadMapper
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.models.user.NewUser
import com.quanticheart.core.base.domain.models.user.User
import com.quanticheart.core.base.domain.models.user.UserLogin
import kotlinx.coroutines.tasks.await

class UserRemoteFirebaseDataSourceImpl(
    private val mAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : UserRemoteDataSource {

    override suspend fun getUserLogged(): RequestState<User> {
        mAuth.currentUser?.reload()
        val firebaseUser = mAuth.currentUser
        return if (firebaseUser == null) {
            RequestState.Error(Exception("Usuário não logado"))
        } else {
            val user = firebaseFirestore.collection("users")
                .document(firebaseUser.uid).get().await().toObject(User::class.java)
            if (user == null) {
                RequestState.Error(Exception("Usuário não encontrado"))
            } else {
                user.id = firebaseUser.uid
                RequestState.Success(user)
            }
        }
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return try {
            mAuth.signInWithEmailAndPassword(userLogin.email, userLogin.password).await()
            val firebaseUser = mAuth.currentUser
            if (firebaseUser == null) {
                RequestState.Error(Exception("Usuário ou senha inválido"))
            } else {
                RequestState.Success(User(firebaseUser.displayName ?: ""))
            }
        } catch (e: Exception) {
            RequestState.Error(Exception(e))
        }
    }

    override suspend fun create(newUser: NewUser): RequestState<User> {
        return try {
            mAuth.createUserWithEmailAndPassword(newUser.email, newUser.password).await()
            val userId = mAuth.currentUser?.uid
            if (userId == null) {
                RequestState.Error(Exception("Não foi possível criar a conta"))
            } else {
                val newUserFirebasePayload =
                    NewUserFirebasePayloadMapper.mapToNewUserFirebasePayload(newUser)

                firebaseFirestore
                    .collection("users")
                    .document(userId)
                    .set(newUserFirebasePayload)
                    .await()

                RequestState.Success(NewUserFirebasePayloadMapper.mapToUser(newUserFirebasePayload))
            }
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    fun resetPassword(email: String) {
        if (email.isNotEmpty()) {
            mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        RequestState.Success("Verifique sua caixa de e-mail")

                    } else {
                        RequestState.Error(
                            Throwable(
                                task.exception?.message ?: "Não foi possível realizar a requisição"
                            )
                        )

                    }
                }
        } else {
            RequestState.Error(Throwable("Não foi possível resetar a senha"))
        }
    }
}