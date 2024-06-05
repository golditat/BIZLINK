package com.example.bizlink.ui.compose

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.bizlink.R
import com.example.bizlink.data.database.entity.UserEntity
import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.ui.BLViewModel
import com.example.bizlink.ui.compose.common.Colors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController,viewModel: BLViewModel = viewModel()) {
    var openProjctInviteDialog by remember{ mutableStateOf(false)}
    var openTaskInviteDialog by remember{ mutableStateOf(false)}
    var openEditProfileDialog by remember{ mutableStateOf(false)}
    var openDeleteAccountDialog by remember{ mutableStateOf(false)}
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(100) }
    val context = LocalContext.current
    val user by viewModel.userData.observeAsState(null)
    Scaffold(
        Modifier.background(color =
        Colors.BG_COLOR
        ),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        style = TextStyle(fontSize = 24.sp, fontFamily = FontFamily(Font(R.font.merriweather)), color = Colors.TEXT_COLOR)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Colors.CARD_COLOR,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.shadow(elevation = 16.dp),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
            )
        }

    ) { innerPading ->
        Column(
            modifier = Modifier
                .padding(innerPading)
                .verticalScroll(scrollState)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(320.dp)
                    .align(Alignment.CenterHorizontally)
                    .wrapContentHeight()
                    .shadow(16.dp, CircleShape) // Применяем тень к контейнеру с картинкой
                    .clip(CircleShape) // Обрезаем контейнер круглой формой
            ){
                if(user?.photo?.size!= 0){
                    var imBit = BitmapFactory.decodeByteArray(user?.photo, 0, user?.photo?.size?:0).asImageBitmap()
                    Image(
                        bitmap = imBit,
                        contentDescription = null,
                        modifier = Modifier
                            .size(320.dp)
                            .clip(CircleShape)
                            .align(Alignment.Center)
                    )
                }else {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://img.razrisyika.ru/kart/137/544001-fotografiy-v-horoshem-kachestve-27.jpg")
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(320.dp)
                            .align(Alignment.Center),
                        loading = {
                            CircularProgressIndicator()
                        },
                        error = {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                tint = Color.Red,
                                contentDescription = ""
                            )
                        }
                    )
                }
            }
            Card(
                colors = CardDefaults.cardColors(
                    Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = user?.email ?:"ERROR",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = TextStyle(fontSize = 24.sp, fontFamily = FontFamily(Font(R.font.merriweather)))
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ){
                        Column() {
                            Button(
                                onClick = { openProjctInviteDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.Gray
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = ButtonDefaults.ContentPadding,

                                ) {
                                Icon(imageVector = Icons.Outlined.Face, contentDescription = "")
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(
                                    Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Invite project",
                                        modifier = Modifier.align(Alignment.Start),
                                        style = TextStyle(
                                            fontSize = 22.sp,
                                            fontFamily = FontFamily(Font(R.font.merriweather))
                                        )
                                    )
                                }
                            }
                            HorizontalDivider()
                            Button(
                                onClick = { openTaskInviteDialog = true},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.Gray
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = ButtonDefaults.ContentPadding,

                                ) {
                                Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "")
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(
                                    Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Invite task",
                                        modifier = Modifier.align(Alignment.Start),
                                        style = TextStyle(
                                            fontSize = 22.sp,
                                            fontFamily = FontFamily(Font(R.font.merriweather))
                                        )
                                    )
                                }
                            }
                            HorizontalDivider()
                            Button(
                                onClick = { openEditProfileDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.Gray
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = ButtonDefaults.ContentPadding,

                                ) {
                                Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = "")
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(
                                    Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Edit profile",
                                        modifier = Modifier.align(Alignment.Start),
                                        style = TextStyle(
                                            fontSize = 22.sp,
                                            fontFamily = FontFamily(Font(R.font.merriweather))
                                        )
                                    )
                                }
                            }
                            HorizontalDivider()
                            Button(
                                onClick = { openDeleteAccountDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.Red
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = ButtonDefaults.ContentPadding,

                                ) {
                                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(
                                    Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Delete account",
                                        modifier = Modifier.align(Alignment.Start),
                                        style = TextStyle(
                                            fontSize = 22.sp,
                                            fontFamily = FontFamily(Font(R.font.merriweather))
                                        )
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
    if(openProjctInviteDialog){
        var projectCode by remember{ mutableStateOf("") }
        AlertDialog(
            modifier =  Modifier.shadow(32.dp, RoundedCornerShape(32.dp)),
            onDismissRequest = {
                openProjctInviteDialog = false
                /*TODO*/
            },
            confirmButton = {
                GradientButton(onClick = {
                    openProjctInviteDialog = false
                   Toast.makeText(context, "успешно", Toast.LENGTH_SHORT)
                }, label = "Save")
            },
            title = { Text(text = "Invite Project") },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            text = {
                StyledTextField(
                    textState = projectCode,
                    onTextChanged = {
                        projectCode = it
                    },
                    labelText = "Enter Code",
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier
                )
            },
            containerColor = Color.White,
            tonalElevation = AlertDialogDefaults.TonalElevation
        )
    }
    if(openTaskInviteDialog){
        var taskCode by remember{ mutableStateOf("") }
        AlertDialog(
            modifier =  Modifier.shadow(32.dp, RoundedCornerShape(32.dp)),
            onDismissRequest = {
                openTaskInviteDialog = false
                /*TODO*/
            },
            confirmButton = {
                GradientButton(onClick = {
                    openTaskInviteDialog = false
                    Toast.makeText(context, "успешно", Toast.LENGTH_SHORT)
                }, label = "Save")
            },
            title = { Text(text = "Invite Task") },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            text = {
                StyledTextField(
                    textState = taskCode,
                    onTextChanged = {
                        taskCode = it
                    },
                    labelText = "Enter Code",
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier
                )
            },
            containerColor = Color.White,
            tonalElevation = AlertDialogDefaults.TonalElevation
        )
    }
    if(openEditProfileDialog){
        var email by remember{ mutableStateOf(user?.email ?: "ERROR") }
        var photo = user?.photo ?: ByteArray(0)
        var password by remember {
            mutableStateOf("")
        }
        var selectedImage = remember { mutableStateOf<ImageBitmap>(ByteArrayInputStream(photo).use { stream ->
                BitmapFactory.decodeStream(stream)?.asImageBitmap()
        }?: ImageBitmap(0, 0)) }
        AlertDialog(
            modifier =  Modifier.shadow(32.dp, RoundedCornerShape(32.dp)),
            onDismissRequest = {
                openEditProfileDialog = false
                /*TODO*/
            },
            confirmButton = {
                GradientButton(onClick = {
                    openEditProfileDialog = false
                    photo = ByteArrayOutputStream().apply {
                        selectedImage.value.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, this)
                    }.toByteArray()
                    //TODO
                }, label = "Save")
            },
            title = { Text(text = "Edit Profile") },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            text = {
                Column(Modifier.wrapContentSize()) {
                    StyledTextField(
                        textState = password,
                        onTextChanged = {
                            password = it
                        },
                        labelText = "password",
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier.padding(8.dp)
                    )
                    StyledTextField(
                        textState = email,
                        onTextChanged = {
                            email = it
                        },
                        labelText = "Enter Code",
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier.padding(8.dp)
                    )
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()
                    ) { uri ->
                        // Обработка выбранного изображения
                        uri?.let { uri ->
                            val inputStream = context.contentResolver.openInputStream(uri)
                            inputStream?.use { stream ->
                                selectedImage.value =
                                    BitmapFactory.decodeStream(stream)?.asImageBitmap()?: ImageBitmap(0,0)
                            }

                        }
                    }

                    Column(
                        Modifier.padding(8.dp)
                    ){
                        GradientButton(onClick = { launcher.launch("image/*") }, label = "Выбрать изображение")
                        selectedImage.value.let { bitmap ->
                            Image(bitmap = bitmap, contentDescription = null)
                        }
                    }
                }
            },
            containerColor = Color.White,
            tonalElevation = AlertDialogDefaults.TonalElevation
        )
    }
    if(openDeleteAccountDialog) {
        AlertDialog(
            modifier =  Modifier.shadow(32.dp, RoundedCornerShape(32.dp)),
            onDismissRequest = {
                openDeleteAccountDialog = false
                /*TODO*/
            },
            confirmButton = {
                GradientButton(onClick = {
                    openDeleteAccountDialog = false
                }, label = "OK")
            },
            title = { Text(text = "Do you really want to delete your account?") },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            text = {
                Text(
                    text = "After confirming the deletion, account recovery will not be possible. You will permanently lose your data. If you really want to delete your account, click OK",
                    style = TextStyle(fontSize = 22.sp, fontFamily = FontFamily(Font(R.font.merriweather)))
                )
            },
            containerColor = Color.White,
            tonalElevation = AlertDialogDefaults.TonalElevation
        )
    }
}
