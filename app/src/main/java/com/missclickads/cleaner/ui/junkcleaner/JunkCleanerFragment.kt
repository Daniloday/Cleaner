package com.missclickads.cleaner.ui.junkcleaner

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import android.util.Log
import androidx.core.content.ContextCompat
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
import com.missclickads.cleaner.databinding.FragmentJunkCleanerBinding
import com.missclickads.cleaner.models.OptimizeType
import com.missclickads.cleaner.ui.batteryoptimizer.FROM
import com.missclickads.cleaner.ui.optimazed.CompleteOptimizationDialogFragment
import com.missclickads.cleaner.utils.OptimizeDataSaver
import org.koin.android.ext.android.inject

class JunkCleanerFragment : BaseFragment<JunkCleanerViewModel>() {

    override val viewModel : JunkCleanerViewModel by viewModels()
    private var _binding: FragmentJunkCleanerBinding? = null
    private val optimizeDataSaver : OptimizeDataSaver by inject()
    private val binding get() = _binding!!
    private var fromOptScreen : Boolean = false
    var mInterstitialAd: InterstitialAd? = null
    private lateinit var circlesList : List<ShapeableImageView>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJunkCleanerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fromOptScreen = arguments?.getBoolean(FROM) == true
        initUi()
    }

    private fun initUi(){
        (activity as MainActivity).back = false
        if (optimizeDataSaver.dataSaver.junkCleaner) viewModel.endOptimization()
        if (fromOptScreen) viewModel.startOptimization()
        circlesList = listOf(binding.imageCircle1, binding.imageCircle2,
            binding.imageCircle3, binding.imageCircle4)
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
        Log.e("JunkCleaner", "notOptimized")

        binding.shapeableImageView.setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity,
                R.drawable.ic_junk_hard))
        binding.optimizeBtn.text = getString(R.string.optimize_btn)
        binding.optimizeBtn.setOnClickListener {
            viewModel.startOptimization()
        }
        circlesList.forEach {
            it.setImageDrawable(ContextCompat.getDrawable(activity as MainActivity,
                R.drawable.ic_red_circle_junk))
        }
    }

    override fun optimization() {
        Log.e("JunkCleaner", "optimization")
        val dialog = JunkOptimizationDialogFragment{
            viewModel.endOptimization()
//            showAd()
        }
        dialog.show(childFragmentManager, "optimization")

    }

    override fun optimized() {
        Log.e("JunkCleaner", "optimized")

        optimizeDataSaver.saveOptimization(type = OptimizeType.JUNK_CLEANER)

        binding.shapeableImageView.setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity,
                R.drawable.ic_junk_cleared))
        binding.optimizeBtn.text = getString(R.string.optimized_btn)
        binding.optimizeBtn.setOnClickListener {
            showToast(fragmentName = getString(R.string.junk_cleaner))
        }
        circlesList.forEach {
            it.setImageDrawable(ContextCompat.getDrawable(activity as MainActivity,
                R.drawable.ic_green_circle_junk))
        }
    }

    override fun error() {
        Log.e("JunkCleaner", "error")
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