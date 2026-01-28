package com.example.android.data.remote.datasource

interface ExampleDataSource {
    suspend fun getExample(): String
}