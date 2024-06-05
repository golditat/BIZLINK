package com.example.bizlink.ui.compose

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material3.IconButton
import com.example.bizlink.R
import com.example.bizlink.ui.BLViewModel
import com.example.bizlink.ui.compose.common.Colors
import com.example.bizlink.ui.entities.Comment
import com.example.bizlink.ui.entities.Material
import com.example.bizlink.ui.entities.Project
import com.example.bizlink.ui.entities.Task
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@Composable
fun newProjectDialog(
    onDismiss: ()->Unit,
    confirm:(Project)->Unit,
    textState:String,
    onTextChange: (String) -> Unit,
    opend: Boolean,
){
    var flag = opend
    var coroutineScope = rememberCoroutineScope()
    var text by remember {
        mutableStateOf(textState)
    }
    if(flag) {
        AlertDialog(
            modifier = Modifier.shadow(32.dp, RoundedCornerShape(32.dp)),
            onDismissRequest = {
                flag = false
                onDismiss.invoke()
            },
            confirmButton = {
                GradientButton(onClick = {
                    flag = false
                    coroutineScope.launch {
                        confirm.invoke(
                            Project(
                                id = 0,
                                projectName = text,
                                invitingCode = "",
                                ownerID = 0
                            )
                        )
                    }
                }, label = "Save")
            },
            title = { Text(text = "Add new Project") },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            text = {
                StyledTextField(
                    textState = text,
                    onTextChanged = {
                        text = it
                        onTextChange.invoke(it)
                    },
                    labelText = "Project Name",
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier
                )
            },
            containerColor = Colors.CARD_COLOR,
            tonalElevation = AlertDialogDefaults.TonalElevation
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun newTaskDialog(
    onDismiss: ()->Unit,
    confirm:(Task, Int)->Unit,
    textStateName:String,
    onTextChangeName: (String) -> Unit,
    textStateDeskription:String,
    onTextChangeDeskription: (String) -> Unit,
    startDateState:(Long)->Unit,
    deadlineDateState:(Long)->Unit,
    reviewDateState:(Long)->Unit,
    opend:Boolean,
    viewModel: BLViewModel = viewModel()
){
    val coffeeDrinks by viewModel.projectsListData.observeAsState(emptyList())
    var coroutoneScope = rememberCoroutineScope()
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedId by remember { mutableStateOf(0) }
    var scrollState = rememberScrollState()
    var textStateN by remember {
        mutableStateOf(textStateName)
    }
    var textStateD by remember {
        mutableStateOf(textStateDeskription)
    }
    var startDate by remember {
        mutableStateOf("")
    }
    var deadlineDate by remember {
        mutableStateOf("")
    }
    var reviewDate by remember {
        mutableStateOf("")
    }
    var start by remember {
        mutableStateOf("Start date")
    }
    var deadline by remember {
        mutableStateOf("Deadline date")
    }
    var review by remember {
        mutableStateOf("Review date")
    }
    val mCalendar = Calendar.getInstance()
    var mYear by remember {
        mutableStateOf( mCalendar.get(Calendar.YEAR))}
    var mMonth by remember {
        mutableStateOf(mCalendar.get(Calendar.MONTH))}
    var mDay by remember {
        mutableStateOf(mCalendar.get(Calendar.DAY_OF_MONTH))}
    val stroke = Stroke(width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    LaunchedEffect(Unit) { scrollState.animateScrollTo(100) }
    var flag = opend
    if(flag) {
        AlertDialog(
            modifier = Modifier.shadow(32.dp, RoundedCornerShape(32.dp)),
            onDismissRequest = {
                flag = false
                onDismiss.invoke()
            },
            confirmButton = {
                GradientButton(onClick = {
                    flag = false
                    coroutoneScope.launch {
                        confirm.invoke(
                            Task(
                                id = 0,
                                taskName = textStateN,
                                deskription = textStateD,
                                mentorID = 0,
                                projectID = 0,
                                status = 0,
                                start_date = Date(start),
                                deadline_date = Date(deadline),
                                review_date = Date(review),
                                comments = ArrayList<Comment>(),
                                materials = ArrayList<Material>(),
                                invitingCode = ""
                            ), 0
                        )
                    }
                }, label = "Save")
            },
            title = { Text(text = "Add new Task") },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            text = {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = {
                                        // We shouldn't hide the menu when the user enters/removes any character
                                    },
                                    scrollState = scrollState
                                ) {
                                    coffeeDrinks.forEach { item ->
                                        DropdownMenuItem(
                                            text = { Text(text = item.projectName) },
                                            onClick = {
                                                selectedId = item.id
                                                expanded = false
                                            },
                                            colors = MenuDefaults.itemColors(
                                                textColor = Colors.TEXT_COLOR,
                                                trailingIconColor = Colors.ACCENT_COLOR,
                                                disabledTextColor = Color.Gray,
                                                disabledTrailingIconColor = Color.Black
                                            )
                                        )
                                    }
                                }

                    }
                    var datepicerStart = DatePickerDialog(
                        context,
                        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                            startDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
                            start = "$mDayOfMonth/${mMonth + 1}/$mYear"
                            mCalendar.set(mYear, mMonth, mDayOfMonth, 0, 0, 0)
                            startDateState(mCalendar.timeInMillis)
                        }, mYear, mMonth, mDay
                    )
                    var datepicerDeadline = DatePickerDialog(
                        context,
                        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                            deadlineDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
                            deadline = "$mDayOfMonth/${mMonth + 1}/$mYear"
                            mCalendar.set(mYear, mMonth, mDayOfMonth, 0, 0, 0)
                            deadlineDateState(mCalendar.timeInMillis)
                        }, mYear, mMonth, mDay
                    )
                    var datepicerReview = DatePickerDialog(
                        context,
                        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                            reviewDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
                            review = "$mDayOfMonth/${mMonth + 1}/$mYear"
                            mCalendar.set(mYear, mMonth, mDayOfMonth, 0, 0, 0)
                            reviewDateState(mCalendar.timeInMillis)
                        }, mYear, mMonth, mDay
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    StyledTextField(
                        textState = textStateN,
                        onTextChanged = {
                            textStateN = it
                            onTextChangeName.invoke(it)
                        },
                        labelText = "Task Name",
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    StyledTextField(
                        textState = textStateD,
                        onTextChanged = {
                            textStateD = it
                            onTextChangeDeskription.invoke(it)
                        },
                        labelText = "Description",
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier
                        .height(48.dp)
                        .align(Alignment.CenterHorizontally)) {
                        Column(
                            Modifier
                                .wrapContentHeight()
                                .align(Alignment.CenterVertically)) {
                            Text(
                                text = start,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.merriweather)),
                                    color = Colors.TEXT_COLOR,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        IconButton(
                            onClick = { datepicerStart.show() },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .drawBehind {
                                    drawRoundRect(
                                        color = Color.Gray,
                                        style = stroke,
                                        cornerRadius = CornerRadius(8.dp.toPx())
                                    )
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "start date"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier
                        .height(48.dp)
                        .align(Alignment.CenterHorizontally)) {
                        Column(
                            Modifier
                                .wrapContentHeight()
                                .align(Alignment.CenterVertically)) {
                            Text(
                                text = deadline,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.merriweather)),
                                    color = Colors.TEXT_COLOR,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        IconButton(
                            onClick = { datepicerDeadline.show() },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .drawBehind {
                                    drawRoundRect(
                                        color = Color.Gray,
                                        style = stroke,
                                        cornerRadius = CornerRadius(8.dp.toPx())
                                    )
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "deadline date"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier
                        .height(48.dp)
                        .align(Alignment.CenterHorizontally)) {
                        Column(
                            Modifier
                                .wrapContentHeight()
                                .align(Alignment.CenterVertically)) {
                            Text(
                                text = review,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.merriweather)),
                                    color = Colors.TEXT_COLOR,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        IconButton(
                            onClick = { datepicerReview.show() },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .drawBehind {
                                    drawRoundRect(
                                        color = Color.Gray,
                                        style = stroke,
                                        cornerRadius = CornerRadius(8.dp.toPx())
                                    )
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "review date"
                            )
                        }
                    }
                }
            },
            containerColor = Color.White,
            tonalElevation = AlertDialogDefaults.TonalElevation
        )
    }
}
