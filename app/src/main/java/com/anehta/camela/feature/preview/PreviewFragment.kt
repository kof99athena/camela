package com.anehta.camela.feature.preview

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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

    private val CAMERA_PERMISSION_REQUEST_CODE = 10
    private val REQUIRED_PERMISSIONS = mutableListOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    ).apply {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }.toTypedArray()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
        Log.d("CAMERA ACCESS", "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("CAMERA ACCESS", "onViewCreated")

        viewModel.permissionRequest.observe(viewLifecycleOwner, Observer { requsetModel ->
            if (requsetModel.isGranted) {
                Toast.makeText(context, R.string.granted, Toast.LENGTH_SHORT).show()
                startCamera()
            } else {
                requestPermissions(REQUIRED_PERMISSIONS, CAMERA_PERMISSION_REQUEST_CODE)
            }
        })

        Log.d("CAMERA ACCESS", "permission Granted")

        // Check permissions and set initial status
        if (allPermissionsGranted()) {
            viewModel.setPermissionStatus(isGranted = true)
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, CAMERA_PERMISSION_REQUEST_CODE)
        }


        // add surfaceView holder
        surfaceHolder = binding.surface.holder
        surfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                if (viewModel.permissionRequest.value?.isGranted == true) startCamera()
                Log.d("CAMERA ACCESS", "surfaceCreated")
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

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider { request ->
                    request.provideSurface(
                        surfaceHolder.surface,
                        ContextCompat.getMainExecutor(requireContext())
                    ) { result ->
                        // Handle surface request result if needed
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
        Log.d("CAMERA ACCESS", "startCamera()")
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        context?.let { ctx ->
            ContextCompat.checkSelfPermission(ctx, it)
        } == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED })) {
                viewModel.setPermissionStatus(isGranted = true)
            } else {
                viewModel.setPermissionStatus(isGranted = false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}