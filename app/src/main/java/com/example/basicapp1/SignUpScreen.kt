package com.example.basicapp1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignUpScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userRepository = remember { UserRepository(context) }
    
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var isOldUser by remember { mutableStateOf(false) }
    
    // Get string resources outside of onClick lambda
    val successMessage = stringResource(R.string.user_registered_successfully)
    val oldUserMessage = stringResource(R.string.old_user_message)
    
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF667eea),
            Color(0xFF764ba2)
        )
    )
    
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { 
                showDialog = false
                if (isOldUser) {
                    onNavigateBack()
                }
            },
            title = {
                Text(
                    text = if (isOldUser) stringResource(R.string.old_user_message) else "Message",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(dialogMessage)
            },
            confirmButton = {
                Button(
                    onClick = { 
                        showDialog = false
                        if (isOldUser) {
                            onNavigateBack()
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header
        Text(
            text = stringResource(R.string.signup_title),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Sign Up Form Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // First Name Field
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text(stringResource(R.string.first_name_hint)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF667eea),
                        unfocusedBorderColor = Color(0xFF718096)
                    )
                )
                
                // Last Name Field
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text(stringResource(R.string.last_name_hint)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF667eea),
                        unfocusedBorderColor = Color(0xFF718096)
                    )
                )
                
                // Mobile Number Field
                OutlinedTextField(
                    value = mobileNumber,
                    onValueChange = { mobileNumber = it },
                    label = { Text(stringResource(R.string.mobile_number_hint)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF667eea),
                        unfocusedBorderColor = Color(0xFF718096)
                    )
                )
                
                // Sign Up Button
                Button(
                    onClick = {
                        val user = User(firstName, lastName, mobileNumber)
                        when (val result = userRepository.registerUser(user)) {
                            is RegistrationResult.Success -> {
                                dialogMessage = successMessage
                                isOldUser = false
                                showDialog = true
                            }
                            is RegistrationResult.OldUser -> {
                                dialogMessage = oldUserMessage
                                isOldUser = true
                                showDialog = true
                            }
                            is RegistrationResult.Error -> {
                                dialogMessage = result.message
                                isOldUser = false
                                showDialog = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF667eea)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.signup_button),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                // Back Button
                TextButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Back",
                        color = Color(0xFF667eea)
                    )
                }
            }
        }
    }
}
