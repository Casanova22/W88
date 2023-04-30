package vv.eighteight.w88conthoismash.services.utils.tabs

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vv.eighteight.w88conthoismash.FragmentWSecond
import vv.eighteight.w88conthoismash.data.info.InfoDataModel
import vv.eighteight.w88conthoismash.databinding.InfoViewBinding

class TabAdapter(val listener: FragmentWSecond) : RecyclerView.Adapter<TabAdapter.AdapterHolder>() {

    private var privacyList = emptyList<InfoDataModel>()
    class AdapterHolder (val adpts : InfoViewBinding) : RecyclerView.ViewHolder(adpts.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder = AdapterHolder(
        InfoViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
        with (holder){
            with(privacyList[position]){
                adpts.infoTitle.text  = this.contentTitle
                adpts.infoDesc.text = this.contentDesc

                adpts.infoDesc.ellipsize = TextUtils.TruncateAt.MARQUEE
                adpts.infoDesc.isSelected = true

                adpts.onclickCardview.setOnClickListener {
                    listener.onItemClick(this)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return privacyList.size
    }

    fun setAdapter (setAdapt : List<InfoDataModel>){
        this.privacyList = setAdapt
    }
}