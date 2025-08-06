package uk.co.weightwars.network.model

sealed class FirebaseAction<T> {
    data class Added<T>(val data: T) : FirebaseAction<T>()
    data class Modified<T>(val data: T) : FirebaseAction<T>()
    data class Removed<T>(val data: T) : FirebaseAction<T>()
}