package com.example.bizlink.ui.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bizlink.R
import com.example.bizlink.data.database.entity.UserEntity
import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.ui.AuthViewModel
import com.example.bizlink.ui.BLViewModel
import com.example.bizlink.ui.compose.common.Colors
import com.example.bizlink.ui.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegScreen(navController: NavHostController, viewModel: AuthViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()

    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    var emailTextState by remember {
        mutableStateOf("")
    }
    var passwordTextState by remember {
        mutableStateOf("")
    }
    var repeatedPasswordTextState by remember {
        mutableStateOf("")
    }
    val shape = RoundedCornerShape(25.dp)
    val context = LocalContext.current
    Scaffold(
        Modifier.background(
            color =
            Colors.BG_COLOR
        )
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    Colors.ACCENT_COLOR
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
                        text = AnnotatedString("Registration"),
                        modifier = Modifier
                            .padding(innerPadding)
                            .align(Alignment.CenterHorizontally),
                        style = TextStyle(
                            fontSize = 36.sp,
                            fontFamily = FontFamily(Font(R.font.merriweather))
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        StyledTextField(
                            emailTextState,
                            onTextChanged = { newValue ->
                                emailTextState = newValue
                            },
                            labelText = "Email",
                            shape = shape,
                            Modifier
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        StyledTextField(
                            passwordTextState,
                            onTextChanged = { newValue ->
                                passwordTextState = newValue
                            },
                            labelText = "Password",
                            shape = shape,
                            Modifier
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        StyledTextField(
                            repeatedPasswordTextState,
                            onTextChanged = { newValue ->
                                repeatedPasswordTextState = newValue
                            },
                            labelText = "Repeat password",
                            shape = shape,
                            Modifier
                        )
                    }
                    GradientButton(
                        onClick = {
                            //set redister
                            if (passwordTextState.equals(repeatedPasswordTextState)) {
                                val flag = viewModel.registration(User(0, emailTextState, ByteArray(0)),passwordTextState, repeatedPasswordTextState )
                                if(flag) {
                                    navController.navigate("auth")
                                }else{
                                    Toast.makeText(context, "попробуйте позднее", Toast.LENGTH_LONG)
                                }
                            } else {
                                Toast.makeText(context, "пароли не совпадают", Toast.LENGTH_LONG)
                            }
                        },
                        label = "Sign up"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate("auth") },
                        modifier = Modifier
                            .height(48.dp)
                            .drawBehind {
                                drawRoundRect(
                                    color = Color.Gray,
                                    style = stroke,
                                    cornerRadius = CornerRadius(8.dp.toPx())
                                )
                            },
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        ),
                    ) {
                        Text(
                            text = "Authentication",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.merriweather)),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}
suspend fun addUser(userEntity:UserEntity){
    coroutineScope {
        withContext(Dispatchers.IO) {
            ServiceLocator.userDao.addUser(
                userEntity
            )
        }
    }
}
@Composable
fun StyledTextField(
   textState:String,
   onTextChanged:(String)->Unit,
   labelText:String,
   shape:RoundedCornerShape,
   modifier:Modifier
) {
    Box (
        contentAlignment = Alignment.Center
    ){
        TextField(
            value = textState,
            onValueChange = onTextChanged,
            label = @Composable{
                Text(
                    text=labelText,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.merriweather)
                        )
                    )
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedSupportingTextColor = Color.Gray.copy(.20f),
                focusedSupportingTextColor = Color.Gray.copy(.20f),
                focusedContainerColor = Colors.TF_COLOR,
                unfocusedContainerColor = Colors.TF_COLOR
            ),
            shape = shape,
            modifier = modifier.shadow(
                    elevation = 8.dp,
                    shape = shape
            )

        )
    }
}

@Composable
fun GradientButton(
    onClick: ()->Unit,
    label:String
){
    Box(
        modifier = Modifier.padding(horizontal = 44.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = { onClick() },
            modifier= Modifier
                .fillMaxWidth()
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(25.dp)),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            contentPadding = PaddingValues(),
        ){
            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Colors.BT_GRADIENT_TOP_COLOR,
                                Colors.BT_GRADIENT_BOTTOM_COLOR
                            )
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center,

            ) {
                Text(
                    text = label,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.merriweather))
                    )
                )
            }
        }
    }
}