/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    private val productA = ProductEntity("1", "Name A", "Type 1", "URL")
    private val productB = ProductEntity("2", "Name B", "Type 2", "URL")
    private val productC = ProductEntity("3", "Name C", "Type 1", "URL")

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
    fun testGetProduct() {
        assertThat(getValue(dao.getProduct(productA.productId)), equalTo(productA))
    }
}
