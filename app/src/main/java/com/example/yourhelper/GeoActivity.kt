import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class GeoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeoScreen(this)
        }
    }
}
