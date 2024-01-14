package ugm.bootcamp.teti.todo.data.repository

import ugm.bootcamp.teti.todo.data.remote.ApiService

interface BannerRepository {

    suspend fun getBanners(): List<String>

    class Impl(private val apiService: ApiService) : BannerRepository {
        override suspend fun getBanners(): List<String> {
            return apiService.getBanners()
        }

    }
}