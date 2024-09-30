package com.example.yourhelper.firebase

data class ItemPeriferico(
    var nombre: String = "",
    var activo: Boolean = false,
    var conexion: String = "",
    var type: String = ""
) {
    fun toggleActivo() {
        activo = !activo
    }
}
