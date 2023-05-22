package com.example.domain

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun savePortfolio(data: Portfolio)

    fun getPortfolio(): Flow<Portfolio>
}