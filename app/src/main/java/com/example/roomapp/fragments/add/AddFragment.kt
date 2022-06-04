package com.example.roomapp.fragments.add

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.roomapp.MainActivity
import com.example.roomapp.R
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.util.*


class AddFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        view.buttonLocation.setOnClickListener {
            // wykonuje sie get location i wpisuje w textview
           getLocation()
           // editTextLocation.setText("")
        }
        return view
    }



    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if ((activity as MainActivity).checkPermissions()) {
            if ((activity as MainActivity).isLocationEnabled()) {
                (activity as MainActivity).mFusedLocationClient.lastLocation.addOnCompleteListener(activity as MainActivity) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder((activity as MainActivity), Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        /*mainBinding.apply {
                            tvLatitude.text = "Latitude\n${list[0].latitude}"
                            tvLongitude.text = "Longitude\n${list[0].longitude}"
                            tvCountryName.text = "Country Name\n${list[0].countryName}"
                            tvLocality.text = "Locality\n${list[0].locality}"
                            tvAddress.text = "Address\n${list[0].getAddressLine(0)}"
                        }*/
                        editTextLocation.setText("Latitude\n${list[0].latitude}")
                    }
                }
            } else {
                Toast.makeText((activity as MainActivity), "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            (activity as MainActivity).requestPermissions()
        }
    }

}