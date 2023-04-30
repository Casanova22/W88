package vv.eighteight.w88conthoismash.view.app

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response
import vv.eighteight.w88conthoismash.data.list.ListDataModel
import vv.eighteight.w88conthoismash.services.url.ApiBaseUrlHandler
import java.util.ArrayList

class AppViewModel : ViewModel() {

    private val manifest = ApiBaseUrlHandler()

    var chismisInfo: ArrayList<ListDataModel> = ArrayList<ListDataModel>()
    private val kuhaInfo = MutableLiveData<List<ListDataModel>?>()
    val dataListModel: MutableLiveData<List<ListDataModel>?> get() = kuhaInfo

    fun initJson() {
        manifest.getFirebaseJson().enqueue(object : retrofit2.Callback<List<ListDataModel>> {
            override fun onResponse(
                call: Call<List<ListDataModel>>,
                responde: Response<List<ListDataModel>>
            ) {
                try {
                    val sheeEsh: List<ListDataModel> = responde.body()!!
                    for (ind in sheeEsh.indices) {
                        chismisInfo.add(sheeEsh[ind])
                        Log.e(ContentValues.TAG, sheeEsh.toString())
                    }
                    kuhaInfo.value = chismisInfo
                    Log.e(ContentValues.TAG, chismisInfo.toString())

                } catch (e: Exception) {
                    kuhaInfo.value = chismisInfo
                }
            }
            override fun onFailure(call: Call<List<ListDataModel>>, t: Throwable) {
                kuhaInfo.value = chismisInfo
            }
        } )
    }
}