package com.lucasmontano.shopping.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.lucasmontano.shopping.data.entities.ProductEntity
import com.lucasmontano.shopping.data.repositories.ProductRepository
import com.lucasmontano.shopping.data.viewmodels.ProductListViewModel
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

    private val productA = ProductEntity("1", "Name A", "Type 1", "URL")
    private val productB = ProductEntity("2", "Name B", "Type 2", "URL")
    private val productC = ProductEntity("3", "Name C", "Type 1", "URL")

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
            repository.getAllProducts("Type A")
        } returns MutableLiveData(
            listOf(
                productA, productC
            )
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun `when initializing the viewmodel, default products are listed`() {
        assertTrue(getValueUnitTest(viewModel.products).isNotEmpty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun `when filtering by type, repository returns the right products`() {
        viewModel.filterByType("Type A")
        assertTrue(getValueUnitTest(viewModel.products).size == 2)
    }

    @Test
    @Throws(InterruptedException::class)
    fun `when clearing filter, all products are available`() {
        viewModel.filterByType("Type A")
        viewModel.clearFilter()
        assertTrue(getValueUnitTest(viewModel.products).size == 3)
    }
}
