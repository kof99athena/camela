package com.anehta.camela.feature.preview

import android.Manifest
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.anehta.camela.R
import com.anehta.camela.databinding.FragmentPreviewBinding
import com.anehta.camela.feature.preview.viewmodel.PreviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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
                    Log.d(TAG, "startCamera(surfaceHolder)")
                    startCamera(surfaceHolder)
                }
            } else {
                requestPermission.launch(requiredPermission)
            }
        }

        surfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.d("CAMERA ACCESS", "surfaceCreated")
                if (viewModel.permissionRequest.value?.isGranted == true) {
                    startCamera(holder)
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                Log.d("CAMERA ACCESS", "surfaceChanged")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                Log.d("CAMERA ACCESS", "surfaceDestroyed")
            }
        })
    }

    private fun startCamera(surfaceHolder: SurfaceHolder) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            Log.d(TAG, "get cameraProviderFuture intance")

            val screenSize =
                if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) Size(
                    720,
                    1280
                ) else Size(1280, 720)
            val resolutionSelector = ResolutionSelector.Builder().setResolutionStrategy(
                ResolutionStrategy(
                    screenSize,
                    ResolutionStrategy.FALLBACK_RULE_NONE
                )
            ).build()

            Log.d(TAG, "initialized resolutionSelector")

            val preview = Preview.Builder()
                .build().also {
                    it.setSurfaceProvider { request ->
                        Log.d(TAG, "request SurfaceView")

                        if (surfaceHolder.surface.isValid) {
                            Log.d(TAG, "SurfaceHolder valid")
                        } else {
                            Log.d(TAG, "SurfaceHolder is not valid")
                        }

                        val executor = Executors.newSingleThreadScheduledExecutor()
                        val future = executor.schedule({
                            Log.e(TAG, "Surface request timed out")
                        }, 5, TimeUnit.SECONDS)

                        try {
                            request.provideSurface(
                                surfaceHolder.surface,
                                ContextCompat.getMainExecutor(requireContext())
                            ) { result ->
                                future.cancel(false) // 타임아웃 취소
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
                            Log.d(TAG, "binding SurfaceView")
                            Log.d(TAG, "===================")
                        } catch (e: Exception) {
                            future.cancel(false) // 타임아웃 취소
                            Log.e(TAG, "Error providing surface", e)
                        }


//                        request.provideSurface(
//                            surfaceHolder.surface,
//                            ContextCompat.getMainExecutor(requireContext())
//                        ) { result ->
//                            Log.d(TAG, "Surface provided ${result.resultCode}")
//                            when (result.resultCode) {
//                                SurfaceRequest.Result.RESULT_SURFACE_USED_SUCCESSFULLY -> {
//                                    Log.d(
//                                        TAG,
//                                        "Surface provided successfully: ${result.resultCode}"
//                                    )
//                                }
//
//                                else -> {
//                                    Log.d(TAG, "Surface provided with error: ${result.resultCode}")
//                                }
//                            }
//                        }
                        Log.d(TAG, "binding SurfaceView")
                        Log.d(TAG, "===================")
                    }
                }
            Log.d(TAG, "End of Configuration")
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                Log.d(TAG, "Unbound all use cases")
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
                Log.d(TAG, "Bound to lifecycle")

            } catch (exc: Exception) {
                exc.printStackTrace()
                Log.e(TAG, "Failed to bind camera use cases", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}