package com.missclickads.cleaner.ui.batteryoptimizer

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.SplashActivity
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentBatteryOptimizerBinding
import com.missclickads.cleaner.models.OptimizeType
import com.missclickads.cleaner.ui.optimazed.CompleteOptimizationDialogFragment
import com.missclickads.cleaner.utils.OptimizeDataSaver
import com.missclickads.cleaner.utils.PhoneData
import org.koin.android.ext.android.inject

const val FROM = "from"

class BatteryOptimizerFragment : BaseFragment<BatteryOptimizerViewModel>() {

    private var fromOptScreen : Boolean = false

    override val viewModel : BatteryOptimizerViewModel by viewModels()
    private var _binding: FragmentBatteryOptimizerBinding? = null
    private val phoneData : PhoneData by inject()
    private val optimizeDataSaver : OptimizeDataSaver by inject()
    private val binding get() = _binding!!
    var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatteryOptimizerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fromOptScreen = arguments?.getBoolean(FROM) == true
        initUi()
    }

    @SuppressLint("SetTextI18n")
    private fun initUi(){
        if (optimizeDataSaver.dataSaver.batteryOptimizer) viewModel.endOptimization()
        if (fromOptScreen) viewModel.startOptimization()
        val batteryValue = phoneData.getBatteryValue()
        binding.batteryInfo.text = "${batteryValue[0]} %"
        if (batteryValue[0] <= 20)
            binding.imageBattery.setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_battery_red))
        if (batteryValue[0] in 21..50)
            binding.imageBattery.setImageDrawable(
                ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_battery_yellow))
        if (batteryValue[0] > 50)
            binding.imageBattery.setImageDrawable(
                ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_battery_green))
        binding.batteryTimeInfoHours.text = batteryValue[1].toString()
        binding.batteryTimeInfoMinutes.text = batteryValue[2].toString()
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
        Log.e("BatteryOptimizer", "notOptimized")

        binding.optimizeBtn.text = getString(R.string.optimize_btn)
        binding.optimizeBtn.setOnClickListener {
            viewModel.startOptimization()
        }
    }

    override fun optimization() {
        Log.e("BatteryOptimizer", "optimization")
        val dialog = BatteryOptimizationDialogFragment{
            viewModel.endOptimization()
            showAd()
        }
        dialog.show(childFragmentManager, "optimization")

    }

    override fun optimized() {
        Log.e("BatteryOptimizer", "optimized")

        optimizeDataSaver.saveOptimization(type = OptimizeType.BATTERY_OPTIMIZER)

        binding.optimizeBtn.text = getString(R.string.optimized_btn)
        binding.optimizeBtn.setOnClickListener {
            showToast(fragmentName = getString(R.string.battery_optimizer))
        }
    }

    override fun error() {
        Log.e("BatteryOptimizer", "error")
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