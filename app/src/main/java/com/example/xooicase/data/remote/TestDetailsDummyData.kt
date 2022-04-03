package com.example.xooicase.data.remote

import com.example.xooicase.data.model.DetailsDTO

class TestDetailsDummyData {
    companion object {

        fun getTestDetails(): DetailsDTO {
            return DetailsDTO(
                WeightType = "Genel Kargo",
                LoadType = "Paletli",
                LoadCount = 243,
                TotalWeight = "24 Ton",
                LoadTime = "14:30",
                Volume = "67 m3",
                Place = "Kapıkule",
                WaybillImageUrl = "",
                LoadImages = ArrayList<String>()

            )
        }

    }

}