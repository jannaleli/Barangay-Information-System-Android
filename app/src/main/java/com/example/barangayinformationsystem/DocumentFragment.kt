package com.example.barangayinformationsystem

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_launcher.*
import kotlinx.android.synthetic.main.fragment_document.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DocumentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DocumentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DocumentFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_document, container, false)
        var clearanceButton = rootView.findViewById(R.id.clearanceButton) as Button // Add this
        var permitButton = rootView.findViewById(R.id.permitButton) as Button // Add this
        var statusButton = rootView.findViewById(R.id.statusButton) as Button // Add this
        clearanceButton.setOnClickListener {
                view ->

            val intent = Intent(getActivity(), ClearanceApplication::class.java)
            // start your next activity
            startActivity(intent)

        }
        permitButton.setOnClickListener {
                view ->

            val intent = Intent(getActivity(), BusinessPermitApplication::class.java)
            // start your next activity
            startActivity(intent)

        }
        statusButton.setOnClickListener {
                view ->

            val intent = Intent(getActivity(), CheckDocuments::class.java)
            // start your next activity
            startActivity(intent)

        }










        return rootView
    }


}
