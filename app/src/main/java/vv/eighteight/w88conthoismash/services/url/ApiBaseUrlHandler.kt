package vv.eighteight.w88conthoismash.services.url

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vv.eighteight.w88conthoismash.data.list.ListDataModel
import vv.eighteight.w88conthoismash.services.api.AppJsonHandler

class ApiBaseUrlHandler {
    private val fireBaseProject = "https://android-projects-82fc0-default-rtdb.firebaseio.com/"

    private val getApiJson : AppJsonHandler = Retrofit.Builder().baseUrl(fireBaseProject).addConverterFactory(
        GsonConverterFactory.create()
    ).build().create(AppJsonHandler::class.java)

    fun getFirebaseJson(): Call<List<ListDataModel>> {
        return getApiJson.readyJson()
    }
}