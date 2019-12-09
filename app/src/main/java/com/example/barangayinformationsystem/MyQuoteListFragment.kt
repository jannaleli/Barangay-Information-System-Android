package com.example.barangayinformationsystem
import android.Manifest
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri

import android.os.Environment
import android.provider.Contacts.SettingsColumns.KEY
import android.provider.MediaStore

import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URI
import java.nio.file.Files.createFile
import java.util.*


import android.content.ContentResolver

import android.content.pm.PackageManager

import android.graphics.BitmapFactory
import android.text.TextUtils

import android.webkit.MimeTypeMap
import android.widget.EditText


import androidx.core.app.ActivityCompat

import com.amazonaws.ClientConfiguration
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions


import com.amazonaws.util.IOUtils

import java.io.InputStream
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
//import com.google.android.gms.internal.zzagr.runOnUiThread
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
import com.ramotion.foldingcell.FoldingCell


import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.content_launcher.view.*
import kotlinx.android.synthetic.main.fragment_myquotelist.view.*
import kotlinx.android.synthetic.main.fragment_myquotelist_list.*
import kotlinx.android.synthetic.main.fragment_myquotelist_list.*
import kotlinx.android.synthetic.main.fragment_myquotelist.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [MyQuoteListFragment.OnListFragmentInteractionListener] interface.
 */

public interface eventCallback {
    fun onSuccess(clearance: Array<EventModel>)
    fun onError(err: String)
}


public interface picCallback {
    fun onSuccess(image:Bitmap)
    fun onError(err: String)
}
class MyQuoteListFragment : Fragment() {
    private var fileUri: Uri? = null
    private var fileName: String? = null
    val dispatchGroup = DispatchGroup()
    companion object {

        @JvmStatic
        fun newInstance() =
            MyQuoteListFragment().apply {
                arguments = Bundle().apply {
                    // putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    private lateinit var adapter: MyQuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            // columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val rootView = inflater.inflate(R.layout.fragment_myquotelist_list, container, false)
        var list = rootView.findViewById(R.id.list) as RecyclerView // Add this
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = MyQuoteAdapter()


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Thread(Runnable {
            // Do network action in this function
            val callback = object : eventCallback {
                override fun onSuccess(clearance: Array<EventModel>) {

                    //var list = findViewById(R.id.listRec) as RecyclerView // Add this
                    //list.layoutManager = LinearLayoutManager(this@MyQuoteListFragment)

                    //list.adapter = CheckDocuments.MyStatusAdapter()
                    dispatchGroup.enter()
                    var itemsClearance: MutableList<Quote>  = arrayListOf()
                    var i = 0;
                    for(each in clearance) {

                        val callback = object : picCallback {
                            override fun onSuccess(pic: Bitmap) {
                                // no errors
                                //display success message

                                i = i + 1


                                var stats = Quote(each.event_desc, each.event_title, pic, each.start_date)


                                itemsClearance.add(stats)

                                if(clearance.count() == i) {
                                    dispatchGroup.leave()
                                }
                            }



                            override fun onError(err: String) {
                                // error happen
                                println(err)
                            }
                        }

                        downloadFile(each.attachment_id, callback)
                    }

//                    val items = listOf(
//                        Quote("Medical and Dental Mission in cooperation with Charoen Pokphand Foods Philippines Cooperation to be held at the National Stadium. Calling for volunteers.", "Medical and Dental Mission 2019", R.drawable.basket),
//                        Quote("As per DILG Memorandum 2019-72 regarding the Guidelines on Accreditation of Civil Society Organizations (CSO) and Selection of Representatives to the Local Special Bodies, the municipality is now conducting an inventory of all CSOs in Gerona.\n" +
//                                "\n" +
//                                "Representative of each organization in the municipality are advised to coordinate with the Office of the Municipal Planning and Development Office (MPDC) for inclusion in the directory and/or updating of your respective organizational profile.\n" +
//                                "\n" +
//                                "Conduct of inventory is on-going until July 12, 2019.", "Inauguration  of permanent evacuation center at Barangay Loma De Gato ", R.drawable.beautypageant),
//                        Quote("In full support to good governance, accountability and transparency; the Liga ng mga Barangay (LnB) of Libungan Municipality is implementing a computerized Barangay Accounting System (BAS) since 2008 for all its barangay transactions that automatically generates all NGAS reports and other accounting requirements as prescribed by the Commission on Audit (CoA).\n" +
//                                "\n", "New Barangay Accounting System Announcement", R.drawable.dogvaccine),
//                        Quote("For those who are NOT included in the list, you may coordinate with the Office of the Municipal Planning and Development Coordinator (MPDC) for inclusion in the directory UNTIL TOMORROW (JULY 19, 2019).\n" +
//                                "\n" +
//                                "For those who are included in the list, you are encouraged to complete/verify your organization’s information also UNTIL TOMORROW (JULY 19, 2019).\n" +
//                                "\n" +
//                                "Thank you very much.\n" +
//                                "\n", "Attention to all Civil Society Organization in Barangay Loma De Gato", R.drawable.healthcheckup)
//                    )
                    dispatchGroup.notify {
                        // Some code to run after all dispatch groups complete
                        runOnUiThread(Runnable {
                            //stuff that updates ui
                            adapter = MyQuoteAdapter()
                            adapter.replaceItems(itemsClearance)
                            list.adapter = adapter
                        })
                    }


                }



                override fun onError(err: String) {
                    // error happen
                    println(err)
                }
            }
            APICalls.getEvents(callback)
        }).start()

        //var list = view!!.findViewById<RecyclerView>(R.id.list)

        //view!!.findViewById<RecyclerView>(R.id.list).adapter = adapter
    }

    private fun getFileExtension(uri: Uri): String? {
        val contentResolver = context!!.contentResolver
        val mime = MimeTypeMap.getSingleton()

        return mime.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    private fun downloadFile(fileName: String, callback: picCallback) {
        val localFile = File.createTempFile("images", "jpg"/*getFileExtension(fileUri!!)*/);

        val credentials = BasicAWSCredentials("", "")
        var clientConfiguration: ClientConfiguration = ClientConfiguration()
        clientConfiguration.setMaxErrorRetry(0);
        clientConfiguration.setConnectionTimeout(3600000);
        clientConfiguration.setSocketTimeout(3600000);
        val s3Client = AmazonS3Client(credentials,clientConfiguration)
        // Log.d("lol", AWSMobileClient.getInstance().configuration.toString())

        var regions: Region = Region.getRegion(Regions.AP_SOUTHEAST_1)
        //Region.getRegion(.Region)

        s3Client.setRegion(regions)

        val transferUtility = TransferUtility.builder()
            .context(this.context)
            .defaultBucket("barangay-api")
            //.awsConfiguration(AWSMobileClient.getInstance().configuration)
            .s3Client(s3Client)
            .build()
        val downloadObserver: TransferObserver =     transferUtility.download(fileName, localFile);
        downloadObserver.setTransferListener(object : TransferListener {

            override fun onStateChanged(id: Int, state: TransferState) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed download.
                    Log.d("lol", "successful")
                    if (localFile.exists()) {
                        val bitmap: Bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        callback.onSuccess(bitmap)

                    }


                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val percentDonef = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
                val percentDone = percentDonef.toInt()
            }

            override fun onError(id: Int, ex: Exception) {
                // Handle errors
            }

        })
    }

    class MyQuoteAdapter : RecyclerView.Adapter<MyQuoteAdapter.ViewHolder>() {
        private var items = listOf<Quote>()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {



            val rootView =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_myquotelist, parent, false)
//            rootView.findViewById<TextView>(R.id.contentTextView).text = "Hello, world."
//            rootView.findViewById<TextView>(R.id.sourceTextView).text = "Hello, world Source."
            val textView = rootView.findViewById<TextView>(R.id.contentTextView)
//            textView.setMovementMethod(ScrollingMovementMethod())
            val sourceTextView = rootView.findViewById<TextView>(R.id.sourceOneTextView)
            val dateView = rootView.findViewById<TextView>(R.id.publishDate)
       //     sourceTextView.setMovementMethod(ScrollingMovementMethod())
           // var fc = rootView.findViewById(R.id.folding_cell) as FoldingCell // Add this

//            fc.setOnClickListener {
//                    view ->
//
//                //fc.toggle(false)
//
//
//                val context = view.context
//                val intent = Intent(context, EventDetail::class.java)
//
//                context.startActivity(intent)
//                //  fc.toggle(false)
//
//
//
//            }
//            var list = rootView.findViewById(R.id.list) as RecyclerView // Add this
//            list.layoutManager = LinearLayoutManager(activity)
//            list.adapter = MyQuoteAdapter()






            return ViewHolder(rootView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
           // holder.containerView.contentTextView.text = item.content
          // holder.containerView.sourceTextView.text = item.source
          //        holder.containerView.imageContentView.setImageBitmap(item.image)
            holder.containerView.sourceOneTextView.text = item.source

                holder.containerView.profile_image.setImageBitmap(item.image)

             holder.containerView.publishDate.text = item.date

            holder.itemView.setOnClickListener {
                    view ->

                try {
                    //fc.toggle(false)
                    val item = items[holder.adapterPosition]



                    //  fc.toggle(false)

                    val filename: String = "temporary.png"
                    val stream: ByteArrayOutputStream = ByteArrayOutputStream()
                    val bmp = item.image
                    bmp!!.compress(Bitmap.CompressFormat.PNG, 100, stream)

                    val byteArray: ByteArray = stream.toByteArray()


                    //Cleanup
                   // stream.close();
                   //  bmp.recycle();

                     //Pop intent
                    val context = view.context
                    val intent = Intent(context, EventDetail::class.java)
                    intent.putExtra("title", item.source)
                    intent.putExtra("content", item.content)
                    intent.putExtra("image", byteArray)
                    context.startActivity(intent)

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace();
                }





            }
        }

        fun replaceItems(items: List<Quote>) {
            this.items = items
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = items.size

        inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
    }

}
