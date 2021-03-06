package com.lucasmontano.shopping.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.lucasmontano.shopping.data.domain.*
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

    private val productA = ChairDomainModel(
        ProductDomainModel(
            id = "1",
            name = "Name A",
            price = 10.00,
            currency = "EUR",
            color = "blue",
            imageUrl = ""
        ),
        ProductWithMaterial(material = "Wood")
    )
    private val productB = CouchDomainModel(
        ProductDomainModel(
            id = "2",
            name = "Name B",
            price = 10.00,
            currency = "EUR",
            color = "blue",
            imageUrl = ""
        ),
        ProductWithSeats(numberOfSeats = 2)
    )
    private val productC = ChairDomainModel(
        ProductDomainModel(
            id = "3",
            name = "Name B",
            price = 10.00,
            currency = "EUR",
            color = "blue",
            imageUrl = ""
        ),
        ProductWithMaterial(material = "Wood")
    )

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
