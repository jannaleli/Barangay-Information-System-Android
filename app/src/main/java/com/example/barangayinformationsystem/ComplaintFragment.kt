package com.example.barangayinformationsystem


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
//import com.google.android.gms.internal.zzagr.runOnUiThread
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.content_launcher.*
import kotlinx.android.synthetic.main.fragment_document.*


import kotlinx.android.synthetic.main.fragment_complaint.*
import kotlinx.android.synthetic.main.fragment_complaint.view.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import kotlinx.android.synthetic.main.fragment_myquotelist_list.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

public interface complaintCallback {
    fun onSuccess(clearance: Array<ComplaintModel>)
    fun onError(err: String)
}
class ComplaintFragment : Fragment(), OnMapReadyCallback {

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_complaint, container, false)
//    }

//


    private var googleMap: GoogleMap? = null
    private var mapView: MapView? = null
    private var mapsSupported = true
    var mapFragment : SupportMapFragment?=null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onActivityCreated(savedInstanceState)
        try {
            MapsInitializer.initialize(activity!!)
        } catch (e: GooglePlayServicesNotAvailableException) {
            mapsSupported = false
        }

        if (mapView != null) {
            mapView!!.onCreate(savedInstanceState)
        }
        initializeMap()
    }

    private fun initializeMap() {
        if (googleMap == null && mapsSupported) {
            var map = activity!!.findViewById<View>(R.id.map) as MapView

            map.getMapAsync(this)
            //setup markers etc...
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {





        val parent =   inflater.inflate(R.layout.fragment_complaint, container, false)       //inflater.inflate(R.layout.f, container, false) as LinearLayout
        mapView = parent.findViewById<View>(R.id.map) as MapView

        var complaintButton = parent.findViewById<Button>(R.id.complaintButton) as Button

        complaintButton.setOnClickListener {
                view ->


            val intent = Intent(getActivity(), PostComplaint::class.java)
            // start your next activity
            startActivity(intent)
        }
        return parent
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
        initializeMap()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap) {

        Thread(Runnable {
            // Do network action in this function

            val complaintCallback = object : complaintCallback {
                override fun onSuccess(clearance: Array<ComplaintModel>) {
                    // no errors


                    runOnUiThread(Runnable {
                        googleMap = map
                        var itemsClearance: MutableList<LatLng>  = arrayListOf()

                        for(each in clearance) {

                            if (each.status.toString() == "RESOLVED") {
                                break
                            }
                            val lat = each.latitude.toDouble()
                            val long = each.longitude.toDouble()
                            val location = LatLng(lat, long)

                            itemsClearance.add(location)
                            map.addMarker(
                                MarkerOptions().position(location).title(each.complaint_desc).snippet(each.complaint_desc))
                        }
                        map.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(14.776485, 121.019054),
                                13f
                            )
                        )

                        val cameraPosition = CameraPosition.Builder()
                            .target(
                                LatLng(14.776485, 121.019054)
                            )      // Sets the center of the map to location user
                            .zoom(17f)                   // Sets the zoom
                            .bearing(90f)                // Sets the orientation of the camera to east
                            .tilt(40f)                   // Sets the tilt of the camera to 30 degrees
                            .build()                   // Creates a CameraPosition from the builder
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                    })



                }



                override fun onError(err: String) {
                    // error happen
                    println(err)
                }
            }

        APICalls.getComplaints(complaintCallback)



        }).start()
//        googleMap = map
//
//        val loc1 = LatLng(14.776485, 121.019054)
//        val loc2 = LatLng(14.776801, 121.018801)
//        val loc3 = LatLng(14.77706, 121.01918)
//        val loc4 = LatLng(14.777138, 121.018724)
//        val loc5 = LatLng(14.777138, 121.018724)
//        map.addMarker(
//            MarkerOptions().position(loc1).title("Noise complaint"))
//        map.addMarker(
//            MarkerOptions().position(loc2).title("Noise complaint"))
//        map.addMarker(
//            MarkerOptions().position(loc3).title("Noise complaint"))
//        map.addMarker(
//            MarkerOptions().position(loc4).title("Noise complaint"))
//        map.addMarker(
//            MarkerOptions().position(loc5).title("Noise complaint"))
//        map.moveCamera(CameraUpdateFactory.newLatLng(loc1))
//        map.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(
//                LatLng(14.776485, 121.019054),
//                13f
//            )
//        )
//
//        val cameraPosition = CameraPosition.Builder()
//            .target(
//                LatLng(14.776485, 121.019054)
//            )      // Sets the center of the map to location user
//            .zoom(17f)                   // Sets the zoom
//            .bearing(90f)                // Sets the orientation of the camera to east
//            .tilt(40f)                   // Sets the tilt of the camera to 30 degrees
//            .build()                   // Creates a CameraPosition from the builder
//        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}
