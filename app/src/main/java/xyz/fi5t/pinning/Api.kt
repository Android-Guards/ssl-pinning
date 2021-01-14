package xyz.fi5t.pinning

import retrofit2.http.GET


interface Api {
    @GET("users")
    suspend fun getUsers(): List<User>
}
