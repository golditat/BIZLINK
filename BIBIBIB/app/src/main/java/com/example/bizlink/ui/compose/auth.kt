package com.example.bizlink.ui.compose
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bizlink.R
import com.example.bizlink.data.database.entity.UserEntity
import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.ui.AuthViewModel
import com.example.bizlink.ui.BLViewModel
import com.example.bizlink.ui.compose.common.Colors
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(navController: NavHostController, viewModel: AuthViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val stroke = Stroke(width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    val context  = LocalContext.current
    var emailTextState by remember {
        mutableStateOf("")
    }
    var passwordTextState by remember {
        mutableStateOf("")
    }
    val shape = RoundedCornerShape(25.dp)
    Scaffold(
        Modifier.background(color =
        Colors.BG_COLOR
        )
    ) {innerPadding->
        Box(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    Colors.CARD_COLOR
                    // MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .wrapContentSize(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(32.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.rectangle_50),
                        contentDescription = "BIZLINK",
                        modifier = Modifier
                            .size(width = 120.dp, height = 80.dp),
                        tint = Colors.ACCENT_COLOR

                    )
                    Text(
                        text = AnnotatedString("Log in"),
                        modifier = Modifier
                            .padding(innerPadding)
                            .align(Alignment.CenterHorizontally),
                        style = TextStyle(fontSize = 36.sp, fontFamily = FontFamily(Font(R.font.merriweather)))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    StyledTextField(
                        emailTextState,
                        onTextChanged = {newValue->
                            emailTextState = newValue
                        },
                        labelText = "Email",
                        shape = shape,
                        Modifier
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    StyledTextField(
                        passwordTextState,
                        onTextChanged = {newValue->
                            passwordTextState = newValue
                        },
                        labelText = "Password",
                        shape = shape,
                        Modifier
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    GradientButton(
                        onClick = {
                            var flag = viewModel.login(email = emailTextState, password = passwordTextState)

                            if(flag){
                                navController.navigate("home")
                            }else{
                                Toast.makeText(context, "не верный логин или пароль", Toast.LENGTH_LONG)
                            }
                        },
                        label = "Log in"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate("reg") },
                        modifier = Modifier.height(48.dp).drawBehind {
                            drawRoundRect(
                                color = Color.Gray,
                                style = stroke,
                                cornerRadius = CornerRadius(8.dp.toPx())
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        ),
                    ){
                        Text(text = "Registration", style = TextStyle(color = Color.Gray, fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.merriweather)), textAlign = TextAlign.Center))
                    }
                }
            }
        }
    }
}
