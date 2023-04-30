package vv.eighteight.w88conthoismash.view.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vv.eighteight.w88conthoismash.data.info.InfoDataModel
import vv.eighteight.w88conthoismash.services.utils.tabs.TabContainer
import java.util.ArrayList

class InfoViewModel : ViewModel() {

    private var privacyList = ArrayList<InfoDataModel>()
    private var privacyInfo = MutableLiveData<List<InfoDataModel>>()
    val trmNf : LiveData<List<InfoDataModel>>
        get() = privacyInfo
    private var privacyError = CoroutineExceptionHandler { _, _ ->
        privacyInfo.postValue(listOfNotNull())
    }

    fun termiFun(): MutableLiveData<List<InfoDataModel>> {
        viewModelScope.launch(privacyError + Dispatchers.IO) {
            for (n in TabContainer.titleInfo.indices) {
                privacyList.add(InfoDataModel(TabContainer.titleInfo[n], TabContainer.titleDescription[n]))
            }
            privacyInfo.postValue(privacyList)
        }
        return privacyInfo
    }
}