package com.basarcelebi.localguide

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basarcelebi.localguide.ui.theme.LocalGuideTheme
import com.basarcelebi.localguide.ui.theme.Orange
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        enableEdgeToEdge()
        setContent {
            LocalGuideTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SignUpScreen(innerPadding)
                }
            }
        }
    }



    @Composable
    fun SignUpScreen(innerPadding: PaddingValues) {
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var repassword by remember { mutableStateOf("") }
        var firstNameError by remember { mutableStateOf("") }
        var lastNameError by remember { mutableStateOf("") }
        var emailError by remember { mutableStateOf("") }
        var passwordError by remember { mutableStateOf("") }
        var repasswordError by remember { mutableStateOf("") }
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
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    color = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                TextField(
                    value = firstName,
                    onValueChange = { firstName = it
                        firstNameError = ""
                    },
                    label = { Text("First Name", style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = fontSize
                    ),
                        color = if (isDarkTheme) Color.White else Color.Black, modifier = Modifier.padding(bottom = 8.dp)) },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null,tint = textColor) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
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
                    isError = firstNameError.isNotEmpty(),
                )
                if (firstNameError.isNotEmpty()) {
                    Text(
                        text = firstNameError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = fontSize
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                TextField(
                    value = lastName,
                    onValueChange = { lastName = it
                        lastNameError = ""
                    },
                    label = { Text("Last Name", style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = fontSize
                    ),
                        color = if (isDarkTheme) Color.White else Color.Black, modifier = Modifier.padding(bottom = 8.dp)) },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = textColor) }, // Icon eklenen yer
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
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
                    isError = lastNameError.isNotEmpty(),
                )
                if (lastNameError.isNotEmpty()) {
                    Text(
                        text = lastNameError,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = fontSize
                        ),
                        color = if (isDarkTheme) Color.White else Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                TextField(
                    value = email,
                    onValueChange = { email = it
                        emailError = ""
                    },
                    label = { Text("Email",style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = fontSize
                    ),
                        color = if (isDarkTheme) Color.White else Color.Black, modifier = Modifier.padding(bottom = 8.dp)) },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null,tint = textColor) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
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
                    isError = emailError.isNotEmpty()
                )
                if (emailError.isNotEmpty()) {
                    Text(
                        text = emailError,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = fontSize
                        ),
                        color = if (isDarkTheme) Color.White else Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                TextField(
                    value = password,
                    onValueChange = { password = it
                        passwordError = ""
                    },
                    label = { Text("Password", style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = fontSize
                    ),
                        color = if (isDarkTheme) Color.White else Color.Black,  modifier = Modifier.padding(bottom = 8.dp)) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null,tint = textColor) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
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
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                        modifier = Modifier.padding(8.dp)
                    )
                }
                TextField(
                    value = repassword,
                    onValueChange = { repassword = it
                        repasswordError = ""
                    },
                    label = { Text("Repeat Password", style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = fontSize
                    ),
                        color = if (isDarkTheme) Color.White else Color.Black,  modifier = Modifier.padding(bottom = 8.dp)) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null,tint = textColor) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
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
                    isError = repasswordError.isNotEmpty()
                )
                if (repasswordError.isNotEmpty()) {
                    Text(
                        text = repasswordError,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = fontSize
                        ),
                        color = if (isDarkTheme) Color.White else Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Button(
                    onClick = { signUpWithEmailPassword(firstName, lastName, email, password, repassword) { success, errors ->
                        if (!success) {
                            firstNameError = errors["firstName"] ?: ""
                            lastNameError = errors["lastName"] ?: ""
                            emailError = errors["email"] ?: ""
                            passwordError = errors["password"] ?: ""
                            repasswordError = errors["repassword"] ?: ""
                        } } },
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
                        text = "Sign Up",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = fontSize
                        ),
                        color = if (isDarkTheme) Color.White else Color.Black,
                    )
                }
            }
        )



    }

    private fun signUpWithEmailPassword(firstName: String, lastName: String, email: String, password: String, repassword: String, callback: (Boolean, Map<String, String>) -> Unit) {
        val errors = mutableMapOf<String, String>()

        if (firstName.isBlank()) errors["firstName"] = "First name cannot be empty"
        if (lastName.isBlank()) errors["lastName"] = "Last name cannot be empty"
        if (email.isBlank()) errors["email"] = "Email cannot be empty"
        if (password.isBlank()) errors["password"] = "Password cannot be empty"
        if (repassword.isBlank()) errors["repassword"] = "Repeat password cannot be empty"
        if (password != repassword) errors["repassword"] = "Passwords do not match"

        if (errors.isNotEmpty()) {
            callback(false, errors)
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    // Update user profile with first name and last name
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName("$firstName $lastName")
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileUpdateTask ->
                            if (profileUpdateTask.isSuccessful) {
                                Toast.makeText(this, "Signed up as ${user.displayName}", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "Profile update failed: ${profileUpdateTask.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}