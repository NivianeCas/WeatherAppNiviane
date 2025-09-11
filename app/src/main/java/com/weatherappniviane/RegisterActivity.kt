package com.weatherappniviane

import android.annotation.SuppressLint
import android.app.Activity
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
class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppNivianeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterPage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun RegisterPage(modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var password2 by rememberSaveable { mutableStateOf("") }

    val activity = LocalContext.current as? Activity

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registrar Novo Usuário",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.size(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            value = password2,
            onValueChange = { password2 = it },
            label = { Text("Confirme sua senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.size(24.dp))

        Row {
            Button(
                onClick = {
                    Toast.makeText(activity, "Registro OK!", Toast.LENGTH_LONG).show()
                    activity?.finish()
                },
                enabled = name.isNotEmpty() && email.isNotEmpty()
                        && password.isNotEmpty() && password2.isNotEmpty()
                        && password == password2
            ) {
                Text("Registrar")
            }

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                onClick = {
                    name = ""
                    email = ""
                    password = ""
                    password2 = ""
                }
            ) {
                Text("Limpar")
            }
        }
    }
}