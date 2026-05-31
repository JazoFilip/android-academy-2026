package com.example.dz3.zadatak2.ui.loginScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.dz3.zadatak2.presentation.LoginUIState

@Composable
fun LoginScreen(
    uiState: LoginUIState,
    onLoginClicked: (String, String) -> Unit,
    onResetState: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUIState.Success -> {
                onLoginSuccess(uiState.token)
            }
            is LoginUIState.Failure -> {
                Toast.makeText(context, uiState.message, Toast.LENGTH_LONG).show()
                onResetState()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Taskie Login", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(bottom = 32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState !is LoginUIState.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState !is LoginUIState.Loading
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState is LoginUIState.Loading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        onLoginClicked(email.trim(), password.trim())
                    } else {
                        Toast.makeText(context, "Popunite sva polja!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Prijavi se")
            }
        }
    }
}