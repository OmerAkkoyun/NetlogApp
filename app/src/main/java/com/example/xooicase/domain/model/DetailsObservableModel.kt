package com.example.xooicase.domain.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.xooicase.data.model.DetailsDTO
import java.io.Serializable

data class DetailsObservableModel(var dto: DetailsDTO) : Serializable, BaseObservable() {

    var weightType: String
        @Bindable get() {
            return if (!dto.WeightType.isNullOrEmpty()) dto.WeightType!!
            else ""
        }
        set(value) {
            dto.WeightType = value
            notifyPropertyChanged(BR.weightType)
        }

    var loadType: String
        @Bindable get() {
            return if (!dto.LoadType.isNullOrEmpty()) dto.LoadType!!
            else ""
        }
        set(value) {
            dto.LoadType = value
            notifyPropertyChanged(BR.loadType)
        }

    var loadCount: Int
        @Bindable get() {
            return if (dto.LoadCount != null) dto.LoadCount!!
            else 0
        }
        set(value) {
            dto.LoadCount = value
            notifyPropertyChanged(BR.loadCount)
        }


    var totalWeight: String
        @Bindable get() {
            return if (!dto.TotalWeight.isNullOrEmpty()) dto.TotalWeight!!
            else ""
        }
        set(value) {
            dto.TotalWeight = value
            notifyPropertyChanged(BR.totalWeight)
        }


    var loadTime: String
        @Bindable get() {
            return if (!dto.LoadTime.isNullOrEmpty()) dto.LoadTime!!
            else ""
        }
        set(value) {
            dto.LoadTime = value
            notifyPropertyChanged(BR.loadTime)
        }

    var volume: String
        @Bindable get() {
            return if (!dto.Volume.isNullOrEmpty()) dto.Volume!!
            else ""
        }
        set(value) {
            dto.Volume = value
            notifyPropertyChanged(BR.volume)
        }

    var place: String
        @Bindable get() {
            return if (!dto.Place.isNullOrEmpty()) dto.Place!!
            else ""
        }
        set(value) {
            dto.Place = value
            notifyPropertyChanged(BR.place)
        }

    // Not: gerçek data geldiğinde "irsaliye url" beklenecektir.
    var waybillImageUrl: String
        @Bindable get() {
            return if (!dto.WaybillImageUrl.isNullOrEmpty()) dto.WaybillImageUrl!!
            else ""
        }
        set(value) {
            dto.WaybillImageUrl = value
            notifyPropertyChanged(BR.waybillImageUrl)
        }

    var loadImages: ArrayList<String>
        @Bindable get() {
            return dto.LoadImages
        }
        set(value) {
            dto.LoadImages = value
            notifyPropertyChanged(BR.loadImages)
        }


}