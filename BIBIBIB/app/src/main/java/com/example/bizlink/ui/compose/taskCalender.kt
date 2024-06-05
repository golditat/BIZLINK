package com.example.bizlink.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.bizlink.R
import com.example.bizlink.ui.BLViewModel
import com.example.bizlink.ui.compose.common.Colors
import com.example.bizlink.ui.entities.CalendarDay
import com.example.bizlink.ui.entities.Task
import com.example.bizlink.ui.entities.TaskItemModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalenderScreen(navController: NavHostController, viewModel: BLViewModel = viewModel()) {
    val shape: Shape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 16.dp,
        bottomEnd = 16.dp
    )
    var tabIndex by remember{ mutableStateOf(0) }
    val deadlineTasks by viewModel.deadlinetasksListData.observeAsState(emptyList())
    val reviewTasks by viewModel.revtasksListData.observeAsState(emptyList())
    val startTasks by viewModel.starttasksListData.observeAsState(emptyList())
    val calendarDays:List<CalendarDay> = viewModel.getNext30Days()
    var curSelectDate by remember {
        mutableStateOf(calendarDays.get(0))
    }
    Scaffold(
        Modifier.background(color =
        Colors.BG_COLOR
        ),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tasks",
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

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            Card (
                colors = CardDefaults.cardColors(
                    Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = shape,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, shape)
            ){
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(calendarDays.size) { calendarDay ->
                        calenderItem(calendarDay = calendarDays.get(calendarDay), selected = (curSelectDate.equals(calendarDays.get(calendarDay))), onSelect = {curSelectDate = calendarDays.get(calendarDay)})
                    }
                }
            }

            Card (
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
            ){
                TabRow(selectedTabIndex = tabIndex,
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(32.dp)),
                    containerColor = Color.Transparent,
                    contentColor = Color.Gray,
                    divider = {
                        HorizontalDivider(
                            color = Color.Gray
                        )
                    }
                ) {
                    Tab(selected = tabIndex == 0,
                        onClick = { tabIndex = 0},
                        text = {
                            Text( text = "Dedline")
                        }
                    )
                    Tab(selected = tabIndex == 1,
                        onClick = { tabIndex = 1},
                        text = {
                            Text( text = "Start")
                        }
                    )
                    Tab(selected = tabIndex == 2,
                        onClick = { tabIndex = 2 },
                        text = {
                            Text( text = "Review")
                        }
                    )
                }
                when(tabIndex){
                    0-> deadlineTasks.forEach{
                        taskItem(task = it, navController)
                    }
                    1-> startTasks.forEach {
                        taskItem(task = it, navController)
                    }
                    2-> reviewTasks.forEach {
                        taskItem(task = it, navController = navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun taskItem(
     task: TaskItemModel,
     navController: NavHostController
){
    val id = task.id
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Button(
            onClick = { navController.navigate("task/$id") },
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
                text = task.taskName,
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

@Composable
fun calenderItem(
    selected:Boolean,
    onSelect:()->Unit,
    calendarDay:CalendarDay
){
    var color = Colors.TEXT_COLOR
    var buttonTextColor = Colors.TEXT_COLOR
    var buttonColor = Colors.CARD_COLOR
    if(selected){
        color = Colors.ACCENT_COLOR
        buttonTextColor = Colors.CARD_COLOR
        buttonColor = Colors.ACCENT_COLOR
    }
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp)
    ) {
        Text(
            text = calendarDay.dayOfWeek,
            style = TextStyle(color, fontSize = 20.sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .size(52.dp)
                .padding(0.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = onSelect,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    buttonColor
                ),
                shape = CircleShape,
                elevation = ButtonDefaults.buttonElevation(8.dp),
                content = {
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center) {
                        Text(
                            text = calendarDay.dayOfMonth.toString(),
                            style = TextStyle(
                                color = buttonTextColor,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
@Preview
fun calenderPreview(){
    CalenderScreen(navController = rememberNavController())
}