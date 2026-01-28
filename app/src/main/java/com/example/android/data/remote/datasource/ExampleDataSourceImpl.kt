package com.example.android.data.remote.datasource

import com.example.android.data.remote.datasource.api.ApiService
import javax.inject.Inject

class ExampleDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): ExampleDataSource {
    override suspend fun getExample(): String {
        return "test"
    }
}