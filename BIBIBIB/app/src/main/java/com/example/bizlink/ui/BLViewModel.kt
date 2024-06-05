package com.example.bizlink.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bizlink.data.remote.retrofit.exeption.runCatching
import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.ui.entities.CalendarDay
import com.example.bizlink.ui.entities.Comment
import com.example.bizlink.ui.entities.JWTLogin
import com.example.bizlink.ui.entities.Material
import com.example.bizlink.ui.entities.Project
import com.example.bizlink.ui.entities.ProjectItemModel
import com.example.bizlink.ui.entities.Task
import com.example.bizlink.ui.entities.TaskItemModel
import com.example.bizlink.ui.entities.User
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bizlink.data.database.entity.ProjectEntity
import com.example.bizlink.data.database.entity.TaskEntity
import com.example.bizlink.data.database.entity.UserEntity
import com.example.bizlink.data.remote.retrofit.mapper.UserDomainModelMapper
import java.util.Date

class BLViewModel: ViewModel() {

    private val _projectData = MutableLiveData<Project>()
    val projectData: LiveData<Project>
        get() = _projectData


    private val _taskData = MutableLiveData<Task>()
    val taskData: LiveData<Task>
        get() = _taskData

    private val _projectsData = MutableLiveData<List<Project>>()
    val projectsData: LiveData<List<Project>>
        get() = _projectsData

    private val _tasksData = MutableLiveData<List<Task>>()
    val tasksData: LiveData<List<Task>>
        get() = _tasksData

    private val _projectsListData = MutableLiveData<List<ProjectItemModel>>()
    val projectsListData: LiveData<List<ProjectItemModel>>
        get() = _projectsListData

    private val _tasksListData = MutableLiveData<List<TaskItemModel>>()
    val tasksListData: LiveData<List<TaskItemModel>>
        get() = _tasksListData

    private val _starttasksListData = MutableLiveData<List<TaskItemModel>>()
    val starttasksListData: LiveData<List<TaskItemModel>>
        get() = _starttasksListData

    private val _deadlinetasksListData = MutableLiveData<List<TaskItemModel>>()
    val deadlinetasksListData: LiveData<List<TaskItemModel>>
        get() = _deadlinetasksListData

    private val _revtasksListData = MutableLiveData<List<TaskItemModel>>()
    val revtasksListData: LiveData<List<TaskItemModel>>
        get() = _revtasksListData

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User>
        get() = _userData

    private val _commentData = MutableLiveData<Comment>()
    val commentData: LiveData<Comment>
        get() = _commentData

    private val _materialData = MutableLiveData<Material>()
    val materialData: LiveData<Material>
        get() = _materialData

    private val _curuserData = MutableLiveData<User>()
    val curuserData: LiveData<User>
        get() = _curuserData
    val errorsChannel = Channel<Throwable>()

    init{
        onAuth().apply {
            if(this){
                getTaskList()
                getProjectList()
                user()
            }
        }

    }

    fun getProject(id:Int){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.projectDao.getProjectById(id)?:ProjectEntity(0, "Project Name", "afsgsfsdassgs2323534sfxv", 0)
            }.onSuccess {
                _projectData.value = Project(it.id, it.projectName, it.invitingCode, it.ownerID)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
    fun getTask(id:Int){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.taskDao.getTaskById(id)?: TaskEntity(0, "Task Name", "afsgsfsdassgs2323534sfxv", "deskription", 0, 0, 0, Long.MIN_VALUE , Long.MIN_VALUE, Long.MIN_VALUE)
            }.onSuccess {
                _taskData.value = Task(it.id, it.taskName, it.invitingCode, it.deskription, it.mentorID, it.projectID, it.status, Date(it.start_date), Date(it.deadline_date), Date(it.review_date), ArrayList<Comment>(), ArrayList<Material>())
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun user(){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.userDao.getUserByEmail(ServiceLocator.bizlinkPref.getString("EMAIL", "")?:"")?:UserEntity(0, "", "", ByteArray(0))
            }.onSuccess {
                _curuserData.value = User(it.id, it.email, it.photo)
            }
        }
    }

    fun getNext30Days(): List<CalendarDay> {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val days = mutableListOf<CalendarDay>()

        for (i in 0 until 30) {
            val dayOfWeek = dateFormat.format(calendar.time)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val dateInMillis = calendar.timeInMillis
            days.add(CalendarDay(dayOfWeek, dayOfMonth, dateInMillis))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return days
    }

    fun getStartedByCurrentDate(current:Long){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.getStartedTaskUsecase.invoke(current)
            }.onSuccess {
                _starttasksListData.value = it
            }.onFailure {
                _starttasksListData.value = emptyList<TaskItemModel>()
            }
        }
    }

    fun getDeadlinedByCurrentDate(current:Long){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.getDeadlinedTaskUsecase.invoke(current)
            }.onSuccess {
                _deadlinetasksListData.value = it
            }.onFailure {
                _deadlinetasksListData.value = emptyList<TaskItemModel>()
            }
        }
    }

    fun getOnReviewByCurrentDate(current:Long){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.getOnReviewTaskUsecase.invoke(current)
            }.onSuccess {
                _revtasksListData.value = it
            }.onFailure {
                _revtasksListData.value = emptyList<TaskItemModel>()
            }
        }
    }

    fun onAuth():Boolean{
        var flag = false
        viewModelScope.launch {
            runCatching {
                    getProjects(ServiceLocator.bizlinkPref.getInt("USER_ID", 0))
                    getTasks(ServiceLocator.bizlinkPref.getInt("USER_ID", 0))
            }.onSuccess {
                flag = true
            }.onFailure {
                errorsChannel.send(it)
            }
        }
        return flag
    }

    fun addComment(comment: Comment){
        viewModelScope.launch {
            runCatching(ServiceLocator.exceptionHandlerDelegate){
                ServiceLocator.addCommentUsecase.invoke(comment)
            }.onSuccess {
                _commentData.value = comment
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
    fun addMaterial(material: Material){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.addMaterialUsecase.invoke(material)
            }.onSuccess {
                _materialData.value = material
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
    fun createProject(project: Project){
        viewModelScope.launch {
            runCatching {
               ServiceLocator.createProjectUsecase.invoke(project)
            }.onSuccess {
                _projectData.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
    fun createTask(task: Task){
        viewModelScope.launch {
            runCatching {
                 ServiceLocator.createTaskUsecase.invoke(task)
            }.onSuccess {
                _taskData.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
    fun getProjects(id:Int){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.getProjectUsecase.invoke(id)
            }.onSuccess {
                _projectsData.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
    fun getTasks(id:Int){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.getTaskUsecase.invoke(id)
            }.onSuccess {
                _tasksData.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
    fun subTask(code:String, id:Int){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.subTaskUsecase.invoke(code, id)
            }.onSuccess {
                _taskData.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
    fun subProject(code: String, id: Int){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.susProjectUsecase.invoke(code, id)
            }.onSuccess {
                _projectData.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
    fun getTaskList(){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.getUsersTasksUsecase.invoke()
            }.onSuccess {
                _tasksListData.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
    fun getProjectList(){
        viewModelScope.launch {
            runCatching {
                ServiceLocator.getUsersPrijectsUsecase.invoke()
            }.onSuccess {
                _projectsListData.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
}