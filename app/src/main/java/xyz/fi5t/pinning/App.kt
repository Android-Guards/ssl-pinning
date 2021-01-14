package xyz.fi5t.pinning

import android.app.Application
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class App : Application() {
    val api by lazy { createApi() }

    private fun createApi(): Api {
        return Retrofit.Builder()
            .baseUrl("https://$HOST/")
            .client(createHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .certificatePinner(createPinner())
            .build()
    }

    private fun createPinner(): CertificatePinner {
        return CertificatePinner.Builder()
            .add(HOST, "sha256/ORtIOYkm5k6Nf2tgAK/uwftKfNhJB3QS0Hs608SiRmE=")
            .build()
    }

    companion object {
        private const val HOST = "api.github.com"
    }
}
