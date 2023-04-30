package vv.eighteight.w88conthoismash.services.api

import retrofit2.http.GET
import vv.eighteight.w88conthoismash.data.list.ListDataModel

interface AppJsonHandler {
    @GET("W88.json?auth=nvOo8N6bit2yEJCw981nfvJETnp4CUDdridkNie7")
    fun readyJson(): retrofit2.Call<List<ListDataModel>>
}