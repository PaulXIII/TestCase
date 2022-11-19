package se.test.testcase.data.dto

import com.google.gson.annotations.SerializedName

data class FilterResponse(

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null
)