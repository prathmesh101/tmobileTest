package tmobile.github.RetrofitInterface

import tmobile.github.model.UserDetails
import tmobile.github.model.Details
import tmobile.github.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Url


/**
 * Created by Amanjeet Singh on 29/11/17.
 */
interface ApiInterface {

    @GET("/search/users")
    fun getSearchUsers(@Query("q") key: String): Call<UserDetails>

    @GET()
    fun getUserDetails(@Url url: String): Call<Details>

    @GET()
    fun getUserRepos(@Url url: String): Call<List<Details>>

    companion object Factory {

        fun create(): ApiInterface {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .client(client)
                    .build()

            return retrofit.create(ApiInterface::class.java);

        }

    }

}