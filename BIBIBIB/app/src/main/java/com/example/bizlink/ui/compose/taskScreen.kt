package com.example.bizlink.ui.compose

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.bizlink.R
import com.example.bizlink.ui.BLViewModel
import com.example.bizlink.ui.compose.common.Colors
import com.example.bizlink.ui.entities.Comment
import java.io.ByteArrayOutputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(navController: NavHostController, id: Int?, viewModel: BLViewModel = viewModel()) {
    viewModel.getTask(id?:0)
    val task by viewModel.taskData.observeAsState()
    var openStatusDialog by remember{ mutableStateOf(false)}
    var rotated by remember { mutableStateOf(false) }
    val state = remember { mutableStateOf(task?.status?:0) }
    val status:ArrayList<String> = arrayListOf("Starting", "Ended", "On review")
    val rotationValue by animateFloatAsState(
        targetValue = if (rotated) -90f else 0f,
        animationSpec = tween(durationMillis = 500)
    )
    var adding by remember {
        mutableStateOf(false)
    }
    var newMaterial:ByteArray = ByteArray(0)
    var context = LocalContext.current
    val scrollState = rememberScrollState()
    var textState by remember{ mutableStateOf("") }
    LaunchedEffect(Unit) { scrollState.animateScrollTo(100) }
    Scaffold(
        Modifier.background(color =
        Colors.BG_COLOR
        ),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = task?.taskName?:"Task Name",
                        style = TextStyle(fontSize = 24.sp, fontFamily = FontFamily(Font(R.font.merriweather)), color = Colors.TEXT_COLOR)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Colors.CARD_COLOR,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.shadow(elevation = 16.dp),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }

    ){innerPading->
        Column (
            modifier = Modifier
                .padding(innerPading)
                .verticalScroll(scrollState)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    Colors.CARD_COLOR
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Status: ",
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterVertically),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.merriweather)),
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = status.get(state.value),
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterVertically),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.merriweather)),
                            textAlign = TextAlign.Center
                        )
                    )
                    Column(
                        Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {
                                openStatusDialog = true
                                rotated = !rotated
                            },
                            Modifier
                                .align(Alignment.End)
                                .size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "Set status",
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .size(48.dp)
                                    .graphicsLayer(rotationZ = rotationValue),
                                tint = Colors.ACCENT_COLOR
                            )
                        }

                        if (openStatusDialog) {
                            var selectStat by remember{ mutableStateOf(state) }
                            AlertDialog(
                                modifier =  Modifier.shadow(32.dp, RoundedCornerShape(32.dp)),
                                onDismissRequest = {
                                    openStatusDialog = false
                                    rotated = false
                                },
                                confirmButton = {
                                    GradientButton(onClick = {
                                        openStatusDialog = false
                                        rotated = false
                                        state.value = selectStat.value
                                    }, label = "Save")
                                },
                                title = { Text(text = "Select status") },
                                properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
                                text = {
                                    Column(Modifier.selectableGroup())
                                    {
                                        Row() {
                                            RadioButton(
                                                selected = if (state.value == 0) {
                                                    true
                                                } else {
                                                    false
                                                },
                                                onClick = {selectStat.value = 0},
                                                colors = RadioButtonDefaults.colors(
                                                    selectedColor = Colors.ACCENT_COLOR
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = status.get(0),
                                                fontSize = 20.sp,
                                                modifier = Modifier.align(Alignment.CenterVertically)
                                            )
                                        }
                                        Row() {
                                            RadioButton(
                                                selected = if (state.value == 1) {
                                                    true
                                                } else {
                                                    false
                                                },
                                                onClick = { selectStat.value = 1 },
                                                colors = RadioButtonDefaults.colors(
                                                    selectedColor = Colors.ACCENT_COLOR
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = status.get(1),
                                                fontSize = 20.sp,
                                                modifier = Modifier.align(Alignment.CenterVertically)
                                            )
                                        }
                                        Row() {
                                            RadioButton(
                                                selected = if (state.value == 2) {
                                                    true
                                                } else {
                                                    false
                                                },
                                                onClick = { selectStat.value = 2 },
                                                colors = RadioButtonDefaults.colors(
                                                    selectedColor = Colors.ACCENT_COLOR
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = status.get(2),
                                                fontSize = 20.sp,
                                                modifier = Modifier.align(Alignment.CenterVertically)
                                            )
                                        }
                                    }
                                },
                                containerColor = Colors.CARD_COLOR,
                                tonalElevation = AlertDialogDefaults.TonalElevation
                            )
                        }
                    }
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
                Text(
                    text = "Project",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.merriweather)),
                        textAlign = TextAlign.Center
                    )
                )
                Row(Modifier.padding(16.dp)) {
                    homeItem(name = "Project", id = task?.projectID ?: 0, type = 1, navController = navController)
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
                Text(
                    text = "Mentor",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.merriweather)),
                        textAlign = TextAlign.Center
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ){
                    Button(
                        onClick = { /*TODO загрузка картинки*/ },
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier.size(48.dp),
                        elevation = ButtonDefaults.buttonElevation(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            Color.Gray
                        )
                    ){}
                    Spacer(modifier = Modifier.width(8.dp))
                    Box() {
                        Text(
                            text = "Mentor",
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .align(Alignment.CenterStart),
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontFamily = FontFamily(Font(R.font.merriweather)),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
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
                Text(
                    text = "Description",
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.merriweather)),
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = task?.deskription?:"Description",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.merriweather)),
                        textAlign = TextAlign.Center
                    ),
                    textAlign = TextAlign.Start
                )
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
                Text(
                    text = "Materials",
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.merriweather)),
                        textAlign = TextAlign.Center
                    )
                )

                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    val selectedImage = remember { mutableStateOf<ImageBitmap?>(null) }
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()
                    ) { uri ->
                        // Обработка выбранного изображения
                        uri?.let { uri ->
                            val inputStream = context.contentResolver.openInputStream(uri)
                            inputStream?.use { stream ->
                                val byteArrayOutputStream = ByteArrayOutputStream()
                                stream.copyTo(byteArrayOutputStream)
                                newMaterial = byteArrayOutputStream.toByteArray()
                                val bitmap =BitmapFactory.decodeByteArray(newMaterial, 0, newMaterial!!.size)
                                selectedImage.value = bitmap?.asImageBitmap()
                            }
                        }
                    }
                    OutlinedButton(
                        onClick = {
                                    //Toast.makeText(context,"you cant add material, set your settings", Toast.LENGTH_LONG )

                                    launcher.launch("image/*")
                                    //TODO запрос на добавление материала
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .drawBehind {
                                drawRoundRect(
                                    color = Color.Gray,
                                    style = Stroke(
                                        width = 2f,
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(
                                                12f,
                                                12f
                                            ), 1f
                                        )
                                    ),
                                    cornerRadius = CornerRadius(32.dp.toPx())
                                )
                            },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Gray
                        ),
                        border = BorderStroke(1.dp, Color.Transparent)
                    ) {
                        Text(
                            text = "Add File",
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp)
                                .align(Alignment.CenterVertically),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.merriweather)),
                                textAlign = TextAlign.Center
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "",
                            tint = Color.Gray
                        )
                    }
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
                Text(
                    text = "Comments",
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.merriweather)),
                        textAlign = TextAlign.Center
                    )
                )

                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(

                    ){
                        StyledTextField(
                            textState = textState,
                            onTextChanged = {
                                textState = it
                            },
                            labelText ="Message" ,
                            shape = RoundedCornerShape(32.dp),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 4.dp)
                        )
                        IconButton(
                            onClick = { /*TODO добавление комментария*/ },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(end = 8.dp)
                                .size(48.dp)
                                .shadow(8.dp, CircleShape),
                            colors = IconButtonDefaults.iconButtonColors( Colors.ACCENT_COLOR),
                        ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = "",
                                    tint = Color.White)

                        }
                    }
                }
            }
        }
    }
}
@Composable
fun commentItem(
    comment: Comment
){
    Box(
        Modifier.wrapContentSize()
    ){
        Column{

        }
    }
}