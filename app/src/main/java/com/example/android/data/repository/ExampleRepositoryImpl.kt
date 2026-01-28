package com.example.android.data.repository

import com.example.android.data.remote.datasource.ExampleDataSource
import com.example.android.domain.repository.ExampleRepository
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(
    private val exampleDataSource: ExampleDataSource
): ExampleRepository {
    override suspend fun getExample(): String {
        return exampleDataSource.getExample()
    }
}