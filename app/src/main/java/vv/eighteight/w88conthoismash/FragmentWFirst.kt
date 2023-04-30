package vv.eighteight.w88conthoismash

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import okhttp3.OkHttpClient
import okhttp3.Request
import vv.eighteight.w88conthoismash.databinding.FragmentFirstBinding
import vv.eighteight.w88conthoismash.services.NetworkConnection
import vv.eighteight.w88conthoismash.view.app.AppViewModel

class FragmentWFirst : Fragment(), View.OnClickListener {

    private var _firstBinding : FragmentFirstBinding? = null
    private val binding get() = _firstBinding!!

    private val args = Bundle()
    private lateinit var viewModel: AppViewModel
    private var checkInternetConnection = NetworkConnection()
    private var checknet = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        _firstBinding = FragmentFirstBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this)[AppViewModel::class.java]
        connectionCheck()
        return binding.root
    }

    private fun connectionCheck() {
        checknet = checkInternetConnection.connectionError(requireActivity())
        if (checknet) {
            viewModel.initJson()
            buttonCode()
        } else {
            Toast.makeText(context, "PLEASE CHECK YOUR INTERNET CONNECTION!!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun onClickMain(){
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        /*binding.btn3.setOnClickListener {
            val intent = Intent(context, SodoGameActivity::class.java)
            startActivity(intent)
        }*/
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun buttonCode() {
        onClickMain()
        viewModel.dataListModel.observe(viewLifecycleOwner) {
            it.let {
                if (it != null) {
                    for (i in it.indices) {
                        val packageName = it[i].APPW88PACKAGE
                        val webView = it[i].APPW88URL
                        val jumpCode = it[i].APPW88URLSTATUS
                        if (packageName == context?.packageName) {
                            Log.e(ContentValues.TAG, packageName.toString())
                            when (jumpCode) {
                                true -> {
                                    binding.b1.text = it[i].APPW88REGISTER
                                    binding.b2.text = it[i].APPW88LOGIN
                                    args.putBoolean("code", true)
                                    args.putBoolean("title",true)
                                    args.putString("urlview", webView)
                                }
                                else -> {
                                    binding.b1.text = getString(R.string.b1)
                                    binding.b2.text = getString(R.string.b2)
                                    args.putBoolean("code", false)
                                    args.putBoolean("title",false)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btn1 -> findNavController().navigate(R.id.action_FirstFragment_to_InfoFragment, args)
            binding.btn2 -> findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, args)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _firstBinding = null
    }
}