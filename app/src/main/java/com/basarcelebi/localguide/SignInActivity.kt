package com.basarcelebi.localguide

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basarcelebi.localguide.ui.theme.LocalGuideTheme
import com.basarcelebi.localguide.ui.theme.Orange
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        enableEdgeToEdge()
        setContent {
            LocalGuideTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SignInScreen(innerPadding, this)
                }
            }
        }
    }

    @Composable
    fun SignInScreen(innerPadding: PaddingValues, context: Context) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var emailError by remember { mutableStateOf("") }
        var passwordError by remember { mutableStateOf("") }
        val poppins = FontFamily(Font(R.font.poppins))
        val isDarkTheme = isSystemInDarkTheme()
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val fontSize = with(LocalDensity.current) {
            when {
                screenWidth < 600.dp -> 14.sp
                screenWidth < 840.dp -> 16.sp
                else -> 18.sp
            }
        }
        val textColor = if (isDarkTheme) Color.White else Color.Black


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                color = if (isDarkTheme) Color.White else Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it
                    emailError = ""},
                label = { Text("Email", style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = fontSize
                ),
                    color = if (isDarkTheme) Color.White else Color.Black, modifier = Modifier.padding(bottom = 8.dp)) },
                singleLine = true,
                leadingIcon = {
                    androidx.compose.material.Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = textColor
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = textColor,
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    placeholderColor = textColor,
                    focusedIndicatorColor = textColor,
                    unfocusedLabelColor = textColor,
                    focusedLabelColor = textColor,
                    leadingIconColor = textColor,
                    cursorColor = textColor
                )
            )

            TextField(
                value = password,
                onValueChange = { password = it
                    passwordError = ""},
                label = { Text("Password", style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = fontSize
                ),
                    color = if (isDarkTheme) Color.White else Color.Black, modifier = Modifier.padding(bottom = 8.dp)) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = {
                    androidx.compose.material.Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = textColor
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = textColor,
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    placeholderColor = textColor,
                    focusedIndicatorColor = textColor,
                    unfocusedLabelColor = textColor,
                    focusedLabelColor = textColor,
                    leadingIconColor = textColor,
                    cursorColor = textColor
                ),
                isError = passwordError.isNotEmpty()
            )
            if (passwordError.isNotEmpty()) {
                Text(
                    text = passwordError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = fontSize
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }

            Button(
                onClick = { if (email.isNotEmpty() && password.isNotEmpty()) {
                    signInWithEmailPassword(email, password, context){ success, errorMessage ->
                        if (!success) {
                            passwordError = errorMessage
                        } }
                }},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Orange,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(
                    text = "Sign In",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = fontSize
                    ),
                    color = if (isDarkTheme) Color.White else Color.Black
                )
            }

            Spacer(modifier = Modifier.height(2.dp))
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navigateToSignUp(context) }
            ) {
                Text("Don't have an account? Sign Up", style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = fontSize
                ),color = if (isDarkTheme) Color.White else Color.Black)
            }
        }

    }

    fun signInWithEmailPassword(email: String, password: String, context: Context, callback: (Boolean, String) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    callback(true, "")
                    Toast.makeText(context, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    callback(false, "Wrong email or password, please try again!")
                }
            }
    }

    fun navigateToSignUp(context: Context) {
        context.startActivity(Intent(context, SignUpActivity::class.java))
    }
}
