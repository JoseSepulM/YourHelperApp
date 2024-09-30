package com.example.yourhelper.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

object PerifericoRepository {
    private const val COLLECTION_NAME = "perifericos"
    private val firestore = FirebaseFirestore.getInstance()

    // Crear o añadir un nuevo ítem periférico
    suspend fun addPeriferico(item: ItemPeriferico): Boolean {
        return try {
            firestore.collection(COLLECTION_NAME)
                .add(item)
                .await() // Espera hasta que la operación se complete
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Obtener todos los ítems periféricos
    suspend fun getAllPerifericos(): List<ItemPeriferico>? {
        return try {
            val snapshot = firestore.collection(COLLECTION_NAME)
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject<ItemPeriferico>() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Obtener un periférico por su ID
    suspend fun getPerifericoById(id: String): ItemPeriferico? {
        return try {
            val document = firestore.collection(COLLECTION_NAME)
                .document(id)
                .get()
                .await()
            document.toObject<ItemPeriferico>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Actualizar un periférico existente
    suspend fun updatePeriferico(id: String, item: ItemPeriferico): Boolean {
        return try {
            firestore.collection(COLLECTION_NAME)
                .document(id)
                .set(item)
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Eliminar un periférico por su ID
    suspend fun deletePeriferico(id: String): Boolean {
        return try {
            firestore.collection(COLLECTION_NAME)
                .document(id)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
