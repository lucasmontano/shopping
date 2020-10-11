package com.lucasmontano.shopping.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.lucasmontano.shopping.data.entities.ProductEntity
import com.lucasmontano.shopping.data.repositories.ProductRepository
import com.lucasmontano.shopping.getValueUnitTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductListViewModelTest {

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var repository: ProductRepository

    private lateinit var viewModel: ProductListViewModel

    private val productA = ProductEntity("1", "Name A", "chair", "URL")
    private val productB = ProductEntity("2", "Name B", "couch", "URL")
    private val productC = ProductEntity("3", "Name C", "chair", "URL")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = ProductListViewModel(repository)

        every {
            repository.getAllProducts(null)
        } returns MutableLiveData(
            listOf(
                productA, productB, productC
            )
        )

        every {
            repository.getAllProducts("chair")
        } returns MutableLiveData(
            listOf(
                productA, productC
            )
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun `when initializing the viewmodel, default products are listed`() {
        assertTrue(getValueUnitTest(viewModel.uiState).products.isNotEmpty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun `when filtering by type, repository returns the right products`() {
        viewModel.filterByType("chair")
        assertTrue(getValueUnitTest(viewModel.uiState).products.size == 2)
    }

    @Test
    @Throws(InterruptedException::class)
    fun `when clearing filter, all products are available`() {
        viewModel.filterByType("chair")
        viewModel.clearFilter()
        assertTrue(getValueUnitTest(viewModel.uiState).products.size == 3)
    }
}
