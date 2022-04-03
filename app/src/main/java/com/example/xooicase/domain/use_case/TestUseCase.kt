package com.example.xooicase.domain.use_case

import com.example.xooicase.common.Resource
import com.example.xooicase.data.model.toDetailsModel
import com.example.xooicase.domain.model.DetailsObservableModel
import com.example.xooicase.domain.repository.ITestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class TestUseCase @Inject constructor(private val iTestRepository: ITestRepository) {

    suspend fun getDetails(): Flow<Resource<DetailsObservableModel>> = flow {
        try {
            val details = iTestRepository.getDetails().toDetailsModel()
            // loading
            emit(Resource.Loading())
            //Success
            emit(Resource.Success(details))
        } catch (e: IOException) {
            //Failed
            emit(Resource.Error(e.localizedMessage))
        }


    }

}