package com.weatherappniviane

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherappniviane.model.ui.theme.WeatherAppNivianeTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppNivianeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginPage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bem-vindo/a!",
            fontSize = 24.sp
        )
        OutlinedTextField(
            value = email,
            label = { Text(text = "Digite seu e-mail") },
            modifier = Modifier.fillMaxWidth(0.9f),
            onValueChange = { email = it }
        )
        OutlinedTextField(
            value = password,
            label = { Text(text = "Digite sua senha") },
            modifier = Modifier.fillMaxWidth(0.9f),
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Button(
                onClick = {
                    if (activity != null) {
                        Toast.makeText(activity, "Login OK!", Toast.LENGTH_LONG).show()
                        activity.startActivity(
                            Intent(activity, MainActivity::class.java).setFlags(
                                Intent.FLAG_ACTIVITY_SINGLE_TOP
                            )
                        )
                    }
                },
                enabled = email.isNotEmpty() && password.isNotEmpty()
            ) {
                Text("Login")
            }
            Button(
                onClick = {
                    email = ""
                    password = ""
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Limpar")
            }
            Button(
                onClick = {
                    activity?.startActivity(Intent(activity, RegisterActivity::class.java))
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Registrar")
            }
        }
    }
}
