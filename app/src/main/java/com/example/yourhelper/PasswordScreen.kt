package com.example.yourhelper

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PassWordScreen(){
    val gradientColor = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF00BCD4)
    )

    val context = LocalContext.current;

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColor)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(value = "", onValueChange = {},
            label = { Text(text = "email")})

        Spacer(modifier = Modifier.height(5.dp))

        Button(onClick = { /*TODO*/ },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            )
        ) {
            Text(text = "Recuperar pass")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){

            Text(text = "Volver",
                modifier = Modifier.clickable {
                    val navigate = Intent(context, MainActivity::class.java)
                    context.startActivity(navigate);
                },
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}