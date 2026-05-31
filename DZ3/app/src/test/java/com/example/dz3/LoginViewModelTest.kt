package com.example.dz3

import com.example.dz3.zadatak2.presentation.LoginUIState
import com.example.dz3.zadatak2.presentation.LoginViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeTaskieRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        fakeRepository = FakeTaskieRepository()
        viewModel = LoginViewModel(fakeRepository)
    }

    @Test
    fun `prijava uspjesna - postavlja Success stanje i sprema token`() {

        fakeRepository.isLoginSuccessful = true
        fakeRepository.tokenToReturn = "token"

        viewModel.login("bam@gmail.com", "12fwefw56")

        val state = viewModel.uiState.value
        assertTrue(state is LoginUIState.Success)
        assertEquals("token", (state as LoginUIState.Success).token)
    }

    @Test
    fun `prijava neuspjesna sa 401 kodom - postavlja Failure stanje`() {
        fakeRepository.isLoginSuccessful = false

        viewModel.login("bam@gmail.com", "kriva_lozinka")

        val state = viewModel.uiState.value
        assertTrue(state is LoginUIState.Failure)
        assertTrue((state as LoginUIState.Failure).message.contains("Kôd: 401"))
    }

    @Test
    fun `resetState postavlja pocetno stanje na Idle`() {
        fakeRepository.isLoginSuccessful = false
        viewModel.login("bam@gmail.com", "lozinka")


        viewModel.resetState()

        assertEquals(LoginUIState.Idle, viewModel.uiState.value)
    }
}