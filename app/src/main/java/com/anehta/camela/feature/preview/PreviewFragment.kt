package com.anehta.camela.feature.preview

import android.Manifest
import android.content.ContentValues
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.anehta.camela.R
import com.anehta.camela.databinding.FragmentPreviewBinding
import com.anehta.camela.feature.preview.viewmodel.PreviewViewModel
import com.anehta.camela.utils.ScreenUtil
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class PreviewFragment : Fragment() {

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<PreviewViewModel>()
    private lateinit var surfaceHolder: SurfaceHolder
    private var imageCapture: ImageCapture? = null

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    //private val CAMERA_PERMISSION_REQUEST_CODE = 10
    private val requiredPermission = mutableListOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preview, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        surfaceHolder = binding.surface.holder

        binding.capture.setOnClickListener {
            takePicture()
        }

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

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

        viewModel.ratio.observe(viewLifecycleOwner) { ratio ->
            setViewRatio(ratio)
        }

        surfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
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
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }
        })
    }

    private fun setViewRatio(ratio: ScreenUtil.Ratio) {
        val layoutParams = binding.surface.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.dimensionRatio = ratio.ratioString
        binding.surface.layoutParams = layoutParams
        binding.surface.requestLayout()
    }

    private fun startCamera(surfaceHolder: SurfaceHolder) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build().also {
                    it.setSurfaceProvider { request ->
                        if (surfaceHolder.surface.isValid) {
                        } else {
                        }

                        try {
                            request.provideSurface(
                                surfaceHolder.surface,
                                ContextCompat.getMainExecutor(requireContext())
                            ) { result ->
                                when (result.resultCode) {
                                    SurfaceRequest.Result.RESULT_SURFACE_USED_SUCCESSFULLY -> {
                                    }

                                    else -> {
                                    }
                                }
                            }
                        } catch (e: Exception) {
                        }
                    }
                }

            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

            } catch (exc: Exception) {
                exc.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePicture() {
        val fileName =
            SimpleDateFormat(FILENAME_FORMAT, Locale.KOREA).format(System.currentTimeMillis())
        val contentValue = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/Camela")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            requireContext().contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValue
        ).build()

        imageCapture?.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val saveUri = outputFileResults.savedUri ?: Uri.EMPTY
                    val msg = "take a photo: $saveUri"
                    Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.i("Camera Capture", "onError: ${exception.message}", exception)
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}