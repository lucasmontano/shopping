package com.lucasmontano.shopping.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.lucasmontano.shopping.data.AppDatabase
import com.lucasmontano.shopping.data.entities.ProductEntity
import com.lucasmontano.shopping.utilities.DATA_FILENAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class InitDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val productList: List<ProductEntity> = withContext(Dispatchers.IO) {
                applicationContext.assets.open(DATA_FILENAME).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val productType = object : TypeToken<List<ProductEntity>>() {}.type
                        Gson().fromJson(jsonReader, productType)
                    }
                }
            }

            val database = AppDatabase.getInstance(applicationContext)
            database.productDao().insertAll(productList)

            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error initializing database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = this::class.simpleName
    }
}
