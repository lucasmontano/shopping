package com.lucasmontano.shopping.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lucasmontano.shopping.data.dao.ProductDao
import com.lucasmontano.shopping.data.entities.ProductEntity
import com.lucasmontano.shopping.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: ProductDao

    private val productA = ProductEntity(
        "1",
        "Name A",
        "Type 1",
        "URL",
        ProductEntity.Price(10.0, "")
    )

    private val productB = ProductEntity(
        "2",
        "Name B",
        "Type 2",
        "URL",
        ProductEntity.Price(10.0, "")
    )

    private val productC = ProductEntity(
        "3",
        "Name C",
        "Type 1",
        "URL",
        ProductEntity.Price(10.0, "")
    )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = database.productDao()
        dao.insertAll(listOf(productB, productC, productA))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetAllProducts() {
        val productList = getValue(dao.getAllProducts())
        assertThat(productList.size, equalTo(3))
        assertThat(productList[0], equalTo(productA))
        assertThat(productList[1], equalTo(productB))
        assertThat(productList[2], equalTo(productC))
    }

    @Test
    fun testGetProductsByType() {
        val productList = getValue(dao.getAllProductsByType("Type 1"))
        assertThat(productList.size, equalTo(2))
        assertThat(productList[0], equalTo(productA))
        assertThat(productList[1], equalTo(productC))
    }

    @Test
    fun testGetProduct() {
        assertThat(getValue(dao.getProduct(productA.productId)), equalTo(productA))
    }
}
