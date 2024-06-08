package com.anehta.camela.feature.preview

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.anehta.camela.R
import com.anehta.camela.databinding.FragmentPreviewBinding
import com.anehta.camela.feature.preview.viewmodel.PreviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewFragment : Fragment() {

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<PreviewViewModel>()
    private lateinit var surfaceHolder: SurfaceHolder

    companion object {
        const val TAG = "DEBUG PREVIEW"
    }

    //private val CAMERA_PERMISSION_REQUEST_CODE = 10
    private val requiredPermission = mutableListOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    ).apply {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }.toTypedArray()

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        viewModel.setPermissionStatus(allGranted)
        Log.d("PERMISSION DEBUG", "${permissions.keys} = ${permissions.values}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        surfaceHolder = binding.surface.holder

        viewModel.permissionRequest.observe(viewLifecycleOwner) { requsetModel ->
            if (requsetModel.isGranted) {
                Toast.makeText(context, R.string.granted, Toast.LENGTH_SHORT).show()
                if (surfaceHolder.surface.isValid) {
                    startCamera(surfaceHolder)
                }
            } else {
                requestPermission.launch(requiredPermission)
            }
        }

        surfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.d(TAG, "surfaceCreated")
                if (viewModel.permissionRequest.value?.isGranted == true) {
                    startCamera(holder)
                    Log.d(TAG, "startCamera(holder)")
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                Log.d(TAG, "surfaceChanged")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                Log.d(TAG, "surfaceChanged")
            }
        })
    }

    private fun startCamera(surfaceHolder: SurfaceHolder) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build().also {
                    it.setSurfaceProvider { request ->
                        if (surfaceHolder.surface.isValid) {
                            Log.d(TAG, "SurfaceHolder valid")
                        } else {
                            Log.d(TAG, "SurfaceHolder is not valid")
                        }

                        try {
                            request.provideSurface(
                                surfaceHolder.surface,
                                ContextCompat.getMainExecutor(requireContext())
                            ) { result ->
                                Log.d(TAG, "Surface provided ${result.resultCode}")
                                when (result.resultCode) {
                                    SurfaceRequest.Result.RESULT_SURFACE_USED_SUCCESSFULLY -> {
                                        Log.d(
                                            TAG,
                                            "Surface provided successfully: ${result.resultCode}"
                                        )
                                    }

                                    else -> {
                                        Log.d(
                                            TAG,
                                            "Surface provided with error: ${result.resultCode}"
                                        )
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Error providing surface", e)
                        }
                    }
                }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch (exc: Exception) {
                exc.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}