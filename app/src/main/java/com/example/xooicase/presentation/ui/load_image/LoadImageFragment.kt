package com.example.xooicase.presentation.ui.load_image

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.xooicase.R
import com.example.xooicase.common.AlertHelper
import com.example.xooicase.common.Constants
import com.example.xooicase.common.PermissionHelper
import com.example.xooicase.common.UIHelper
import com.example.xooicase.databinding.FragmentLoadImageBinding
import com.example.xooicase.domain.model.DetailsObservableModel
import com.example.xooicase.presentation.interfaces.IOnDeleteItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadImageFragment : Fragment(), View.OnClickListener, IOnDeleteItemClickListener {
    companion object {
        fun newInstance() = LoadImageFragment()
    }

    private lateinit var binding: FragmentLoadImageBinding
    private lateinit var uriContent: Uri
    private lateinit var adapter: PhotoRecyclerViewAdapter
    private lateinit var detailObservableModel: DetailsObservableModel
    private var imageUriStringPathList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar?.let { UIHelper.hideSystemUI(requireActivity().window, it) }
        arguments?.apply {
            // observable data
            detailObservableModel = this.getSerializable(Constants.DETAILS_KEY) as DetailsObservableModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // click listeners
        binding.imgBtnClose.setOnClickListener(this)
        binding.imgvAdd.setOnClickListener(this)
        binding.imgvChange.setOnClickListener(this)
        binding.imgvSend.setOnClickListener(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadImageBinding.inflate(inflater, container, false)

        imageUriStringPathList = detailObservableModel.loadImages
        adapter = PhotoRecyclerViewAdapter(imageUriStringPathList, this)
        binding.imgPhotoRecyclerView.adapter = adapter
        binding.imgPhotoRecyclerView.smoothScrollToPosition(imageUriStringPathList.size)

        return binding.root
    }

    // click listeners
    override fun onClick(view: View?) {
        when (view?.id) {
            binding.imgBtnClose.id -> {
                (activity as AppCompatActivity?)!!.supportActionBar?.let { UIHelper.showSystemUI(requireActivity().window, it) }
                findNavController().popBackStack(R.id.detailsFragment, false)
            }
            binding.imgvChange.id -> {
                if (imageUriStringPathList.isNotEmpty()) {
                    imageUriStringPathList.removeAt(imageUriStringPathList.lastIndex)
                    adapter.notifyDataSetChanged()
                }
                takePhotoFromCameraOrGallery()
            }
            binding.imgvAdd.id -> {
                takePhotoFromCameraOrGallery()
            }

            binding.imgvSend.id -> {
                detailObservableModel.loadImages = imageUriStringPathList
                findNavController().popBackStack(R.id.detailsFragment, false)
            }
        }
    }

    // take photo from gallery or camera
    private fun takePhotoFromCameraOrGallery() {
        if (PermissionHelper.getCameraPermissionGranted(requireContext())) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA)) {
                AlertHelper.createAlertDialogWithConfirm(
                    requireContext(),
                    resources.getString(R.string.camera_permission_info), "Tamam"
                ) {
                    //For API >= 23 we need to check specifically that we have permissions to read external storage,
                    requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE), Constants.CAMERA_REQUEST_KEY)
                }
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), Constants.CAMERA_REQUEST_KEY)
            }

        } else {
            launchCropImageActivity()
        }
    }

    // activity result
    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            uriContent = result.uriContent!!
            imageUriStringPathList.add(uriContent.toString())
            adapter.notifyItemInserted(imageUriStringPathList.size)
            binding.imgPhotoRecyclerView.smoothScrollToPosition(imageUriStringPathList.size);
        }

    }

    // launch crop
    private fun launchCropImageActivity() {
        cropImage.launch(
            options {
                setImageSource(true, true)
                setCropShape(CropImageView.CropShape.RECTANGLE)
                setShowCropOverlay(true)
                setAllowRotation(true)
                setAllowCounterRotation(true)
                setAutoZoomEnabled(true)
                setFixAspectRatio(true)
                setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                setOutputCompressQuality(80)
                setAspectRatio(3, 2)
                setRequestedSize(720, 460)
            }
        )
    }

    // delete item
    override fun onDeleteItemClickListener(position: Int) {
        imageUriStringPathList.remove(imageUriStringPathList[position])
        adapter.notifyDataSetChanged()
        binding.imgPhotoRecyclerView.smoothScrollToPosition(imageUriStringPathList.size)
    }

}