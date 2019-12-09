package com.example.barangayinformationsystem

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramotion.foldingcell.FoldingCell
import kotlinx.android.extensions.LayoutContainer

import kotlinx.android.synthetic.main.activity_check_documents.*
import kotlinx.android.synthetic.main.fragment_myquotelist.view.*
import kotlinx.android.synthetic.main.fragment_myquotelist_list.*
import kotlinx.android.synthetic.main.fragment_status_holder.view.*
import me.riddhimanadib.formmaster.FormBuilder
import me.riddhimanadib.formmaster.model.*


public interface clearanceCallback {
    fun onSuccess(clearance: Array<ClearanceModel>)
    fun onError(err: String)
}

public interface permitCallback {
    fun onSuccess(clearance: Array<PermitModel>)
    fun onError(err: String)
}

class CheckDocuments : AppCompatActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register_resident)
//
//
//        var mRecyclerView = findViewById(R.id.recyclerView) as RecyclerView
//
//
//
//        val formItems = ArrayList<Any>()
//
//
//
//    }

    var itemsClearance: MutableList<Status>  = arrayListOf()
private lateinit var linearLayoutManager: LinearLayoutManager
    companion object {

        @JvmStatic
        fun newInstance() =
            StatusHolder().apply {
                arguments = Bundle().apply {
                    // putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    private lateinit var adapter: CheckDocuments.MyStatusAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_documents)
        var list = findViewById(R.id.listRec) as RecyclerView // Add this
        list.layoutManager = LinearLayoutManager(this@CheckDocuments)

        list.adapter = CheckDocuments.MyStatusAdapter()
        Thread(Runnable {
            // Do network action in this function
val callback = object : clearanceCallback {
    override fun onSuccess(clearance: Array<ClearanceModel>) {
        // no errors
        println("Done")

      //  var list = findViewById(R.id.listRec) as RecyclerView // Add this
      //  list.layoutManager = LinearLayoutManager(this@CheckDocuments)

      //  list.adapter = CheckDocuments.MyStatusAdapter()

     //   var itemsClearance: MutableList<Status>  = arrayListOf()

            for(each in clearance) {
                var stats = Status()
                if (each.status == "REJECTED") {
                    stats = Status(sourceTitle = each.user_id , date = each.expiration_date, image = R.drawable.rejected)
                }else if(each.status == "APPROVED") {
                    stats = Status(sourceTitle = each.user_id , date = each.expiration_date, image = R.drawable.approved)
                }else{
                    stats = Status(sourceTitle = each.user_id , date = each.expiration_date, image = R.drawable.pending)
                }

            itemsClearance.add(stats)
        }


//        val items = listOf(
//            Status(
//                sourceTitle = "Ref # ABCD1234", date = "07/07/90", image = R.drawable.rejected
//            ),
//            Status(
//                sourceTitle = "Ref # ABCD1234", date = "07/07/90", image = R.drawable.approved
//            ),
//            Status(
//                sourceTitle = "Ref # ABCD1234", date = "07/07/90", image = R.drawable.pending
//            ),
//            Status(
//                sourceTitle = "Ref # ABCD1234", date = "07/07/90", image = R.drawable.approved
//            )
//        )

        runOnUiThread(Runnable {
            //stuff that updates ui
            adapter = CheckDocuments.MyStatusAdapter()
            adapter.replaceItems(itemsClearance)
            list.adapter = adapter
        })
    }



    override fun onError(err: String) {
        // error happen
        println(err)
    }
}

            val permitcallback = object : permitCallback {
                override fun onSuccess(clearance: Array<PermitModel>) {
                    // no errors
                    println("Done")

                    println("Done")

                 //   var list = findViewById(R.id.listRec) as RecyclerView // Add this
                  //  list.layoutManager = LinearLayoutManager(this@CheckDocuments)

                  //  list.adapter = CheckDocuments.MyStatusAdapter()

                  //  var itemsClearance: MutableList<Status>  = arrayListOf()

                    for(each in clearance) {
                        var stats = Status()
                        if (each.status == "REJECTED") {
                            stats = Status(sourceTitle = each.user_id , date = each.approval_date, image = R.drawable.rejected)
                        }else if(each.status == "APPROVED") {
                            stats = Status(sourceTitle = each.user_id , date = each.approval_date, image = R.drawable.approved)
                        }else{
                            stats = Status(sourceTitle = each.user_id , date = each.approval_date, image = R.drawable.pending)
                        }

                        itemsClearance.add(stats)
                    }


//        val items = listOf(
//            Status(
//                sourceTitle = "Ref # ABCD1234", date = "07/07/90", image = R.drawable.rejected
//            ),
//            Status(
//                sourceTitle = "Ref # ABCD1234", date = "07/07/90", image = R.drawable.approved
//            ),
//            Status(
//                sourceTitle = "Ref # ABCD1234", date = "07/07/90", image = R.drawable.pending
//            ),
//            Status(
//                sourceTitle = "Ref # ABCD1234", date = "07/07/90", image = R.drawable.approved
//            )
//        )


                    runOnUiThread(Runnable {
                        //stuff that updates ui
                        adapter = CheckDocuments.MyStatusAdapter()
                        adapter.replaceItems(itemsClearance)
                        list.adapter = adapter
                    })
                }



                override fun onError(err: String) {
                    // error happen
                    println(err)
                }
            }
            barangay_preference = this.getSharedPreferences(PREFS_FILENAME, 0)
           var username = barangay_preference!!.getString("username", null)
            APICalls.callDocuments(username, callback)
            APICalls.callPermit(username, permitcallback)


        }).start()





        //view!!.findViewById<RecyclerView>(R.id.list).adapter = adapter

    }


     fun onActivityCreated(savedInstanceState: Bundle?) {


        //var list = view!!.findViewById<RecyclerView>(R.id.list)

    }

    fun success(){

    }

    fun error(){

    }

    fun getDocumentStatus() {

      //  var clearance = APICalls.callDocuments("jannaleli")

    }

    class MyStatusAdapter : RecyclerView.Adapter<MyStatusAdapter.ViewHolder>() {
        private var items = listOf<Status>()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {



            val rootView =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_status_holder, parent, false)
//            rootView.findViewById<TextView>(R.id.contentTextView).text = "Hello, world."
//            rootView.findViewById<TextView>(R.id.sourceTextView).text = "Hello, world Source."
            val textView = rootView.findViewById<TextView>(R.id.contentTextView)

            val sourceTextView = rootView.findViewById<TextView>(R.id.sourceOneTextView)


//            var list = rootView.findViewById(R.id.list) as RecyclerView // Add this
//            list.layoutManager = LinearLayoutManager(activity)
//            list.adapter = MyQuoteAdapter()






            return ViewHolder(rootView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]


           holder.containerView.contentTextView.text = item.sourceTitle
         //   holder.containerView.sourceTextView.text = item.date
            holder.containerView.imageContentView.setImageResource(item.image)

        }

        fun replaceItems(items: List<Status>) {
            this.items = items
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = items.size

        inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer
    }



}

