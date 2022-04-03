package com.example.xooicase.data.model

import com.example.xooicase.domain.model.DetailsObservableModel
import java.io.Serializable


data class DetailsDTO(
    var WeightType: String?,
    var LoadType: String?,
    var LoadCount: Int?,
    var TotalWeight: String?,
    var LoadTime: String?,
    var Volume: String?,
    var Place: String?,
    var WaybillImageUrl: String?,
    var LoadImages: ArrayList<String>
) : Serializable {}

// "extension"
fun DetailsDTO.toDetailsModel(): DetailsObservableModel {

    return DetailsObservableModel(dto = this)

}

