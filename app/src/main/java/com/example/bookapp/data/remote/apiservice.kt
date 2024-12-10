package com.example.bookapp.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class RecommendRequest(val title: String, val k: Int = 5)

data class RecommendResponse(
    val recommendations: List<RecommendationItem>?
)

data class RecommendationItem(
    val ISBN: String?,
    val Authors: String?,
    val Titles: String?
)

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("recommend")
    suspend fun getRecommendations(@Body request: RecommendRequest): Response<RecommendResponse>
}
