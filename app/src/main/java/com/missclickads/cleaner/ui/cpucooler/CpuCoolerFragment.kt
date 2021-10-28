package com.missclickads.cleaner.ui.cpucooler

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.imageview.ShapeableImageView
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.CompleteDialogFragmentBinding
import com.missclickads.cleaner.databinding.FragmentCpuCoolerBinding
import com.missclickads.cleaner.models.OptimizeType

import com.missclickads.cleaner.utils.OptimizeDataSaver
import com.missclickads.cleaner.utils.PhoneData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CpuCoolerFragment : BaseFragment<CpuCoolerViewModel>() {

    override val viewModel : CpuCoolerViewModel by viewModels()
    private var _binding: FragmentCpuCoolerBinding? = null
    private val phoneData : PhoneData by inject()
    private val optimizeDataSaver : OptimizeDataSaver by inject()
    private val binding get() = _binding!!
    var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCpuCoolerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    @SuppressLint("SetTextI18n")
    private fun initUi(){
        //ads
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        loadAds(adRequest)
        if (optimizeDataSaver.dataSaver.cpuCooler) viewModel.endOptimization()

        val appImageViewList = listOf(binding.appImage1,binding.appImage2,binding.appImage3,binding.appImage4,
            binding.appImage5,binding.appImage6,)
        val appMemoriesTextView = listOf(binding.appMb1,binding.appMb2,binding.appMb3,
            binding.appMb4,binding.appMb5,binding.appMb6)
        val appImages = phoneData.getAppImages()
        for(i in appImageViewList.indices){
            appImageViewList[i].setImageDrawable(appImages[i % appImages.size])
        }
        for (i in appMemoriesTextView.indices){
            appMemoriesTextView[i].text = "${phoneData.appMemories[i]} mb"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun notOptimized() {
        Log.e("CpuCooler", "notOptimized")

        binding.shapeableImageView.setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_cpu_high))
        binding.temperature.text = "${phoneData.cpuBeforeOpt}"
        binding.status.text = getString(R.string.overheated)
        binding.optimizeBtn.text = getString(R.string.optimize_btn)
        binding.optimizeBtn.setOnClickListener {
            viewModel.startOptimization()
        }
        binding.applicationsClass.text = getString(
            R.string.applications_class_are_causing_problem_hit_cool_down)
        binding.appsLinear.visibility = View.VISIBLE

    }

    override fun optimization() {
        Log.e("CpuCooler", "optimization")
        val dialog = CpuOptimizationDialogFragment{
            viewModel.endOptimization()
            showAd()
        }
        dialog.show(childFragmentManager, "optimization")

    }

    override fun optimized() {
        Log.e("CpuCooler", "optimized")

        optimizeDataSaver.saveOptimization(type = OptimizeType.CPU_COOLER)

        binding.shapeableImageView.setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_cpu_low))
        binding.temperature.text = "${phoneData.cpuAfterOpt}"
        binding.status.text = getString(R.string.normal)
        binding.optimizeBtn.text = getString(R.string.optimized_btn)
        binding.optimizeBtn.setOnClickListener {
            showToast(fragmentName = getString(R.string.cpu_cooler))
        }
        binding.applicationsClass.text = getString(
            R.string.cpu_good)
        binding.appsLinear.visibility = View.INVISIBLE
    }

    override fun error() {
        Log.e("CpuCooler", "error")
    }

    private fun showAd(){
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity as MainActivity)
            println("Ads go!")
        }
        else{
            findNavController().navigate(R.id.optimizationEndsFragment)
        }
    }

    private fun loadAds(adRequest: AdRequest){
        //ads
        InterstitialAd.load(
            activity as MainActivity,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(ContentValues.TAG, adError?.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(ContentValues.TAG, "Ad was loaded")
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                Log.d(ContentValues.TAG, "Ad was dismissed.")
                                findNavController().navigate(R.id.optimizationEndsFragment)
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                                Log.d(ContentValues.TAG, "Ad failed to show.")
                                findNavController().navigate(R.id.optimizationEndsFragment)
                            }


                            override fun onAdShowedFullScreenContent() {
                                findNavController().navigate(R.id.optimizationEndsFragment)
                                Log.d(ContentValues.TAG, "Ad showed fullscreen content.")
                                mInterstitialAd = null
                            }
                        }
                }
            })
    }
}