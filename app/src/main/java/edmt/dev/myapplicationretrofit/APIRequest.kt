package edmt.dev.myapplicationretrofit

import edmt.dev.myapplicationretrofit.api.NewsApiJSON
import retrofit2.http.GET

interface APIRequest {
    @GET("/v2/top-headlines?country=us&apiKey=f3628ff9b4b7441da8cb6cc9c822c7ae")
    suspend fun getNews() : NewsApiJSON
}