package com.example.barangayinformationsystem

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        var titleView = findViewById(R.id.eventTitle) as TextView
        var contentView =  findViewById(R.id.eventContent) as TextView // Add this
        var imageView =  findViewById(R.id.eventImage) as ImageView // Add this
        val extras = getIntent().getExtras()
        if (null != extras) {
            val title = extras.getString("title")
            val content: String? = extras.getString("content")
            val image: ByteArray? = extras.getByteArray("image")
            val bmp: Bitmap = BitmapFactory.decodeByteArray(image, 0 , image!!.size)
            titleView.text = title
            contentView.text = content
            imageView.setImageBitmap(bmp!!)

            //The key argument here must match that used in the other activity
        }
    }


}
