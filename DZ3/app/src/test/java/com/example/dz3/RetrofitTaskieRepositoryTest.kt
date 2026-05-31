package com.example.dz3


import com.example.dz3.zadatak2.data.database.TaskEntity
import com.example.dz3.zadatak2.data.model.createTask.CreateTaskRequest
import com.example.dz3.zadatak2.data.model.createTask.CreateTaskResponse
import com.example.dz3.zadatak2.data.model.task.GetTaskResponse
import com.example.dz3.zadatak2.data.network.RetrofitTaskieApiService
import com.example.dz3.zadatak2.data.network.RetrofitTaskieInstance
import com.example.dz3.zadatak2.data.repository.RetrofitTaskieRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class RetrofitTaskieRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeTaskDao: FakeTaskDao
    private val mockApiService: RetrofitTaskieApiService = mockk()
    private lateinit var repository: RetrofitTaskieRepository

    @Before
    fun setUp() {
        fakeTaskDao = FakeTaskDao()
        repository = RetrofitTaskieRepository(fakeTaskDao)

        mockkObject(RetrofitTaskieInstance)
        coEvery { RetrofitTaskieInstance.apiService } returns mockApiService
    }

    @Test
    fun `createTask sprema privremeni lokalni zadatak u bazu i kada mreza baci gresku`() = runTest {

        val token = "Bearer token"
        val request = CreateTaskRequest("Zadaca 7", "Write unit tests")

        coEvery { mockApiService.createTask(token, request) } throws RuntimeException("Network Error")

        val response = repository.createTask(token, request)

        assertFalse(response.isSuccessful)

        val tasksInDb = fakeTaskDao.getAllTasks().first()
        assertEquals(1, tasksInDb.size)
        assertEquals("Zadaca 7", tasksInDb.first().title)
        assertEquals("Write unit tests", tasksInDb.first().body)
        assertFalse(tasksInDb.first().isSynced)
    }

    @Test
    fun `createTask uspjesan na mrezi - brise privremeni i sprema sinkronizirani zadatak s pravim ID-em`() = runTest {

        val token = "Bearer token"
        val request = CreateTaskRequest("Online Task", "Opis")
        val fakeNetworkResponse = Response.success(CreateTaskResponse(id = "server_id_999"))

        coEvery { mockApiService.createTask(token, request) } returns fakeNetworkResponse

        val response = repository.createTask(token, request)

        assertTrue(response.isSuccessful)
        assertEquals("server_id_999", response.body()?.id)


        val tasksInDb = fakeTaskDao.getAllTasks().first()

        assertEquals(1, tasksInDb.size)
        assertEquals("server_id_999", tasksInDb.first().taskId)
        assertTrue(tasksInDb.first().isSynced) // Sada je isSynced = true!
    }

    @Test
    fun `getTaskDetails uspjesan na mrezi - vraca podatak i azurira lokalnu bazu`() = runTest {

        val token = "Bearer token"
        val taskId = "task_1"
        val fakeNetworkResponse = Response.success(
            GetTaskResponse(
                "task_1",
                "Network title",
                "Network body"
            )
        )

        coEvery { mockApiService.getTaskDetails(token, taskId) } returns fakeNetworkResponse

        val response = repository.getTaskDetails(token, taskId)

        assertTrue(response.isSuccessful)
        assertEquals("Network title", response.body()?.title)

        val savedLocalTask = fakeTaskDao.getTaskById(taskId)
        assertEquals("Network body", savedLocalTask?.body)
        assertTrue(savedLocalTask?.isSynced == true)
    }

    @Test
    fun `getTaskDetails mreza baca gresku - vraca offline fallback podatak iz baze`() = runTest {

        val token = "Bearer token"
        val taskId = "task_offline_id"

        val stariLokalniTask = TaskEntity(taskId, "Local title", "Local body", isSynced = true)
        fakeTaskDao.insertTask(stariLokalniTask)

        coEvery { mockApiService.getTaskDetails(token, taskId) } throws RuntimeException("No internet")

        val response = repository.getTaskDetails(token, taskId)

        assertTrue(response.isSuccessful)
        assertEquals("Local title", response.body()?.title)
        assertEquals("Local body", response.body()?.body)
    }
}