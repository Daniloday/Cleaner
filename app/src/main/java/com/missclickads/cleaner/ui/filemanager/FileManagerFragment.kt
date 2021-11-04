package com.missclickads.cleaner.ui.filemanager

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest

import com.missclickads.cleaner.R
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentFileManagerBinding
import com.missclickads.cleaner.utils.PhoneData
import org.koin.android.ext.android.inject

import android.widget.Toast



import android.content.pm.PackageManager
import android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
import android.provider.MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.ui.batteryoptimizer.FROM


class FileManagerFragment : BaseFragment<FileManagerViewModel>() {

    override val viewModel : FileManagerViewModel by viewModels()
    private var _binding: FragmentFileManagerBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileManagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ActivityCompat.requestPermissions(
            activity as MainActivity,
            arrayOf(
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_MEDIA_LOCATION
            ),
            1);

        initUi()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            activity as MainActivity,
                            Manifest.permission.MANAGE_EXTERNAL_STORAGE
                        ) ===
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        Toast.makeText(
                            activity as MainActivity,
                            "Permission Granted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        activity as MainActivity,
                        "Permission Denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }


    private fun initUi(){
        (activity as MainActivity).back = false
        binding.submitBtn.setOnClickListener {
            findNavController().navigate(R.id.fileManagerTypesFragment)
        }
        //ads
        binding.adView.loadAd(AdRequest.Builder().build())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun notOptimized() {
        Log.e("FileManager", "notOptimized")
    }

    override fun optimization() {
        Log.e("FileManager", "optimization")
    }

    override fun optimized() {
        Log.e("FileManager", "optimized")
    }

    override fun error() {
        Log.e("FileManager", "error")
    }
}