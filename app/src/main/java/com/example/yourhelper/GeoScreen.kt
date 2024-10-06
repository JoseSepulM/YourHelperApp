import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch

@Composable
fun GeoScreen(activity: Activity) {
    val context = LocalContext.current
    var coordinates by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    var isModalOpen by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            if (fineLocationGranted || coarseLocationGranted) {
                Log.d("GeoScreen", "Permisos concedidos. Obteniendo ubicación.")
                // Lanzar la corrutina para obtener la ubicación
                coroutineScope.launch {
                    coordinates = getCurrentLocation(activity)
                    if (coordinates != null) {
                        isModalOpen = true
                    } else {
                        Log.d("GeoScreen", "No se pudo obtener la ubicación.")
                    }
                }
            } else {
                Log.d("GeoScreen", "Permisos no concedidos.")
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("GeoScreen", "Solicitando permisos de ubicación.")
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
            } else {
                Log.d("GeoScreen", "Permisos ya concedidos. Obteniendo ubicación.")
                coroutineScope.launch {
                    coordinates = getCurrentLocation(activity)
                    if (coordinates != null) {
                        isModalOpen = true
                    } else {
                        Log.d("GeoScreen", "No se pudo obtener la ubicación.")
                    }
                }
            }
        }) {
            Text("Mostrar coordenadas")
        }

        if (isModalOpen) {
            coordinates?.let {
                AlertDialog(
                    onDismissRequest = { isModalOpen = false },
                    confirmButton = {
                        Button(onClick = { isModalOpen = false }) {
                            Text("Cerrar")
                        }
                    },
                    title = { Text("Coordenadas") },
                    text = { Text("Latitud: ${it.first}, Longitud: ${it.second}") }
                )
            }
        }
    }
}

suspend fun getCurrentLocation(activity: Activity): Pair<Double, Double>? {
    return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        val locationManager = activity.getSystemService(Activity.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isGpsEnabled || isNetworkEnabled) {
            val locationProvider = if (isGpsEnabled) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("GeoScreen", "Permisos de ubicación no concedidos.")
                return@withContext null
            }
            val location: Location? = locationManager.getLastKnownLocation(locationProvider)
            location?.let {
                Log.d("GeoScreen", "Ubicación obtenida: Latitud ${it.latitude}, Longitud ${it.longitude}")
                return@withContext Pair(it.latitude, it.longitude)
            }
        } else {
            Log.d("GeoScreen", "GPS o red no habilitados.")
        }
        null
    }
}
