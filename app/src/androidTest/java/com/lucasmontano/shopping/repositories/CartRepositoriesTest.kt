package com.lucasmontano.shopping.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lucasmontano.shopping.data.AppDatabase
import com.lucasmontano.shopping.data.dao.CartDao
import com.lucasmontano.shopping.data.dao.ProductDao
import com.lucasmontano.shopping.data.entities.ProductEntity
import com.lucasmontano.shopping.data.repositories.CartRepository
import com.lucasmontano.shopping.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartRepositoriesTest {
    private lateinit var database: AppDatabase
    private lateinit var productDao: ProductDao
    private lateinit var dao: CartDao
    private lateinit var repository: CartRepository

    private val productA = ProductEntity("1", "Name A", "Type 1", "URL")
    private val productB = ProductEntity("2", "Name B", "Type 2", "URL")
    private val productC = ProductEntity("3", "Name C", "Type 1", "URL")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        productDao = database.productDao()
        productDao.insertAll(listOf(productB, productC, productA))

        dao = database.cartDao()
        repository = CartRepository(dao)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testAddProductToCart() = runBlocking {
        repository.addProduct(productA.productId, 1)
        val productList = getValue(dao.getAllProducts())
        assertThat(productList.size, equalTo(1))
        assertThat(productList[0].product, equalTo(productA))
        assertTrue(getValue(dao.isAddedToCart(productA.productId)))
    }

    @Test
    fun testGetAllCartProducts() = runBlocking {
        repository.addProduct(productA.productId, 1)
        repository.addProduct(productB.productId, 1)
        assertThat(getValue(repository.getAllProducts()).size, equalTo(2))
    }
}
