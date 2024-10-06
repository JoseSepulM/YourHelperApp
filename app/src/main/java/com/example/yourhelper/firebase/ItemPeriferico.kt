package com.example.yourhelper.firebase

data class ItemPeriferico(
    var nombre: String = "",
    var activo: Boolean = false,
    var conexion: String = "",
    var type: String = "",
    var geo : String = ""
) {
    fun toggleActivo() {
        activo = !activo
    }

    fun updateGeo(param : String){
        geo = param
    }
}
