package com.robbyari.storyapp.repository.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "story")
data class Story(

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val desc: String,

    @field:SerializedName("photoUrl")
    val photo: String,

    @field:SerializedName("createdAt")
    val create: String,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null

    )

data class StoryResponse(

    @field:SerializedName("listStory")
    val list: List<Story>,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null

)

data class RegisterResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String = "kok nul"

)

data class FileUploadResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String

)

data class DetailStoryResponse(

    @field:SerializedName("story")
    val story: Story

)

