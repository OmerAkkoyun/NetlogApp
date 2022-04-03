package com.example.xooicase.data.repository

import com.example.xooicase.data.model.DetailsDTO
import com.example.xooicase.data.remote.TestDetailsDummyData
import com.example.xooicase.domain.repository.ITestRepository

class TestRepositoryImpl: ITestRepository {
    override suspend fun getDetails(): DetailsDTO {

        return TestDetailsDummyData.getTestDetails()

    }


}