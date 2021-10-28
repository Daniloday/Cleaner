package com.missclickads.cleaner.ui.phonebooster

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.missclickads.cleaner.App
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentPhoneBoosterBinding
import com.missclickads.cleaner.models.OptimizeType
import com.missclickads.cleaner.utils.OptimizeDataSaver
import com.missclickads.cleaner.utils.PhoneData
import org.koin.android.ext.android.inject


class PhoneBoosterFragment : BaseFragment<PhoneBoosterViewModel>() {

    override val viewModel : PhoneBoosterViewModel by viewModels()
    private var _binding: FragmentPhoneBoosterBinding? = null
    private val phoneData : PhoneData by inject()
    private val optimizeDataSaver : OptimizeDataSaver by inject()
    private val binding get() = _binding!!
    var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhoneBoosterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi(){
        if (optimizeDataSaver.dataSaver.phoneBooster) viewModel.endOptimization()
        val memory = phoneData.getMemory()
        val storage = phoneData.getStorage()
        binding.storage.text = storage.first.toString()
        binding.storage2.text = storage.first.toString()
        binding.storageMb.text = "${storage.first} MB / ${storage.second} GB"
        binding.ram.text = "${memory[2]}%"
        binding.ramMb.text = "${memory[0]} MB / ${memory[1]} GB"
        binding.progressBarCircle.progress = memory[2] as Int
        //ads
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        loadAds(adRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun notOptimized() {
        Log.e("PhoneBooster", "notOptimized")
        binding.progressBarCircle.progressDrawable = ContextCompat.getDrawable(activity as MainActivity,
            R.drawable.progress_bar_circle_red)
        binding.optimizeBtn.text = getString(R.string.optimize_btn)
        binding.optimizeBtn.setOnClickListener {
            viewModel.startOptimization()
        }
    }

    override fun optimization() {
        val dialog = PhoneOptimizationDialogFragment{
            viewModel.endOptimization()
            showAd()
        }
        dialog.show(childFragmentManager, "optimization")

        Log.e("PhoneBooster", "optimization")
    }

    override fun optimized() {
        Log.e("PhoneBooster", "optimized")
        optimizeDataSaver.saveOptimization(type = OptimizeType.PHONE_BOOSTER)
        binding.progressBarCircle.progressDrawable = ContextCompat.getDrawable(activity as MainActivity,
            R.drawable.progress_bar_circle_blue)
        binding.optimizeBtn.text = getString(R.string.optimized_btn)
        binding.optimizeBtn.setOnClickListener {
            showToast(fragmentName = getString(R.string.phone_booster))
        }
    }

    override fun error() {
        Log.e("PhoneBooster", "error")
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