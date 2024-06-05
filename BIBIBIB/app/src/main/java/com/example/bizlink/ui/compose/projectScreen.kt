package com.example.bizlink.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.bizlink.R
import com.example.bizlink.ui.BLViewModel
import com.example.bizlink.ui.compose.common.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(navController: NavHostController, int: Int?, viewModel: BLViewModel = viewModel()){
    val project by viewModel.projectData.observeAsState()
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(100) }
    var clipboardManager = LocalClipboardManager.current
    Scaffold(
        Modifier.background(color =
        Colors.BG_COLOR
        ),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = project?.projectName ?: "Project Name",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.merriweather)),
                            color = Colors.TEXT_COLOR
                        )
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

    ) { innerPading ->
        Column(
            modifier = Modifier
                .padding(innerPading)
                .verticalScroll(scrollState)
        ) {
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Inviting code:",
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
                        text = project?.invitingCode ?: "ERROR",
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterVertically),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.merriweather)),
                            textAlign = TextAlign.Center
                        )
                    )
                    IconButton(
                        onClick = { clipboardManager.setText(AnnotatedString(project?.invitingCode ?: "ERROR")) }
                    ) {
                        Icon(imageVector = Icons.Outlined.Share, contentDescription = " ")
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Project owner",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = TextStyle(fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.merriweather)))
                    )
                    ItemUserProfile(name = "Owner", id = 0, imageModel = "")
                }
            }
        }
    }
}


@Composable
fun ItemUserProfile(
    name:String,
    id:Int,
    imageModel:String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()

    ){

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Transparent)
                    .clickable { /*TODO*/ }
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://img.razrisyika.ru/kart/137/544001-fotografiy-v-horoshem-kachestve-27.jpg")
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(48.dp),
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

        Spacer(modifier = Modifier.width(8.dp))
        Box() {
            Text(
                text = name,
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
