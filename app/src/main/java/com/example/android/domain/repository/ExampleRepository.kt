package com.example.android.domain.repository

interface ExampleRepository {
    suspend fun getExample(): String
}