package com.saidatmaca.shoppingapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.saidatmaca.shoppingapp.core.common.Constants
import java.io.Serializable

@Entity(tableName = Constants.ROOM_FAV_PRODUCT_TABLE)
data class FavProductModel(
    @PrimaryKey(autoGenerate = true)
    var roomId : Int =0,
    @SerializedName("createdAt") var createdAt: String="",
    @SerializedName("name") var name: String="",
    @SerializedName("image") var image: String="",
    @SerializedName("price") var price: String="",
    @SerializedName("description") var description: String="",
    @SerializedName("model") var model: String="",
    @SerializedName("brand") var brand: String="",
    @SerializedName("id") var id: String=""
):Serializable
