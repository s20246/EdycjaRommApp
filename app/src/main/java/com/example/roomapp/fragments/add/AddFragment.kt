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
import androidx.fragment.app.Fragment
import com.example.roomapp.MainActivity
import com.example.roomapp.R
import com.example.roomapp.data.ProductViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.util.*


class AddFragment : Fragment() {

   // private lateinit var mProductViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

       // mProductViewModel=ViewModelProvider(this).get(ProductViewModel::class.java)

        view.buttonLocation.setOnClickListener {
           getLocation()
        }
        view.takePhoto.setOnClickListener {
            //zrob zdjecie, zapisz do galerii, zapisz scieżkę do text view
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
                        //zmiana textView
                        /*"Latitude\n${list[0].latitude}"
                        "Longitude\n${list[0].longitude}"
                        "Country Name\n${list[0].countryName}"
                        "Locality\n${list[0].locality}"
                        "Address\n${list[0].getAddressLine(0)}"*/
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
    private fun insertDataToDatabase(){

        val name = editTextName.text.toString()
        val location = editTextLocation.text.toString()
        val photoPath = photoPath.text.toString()
    }
}