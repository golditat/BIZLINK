package com.example.bizlink.di

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.bizlink.BuildConfig
import com.example.bizlink.data.database.Database
import com.example.bizlink.data.database.dao.CommentDao
import com.example.bizlink.data.database.dao.MaterialDao
import com.example.bizlink.data.database.dao.ProjectDao
import com.example.bizlink.data.database.dao.TaskDao
import com.example.bizlink.data.database.dao.UserDao
import com.example.bizlink.data.remote.retrofit.BizlinkApi
import com.example.bizlink.data.remote.retrofit.BizlinkRepositoryImpl
import com.example.bizlink.data.remote.retrofit.exeption.ExaptionHandlerDelegate
import com.example.bizlink.data.remote.retrofit.interceptor.AppIdInterceptor
import com.example.bizlink.data.remote.retrofit.mapper.CommentDomainModelMapper
import com.example.bizlink.data.remote.retrofit.mapper.JWTDomainModelMapper
import com.example.bizlink.data.remote.retrofit.mapper.MaterialDomainModelMapper
import com.example.bizlink.data.remote.retrofit.mapper.ProjectDomainModelMapper
import com.example.bizlink.data.remote.retrofit.mapper.TaskDomainModelMapper
import com.example.bizlink.data.remote.retrofit.mapper.UserDomainModelMapper
import com.example.bizlink.domain.mapper.CommentUIModelMapper
import com.example.bizlink.domain.mapper.JWTUIModelMapper
import com.example.bizlink.domain.mapper.MaterialUIModelMapper
import com.example.bizlink.domain.mapper.ProjectUIModelMapper
import com.example.bizlink.domain.mapper.TaskUIModelMapper
import com.example.bizlink.domain.mapper.UserUIModelMapper
import com.example.bizlink.domain.usecases.AddCommentUsecase
import com.example.bizlink.domain.usecases.AddMaterialUsecase
import com.example.bizlink.domain.usecases.CreateProjectUsecase
import com.example.bizlink.domain.usecases.CreateTaskUsecase
import com.example.bizlink.domain.usecases.GetDeadlinedTaskUsecase
import com.example.bizlink.domain.usecases.GetOnReviewTaskUsecase
import com.example.bizlink.domain.usecases.GetProjectUsecase
import com.example.bizlink.domain.usecases.GetStartedTaskUsecase
import com.example.bizlink.domain.usecases.GetTaskUsecase
import com.example.bizlink.domain.usecases.GetUsersProjectUsecase
import com.example.bizlink.domain.usecases.GetUsersTasksUsecase
import com.example.bizlink.domain.usecases.LoginUsecase
import com.example.bizlink.domain.usecases.RegistrationUsecase
import com.example.bizlink.domain.usecases.SubTaskUsecase
import com.example.bizlink.domain.usecases.SusProjectUsecase
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ServiceLocator {

     lateinit var dbInstance: Database

     lateinit var bizlinkPref: SharedPreferences

    lateinit var api: BizlinkApi

     lateinit var okHttpClient: OkHttpClient

    lateinit var userDao: UserDao

     lateinit var taskDao:TaskDao
     lateinit var projectDao: ProjectDao
    lateinit var materialDao: MaterialDao
     lateinit var commentDao: CommentDao

    private lateinit var repository: BizlinkRepositoryImpl

    lateinit var addCommentUsecase: AddCommentUsecase
        private set
    lateinit var addMaterialUsecase: AddMaterialUsecase
        private set
    lateinit var createProjectUsecase: CreateProjectUsecase
        private set
    lateinit var createTaskUsecase: CreateTaskUsecase
        private set
    lateinit var getProjectUsecase: GetProjectUsecase
        private set
    lateinit var getTaskUsecase: GetTaskUsecase
        private set
    lateinit var getUsersTasksUsecase: GetUsersTasksUsecase
        private set
    lateinit var getUsersPrijectsUsecase: GetUsersProjectUsecase
        private set
    lateinit var loginUsecase: LoginUsecase
        private set
    lateinit var registrationUsecase: RegistrationUsecase
        private set
    lateinit var subTaskUsecase: SubTaskUsecase
        private set
    lateinit var susProjectUsecase: SusProjectUsecase
        private set
    lateinit var getStartedTaskUsecase:GetStartedTaskUsecase
        private set
    lateinit var getDeadlinedTaskUsecase:GetDeadlinedTaskUsecase
        private set
    lateinit var getOnReviewTaskUsecase:GetOnReviewTaskUsecase
        private set

    lateinit var exceptionHandlerDelegate: ExaptionHandlerDelegate
        private set

    private val dispatcher = Dispatchers.IO

    private val userUIModelMapper: UserUIModelMapper = UserUIModelMapper()
    private val taskUIModelMapper:TaskUIModelMapper = TaskUIModelMapper()
    private val projectUIModelMapper:ProjectUIModelMapper = ProjectUIModelMapper()
    private val materialUIModelMapper: MaterialUIModelMapper = MaterialUIModelMapper()
    private val commentUIModelMapper: CommentUIModelMapper = CommentUIModelMapper()
    private val userDomainModelMapper: UserDomainModelMapper = UserDomainModelMapper()
    private val taskDomainModelMapper:TaskDomainModelMapper = TaskDomainModelMapper()
    private val projectDomainModelMapper:ProjectDomainModelMapper = ProjectDomainModelMapper()
    private val materialDomainModelMapper: MaterialDomainModelMapper = MaterialDomainModelMapper()
    private val commentDomainModelMapper: CommentDomainModelMapper = CommentDomainModelMapper()
    private val jwtDomainModelMapper:JWTDomainModelMapper = JWTDomainModelMapper()
    private val jwtuiModelMapper:JWTUIModelMapper = JWTUIModelMapper()



    private var ctxRef: WeakReference<Context>? = null

    fun provideContext(): Context {
        return ctxRef?.get() ?: throw IllegalStateException("Context is null")
    }

    fun initDataDependencies(ctx: Context) {
        ctxRef = WeakReference(ctx)
        dbInstance = Room.databaseBuilder(ctx, Database::class.java, "bizlink.db")
            .build()

        userDao = dbInstance.userDao
        taskDao = dbInstance.taskDao
        projectDao = dbInstance.projectDao
        materialDao = dbInstance.materialDao
        commentDao = dbInstance.commentDao

        bizlinkPref = ctx.getSharedPreferences("bizlink_pref", Context.MODE_PRIVATE)

        buildOkHttpClient()
        initApi()
        repository = BizlinkRepositoryImpl(
            userDao, taskDao, projectDao, materialDao, commentDao, api
        )
        exceptionHandlerDelegate = ExaptionHandlerDelegate()
    }

    fun initDomainDependencies() {
        addCommentUsecase = AddCommentUsecase(
            dispatcher,
            repository,
            commentDomainModelMapper,
            commentUIModelMapper
        )
        addMaterialUsecase = AddMaterialUsecase(
            dispatcher,
            repository,
            materialDomainModelMapper,
            materialUIModelMapper
        )
        createProjectUsecase = CreateProjectUsecase(
            dispatcher,
            repository,
            projectDomainModelMapper,
            projectUIModelMapper
        )
        createTaskUsecase = CreateTaskUsecase(
            dispatcher,
            repository,
            taskDomainModelMapper,
            taskUIModelMapper
        )
        getProjectUsecase = GetProjectUsecase(
            dispatcher,
            repository,
            projectDomainModelMapper,
            projectUIModelMapper
        )
        getTaskUsecase = GetTaskUsecase(
            dispatcher,
            repository,
            taskDomainModelMapper,
            taskUIModelMapper
        )
        loginUsecase = LoginUsecase(
            dispatcher,
            repository,
            jwtDomainModelMapper,
            jwtuiModelMapper
        )
        registrationUsecase = RegistrationUsecase(
            dispatcher,
            repository,
            userDomainModelMapper,
            userUIModelMapper
        )
        susProjectUsecase = SusProjectUsecase(
            dispatcher,
            repository,
            projectDomainModelMapper,
            projectUIModelMapper
        )
        subTaskUsecase = SubTaskUsecase(
            dispatcher,
            repository,
            taskDomainModelMapper,
            taskUIModelMapper
        )
        getUsersTasksUsecase = GetUsersTasksUsecase(
            dispatcher
        )
        getUsersPrijectsUsecase = GetUsersProjectUsecase(
            dispatcher
        )
        getStartedTaskUsecase = GetStartedTaskUsecase(
            dispatcher,
            repository,
        )
        getDeadlinedTaskUsecase = GetDeadlinedTaskUsecase(
            dispatcher,
            repository,
        )
        getOnReviewTaskUsecase = GetOnReviewTaskUsecase(
            dispatcher,
            repository,
        )
    }


    private fun buildOkHttpClient() {
        val clientBuilder = createUnsafeClient().addInterceptor(AppIdInterceptor())
            .addInterceptor { chain ->
                val newUrl = chain.request().url.newBuilder()
                    .addQueryParameter("units", "metric")
                    .build()

                val requestBuilder = chain.request().newBuilder().url(newUrl)

                chain.proceed(requestBuilder.build())
            }

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        okHttpClient = clientBuilder.build()
    }

    private fun createUnsafeClient(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(@SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
                okHttpClient.sslSocketFactory(
                    sslSocketFactory,
                    trustAllCerts.first() as X509TrustManager
                )
                okHttpClient.hostnameVerifier { _, _ -> true }
            }

            return okHttpClient
        } catch (e: Exception) {
            return okHttpClient
        }
    }

    private fun initApi() {
        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = builder.create(com.example.bizlink.data.remote.retrofit.BizlinkApi::class.java)
    }
}