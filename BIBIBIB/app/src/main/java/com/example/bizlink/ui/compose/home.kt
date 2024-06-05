package com.example.bizlink.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.bizlink.R
import com.example.bizlink.data.database.entity.ProjectEntity
import com.example.bizlink.data.database.entity.TaskEntity
import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.ui.BLViewModel
import com.example.bizlink.ui.compose.common.Colors
import com.example.bizlink.ui.entities.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: BLViewModel = viewModel()) {
    val stroke = Stroke(width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(100)}
    var projectName by remember{ mutableStateOf("") }
    var npdialog by remember {
        mutableStateOf(false)
    }
    var ntdialog by remember {
        mutableStateOf(false)
    }
    var taskName by remember{ mutableStateOf("") }
    var taskDescription by remember{ mutableStateOf("") }
    var startDate by remember{ mutableStateOf(Long.MIN_VALUE) }
    var deadlineDate by remember{ mutableStateOf(Long.MIN_VALUE) }
    var reviewDate by remember{ mutableStateOf(Long.MIN_VALUE) }
    val projects by viewModel.projectsListData.observeAsState(emptyList())
    val tasks by viewModel.tasksListData.observeAsState(emptyList())
    Scaffold(
        Modifier.background(color =
        Colors.BG_COLOR
        ),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        style = TextStyle(fontSize = 24.sp, fontFamily = FontFamily(Font(R.font.merriweather)), color = Colors.TEXT_COLOR)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Colors.CARD_COLOR,
                    titleContentColor = Colors.TEXT_COLOR,
                ),
                modifier = Modifier.shadow(elevation = 20.dp),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
            )
        }

    ) {innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                Modifier
                    .wrapContentSize()
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        Colors.CARD_COLOR
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = AnnotatedString("Projects"),
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.merriweather))
                        )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentHeight(),
                    ) {
                            repeat(projects.size) {
                                homeItem(
                                    projects.get(it).projectName,
                                    projects.get(it).id,
                                    1,
                                    navController
                                ) //TODO parse with ProjectEntity
                            }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .align(Alignment.Start)
                        ) {
                            IconButton(
                                onClick = {
                                          npdialog = true
                                          },
                                modifier = Modifier
                                    .drawBehind {
                                        drawRoundRect(
                                            color = Color.Gray,
                                            style = stroke,
                                            cornerRadius = CornerRadius(8.dp.toPx())
                                        )
                                    }
                                    .align(Alignment.CenterVertically)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "New Project"
                                )
                            }
                            Spacer(modifier = Modifier
                                .width(8.dp)
                                .height(1.dp))
                            Column(
                            ) {
                                Text(
                                    text = "New Project",
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .align(Alignment.CenterHorizontally),
                                    style = TextStyle(
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
            Box(
                Modifier
                    .wrapContentSize()
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        Colors.CARD_COLOR
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = AnnotatedString("Tasks"),
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.merriweather))
                        )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentHeight(),
                    ) {
                            repeat(tasks.size) {
                                homeItem(tasks.get(it).taskName, tasks.get(it).id, 0, navController)
                            }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Start)
                        ) {
                            IconButton(
                                onClick = { ntdialog = true },
                                modifier = Modifier
                                    .drawBehind {
                                        drawRoundRect(
                                            color = Color.Gray,
                                            style = stroke,
                                            cornerRadius = CornerRadius(8.dp.toPx())
                                        )
                                    }
                                    .align(Alignment.CenterVertically)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "New Project"
                                )
                            }
                            Spacer(modifier = Modifier
                                .width(8.dp)
                                .height(1.dp))
                            Column(
                            ) {
                                Text(
                                    text = "New Task",
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .align(Alignment.CenterHorizontally),
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.merriweather)),
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                            newTaskDialog(
                                onDismiss = { ntdialog = false },
                                confirm = { task, projectId ->
                                    ntdialog = false
                                    viewModel.createTask(task)
                                    navController.navigate("task/${task.id}") },
                                textStateName = taskName,
                                textStateDeskription = taskDescription,
                                onTextChangeName = {name-> taskName = name},
                                onTextChangeDeskription = {desk-> taskDescription = desk},
                                startDateState = {date ->startDate = date},
                                deadlineDateState = {date-> deadlineDate = date},
                                reviewDateState = {date -> reviewDate = date},
                                opend = ntdialog
                            )
                            newProjectDialog(
                                onDismiss = { npdialog = false},
                                confirm = { project ->
                                    npdialog = false
                                    //var project = viewModel.createProject(project)
                                    viewModel.createProject(project)
                                    navController.navigate("project/${project.id}")
                                },
                                textState = projectName,
                                onTextChange = {new-> projectName = new},
                                opend = npdialog
                            )
                    }
                }

            }
        }
    }
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun homeItem(
    name:String,
    id:Int,
    type:Int,
    navController: NavHostController
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Button(
            onClick = { if(type == 0){
                navController.navigate("task/$id")
            }else{
                navController.navigate("project/$id")
            } },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(48.dp)
                .width(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                Color.Gray
            )
        ) {
        }
        Spacer(modifier = Modifier
            .width(8.dp)
            .height(1.dp))
        Column(
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.merriweather)),
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

