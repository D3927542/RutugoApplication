package uk.ac.tees.mad.d3927542

import android.util.Log
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://u0012604.scedt.tees.ac.uk"

interface ApiService {
    // The resource path we're interested in and the method used to fetch it.
    @GET("/CIS4034-N/auth_test/horse_images.txt")
    fun getHorseList(): Call<String>
}
class ApiCall(
    private val userId: String,
    private val password: String) {

    private class BasicAuthInterceptor(username: String, password: String): Interceptor {
        private var credentials: String = Credentials.basic(username, password)

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            request = request.newBuilder().header("Authorization", credentials).build()
            return chain.proceed(request)
        }
    }

    fun getHorseList(
        callback: (List<String>) -> Unit,
        errorCallback: (message: String?) -> Unit = {}) {
        val client =
            OkHttpClient
                .Builder()
                    .addInterceptor(BasicAuthInterceptor(userId, password))
                    .build()

        val retrofit: Retrofit =
            Retrofit
                .Builder()
                    .client(client) // <---- Add the OkHttp client with the Basic Auth
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create()) // <-- To handle simple types like String
                    .build()

        // Create an concrete ApiService instance from the Retrofit instance.
        val service: ApiService = retrofit.create(ApiService::class.java)

        // Prepare the request
        val call: Call<String> = service.getHorseList()

        // Add the request to the queue
        call.enqueue(object : Callback<String> {
            // What to do upon a valid response from the server
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) { // OK/200 response.
                    val horseList: String = response.body().toString()

                    callback(horseList.split("\n").filter { it.isNotEmpty() }.toList())
                }
                else {
                    val failedOperationMessage = "Successful Response, but Failed Fetch. ${response.code()} -- ${response.message()}"
                    Log.d("ApiCall", " $failedOperationMessage")
                    errorCallback(failedOperationMessage)
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("ApiCall", "-----> ${t.message}")
                errorCallback(t.message)
            }
        })
    }
}