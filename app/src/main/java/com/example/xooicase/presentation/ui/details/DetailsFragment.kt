package com.example.xooicase.presentation.ui.details

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.xooicase.R
import com.example.xooicase.common.AlertHelper
import com.example.xooicase.common.Constants
import com.example.xooicase.common.PermissionHelper
import com.example.xooicase.common.UIHelper
import com.example.xooicase.databinding.DetailsFragmentBinding
import com.example.xooicase.domain.model.DetailsObservableModel
import com.example.xooicase.presentation.ui.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailsFragment : Fragment(), View.OnClickListener, OnMapReadyCallback {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var binding: DetailsFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mapView: MapView
    private var gMap: GoogleMap? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var isFromBackStack = false
    private lateinit var detailObservableModel: DetailsObservableModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            // observable data
            detailObservableModel = this.getSerializable(Constants.DETAILS_KEY) as DetailsObservableModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)

        // data binding
        binding.details = detailObservableModel
        binding.executePendingBindings()

        // map permission
        checkLocationPermission()

        //mapView
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // click listeners
        binding.cardViewWaybill.setOnClickListener(this)
        binding.imgBtnClose.setOnClickListener(this)
        binding.btnUploadPicture.setOnClickListener(this)


        if (!detailObservableModel.loadImages.isNullOrEmpty()) {
            val adapter = TakenPhotoRecyclerViewAdapter(detailObservableModel.loadImages)
            binding.rvTakenPhotos.adapter = adapter
            adapter.notifyDataSetChanged()
        }

    }

    private fun checkLocationPermission() {
        if (!PermissionHelper.getLocationPermissionGranted(requireContext())) {
            PermissionHelper.requestLocationPermission(this)
        } else {
            // permission okay
            getDeviceLocation()
        }
    }

    // result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constants.LOCATION_REQUEST_KEY) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // OK
                getDeviceLocation()
            } else {
                // if select "do not ask again"
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertHelper.createAlertDialogForInfo(requireContext(), resources.getString(R.string.map_permission_settings_alert), "Tamam")
                } else {
                    // if select "do not ask again" second time
                    AlertHelper.createAlertDialogWithConfirm(
                        requireContext(), resources.getString(R.string.map_permission_settings_alert), "Tamam"
                    ) {
                        PermissionHelper.openAppSettingsPage(requireActivity(), requireContext())
                    }
                }
            }

        }

    }

    // click listeners
    override fun onClick(view: View?) {
        when (view?.id) {
            binding.imgBtnClose.id -> {
                binding.frmLayoutZoomImage.visibility = View.GONE
                (activity as AppCompatActivity?)!!.supportActionBar?.let { UIHelper.showSystemUI(requireActivity().window, it) }

            }

            binding.cardViewWaybill.id -> {
                binding.frmLayoutZoomImage.visibility = View.VISIBLE
                (activity as AppCompatActivity?)!!.supportActionBar?.let { UIHelper.hideSystemUI(requireActivity().window, it) }

            }

            binding.btnUploadPicture.id -> {
                isFromBackStack = true
                val bundle = Bundle()
                bundle.putSerializable(Constants.DETAILS_KEY, detailObservableModel)
                findNavController().navigate(R.id.action_detailsFragment_to_loadImageFragment, bundle)
            }
        }

    }

    // map ready
    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap?.uiSettings?.setAllGesturesEnabled(false);
        getDeviceLocation()
    }

    // device location process
    @SuppressLint("MissingPermission") // already we checked
    private fun getDeviceLocation() {
        if (PermissionHelper.getLocationPermissionGranted(requireContext())) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result != null) {

                        setAddressDetails(LatLng(task.result.latitude, task.result.longitude))

                        gMap?.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(task.result.latitude, task.result.longitude), 16.0f
                            )
                        )

                        gMap?.addMarker(
                            MarkerOptions()
                                .position(LatLng(task.result.latitude, task.result.longitude))
                                .title("Konumunuz")
                        )
                    }
                }
            }
        }
    }

    // set address
    private fun setAddressDetails(latLng: LatLng) {
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses != null && addresses.size > 0) {
                val address = addresses[0]
                val country = address.countryName
                val city = address.adminArea
                val district = address.subAdminArea
                val street = address.thoroughfare
                val neightborhood = address.subLocality
                binding.tvCountry.text = ("$city / $country")
                binding.tvDistrict.text = district
                if (street != null && street.isNotEmpty()) {
                    binding.tvStreet.text = street
                } else binding.tvStreet.text = neightborhood
            }

        } catch (e: Exception) {
            Log.d("exception", e.localizedMessage)
        }
    }

    override fun onResume() {
        super.onResume()

        if (isFromBackStack) {
            getDeviceLocation()
            isFromBackStack = false
        }
    }

}