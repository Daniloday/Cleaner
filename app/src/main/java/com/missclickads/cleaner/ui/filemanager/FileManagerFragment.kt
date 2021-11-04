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

//        ActivityCompat.requestPermissions(
//            activity as MainActivity,
//            arrayOf(
//                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.ACCESS_MEDIA_LOCATION
//            ),
//            1);

        initUi()
    }




    private fun initUi(){
        (activity as MainActivity).back = false
        binding.submitBtn.setOnClickListener {
            checkPerm(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
//            ActivityCompat.requestPermissions(
//                activity as MainActivity,
//                arrayOf(
//                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.ACCESS_MEDIA_LOCATION
//                ),
//                1);
           // findNavController().navigate(R.id.fileManagerTypesFragment)
        }
        //ads
        binding.adView.loadAd(AdRequest.Builder().build())

    }

    fun checkPerm(permission: String){
        when {
            ContextCompat.checkSelfPermission(
                requireActivity(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                Log.e("Here", "HERE")
            }
//            shouldShowRequestPermissionRationale(...) -> {
//            // In an educational UI, explain to the user why your app requires this
//            // permission for a specific feature to behave as expected. In this UI,
//            // include a "cancel" or "no thanks" button that allows the user to
//            // continue using your app without granting the permission.
//            showInContextUI(...)
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(arrayOf(
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_MEDIA_LOCATION
                ))
            }
        }
    }



    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissions.forEach{
                Log.e("DEBUG", "${it.key} = ${it.value}")
                if(it.value == true){
                    findNavController().navigate(R.id.fileManagerTypesFragment)
                }
            }
//            }
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

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
//                                            grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            1 -> {
//                if (grantResults.isNotEmpty() && grantResults[0] ==
//                    PackageManager.PERMISSION_GRANTED
//                ) {
//                    if ((ContextCompat.checkSelfPermission(
//                            this,
//                            Manifest.permission.MANAGE_EXTERNAL_STORAGE
//                        ) ===
//                                PackageManager.PERMISSION_GRANTED)
//                    ) {
//                        Toast.makeText(
//                            this,
//                            "Permission Granted",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                } else {
//                    Toast.makeText(
//                        this,
//                        "Permission Denied",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                return
//            }
//        }
//    }