package ugm.bootcamp.teti.todo.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/banners.json")
    suspend fun getBanners(): List<String>
}