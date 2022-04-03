package com.example.xooicase.domain.repository

import com.example.xooicase.data.model.DetailsDTO

interface ITestRepository {

    suspend fun getDetails() : DetailsDTO
}