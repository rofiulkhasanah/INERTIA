package com.inertia.ui.home

import androidx.lifecycle.MutableLiveData
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.repository.bencana.BencanaRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @Mock
    private lateinit var repository: BencanaRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun `load bencana should be success`() {
        val data = MutableLiveData<List<BencanaEntity>>()
        Mockito.`when`(repository.getAllBencana()).thenReturn(data)
        viewModel.getAllBencana()
        verify(repository).getAllBencana()
    }
}