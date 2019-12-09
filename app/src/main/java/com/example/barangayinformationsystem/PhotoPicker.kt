package com.example.barangayinformationsystem

import android.Manifest
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Contacts.SettingsColumns.KEY
import android.provider.MediaStore
import android.util.Log
import android.view.View
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
import android.content.Context
import android.content.pm.PackageManager

import android.graphics.BitmapFactory
import android.text.TextUtils

import android.webkit.MimeTypeMap
import android.widget.EditText

import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amazonaws.ClientConfiguration
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions


import com.amazonaws.util.IOUtils

import java.io.InputStream
import java.io.OutputStream

// Storage Permissions
private val REQUEST_EXTERNAL_STORAGE = 1
private val PERMISSIONS_STORAGE =
    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

class PhotoPicker : AppCompatActivity() {

    private var btn: Button? = null
    private var imageview: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private var fileUri: Uri? = null
    private var fileName: String? = null
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_picker)

        btn =  findViewById<Button>(R.id.imgBtn) as Button
        imageview = findViewById<ImageView>(R.id.iv) as ImageView

        btn!!.setOnClickListener { showPictureDialog() }
     //   AWSMobileClient.getInstance().initialize(this).execute()
        // AWSMobileClient.getInstance().initialize(this).execute();

    }

    public fun verifyStorage(activity: Activity) {
        val permission = ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE)
        }
    }

    private fun createFile(context: Context, srcUri: Uri, dstFile: File) {
        try {
            val inputStream = context.contentResolver.openInputStream(srcUri) ?: return
            val outputStream = FileOutputStream(dstFile)
            IOUtils.copy(inputStream, outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun downloadFile(fileName: String) {
        val localFile = File.createTempFile("images", getFileExtension(fileUri!!));

        val credentials = BasicAWSCredentials("AKIAIZGJAQVC2ET4ITYQ", "lskxORlNq8WNtYpsl8EYtTTt+ypAA1h09DsYKVsG")
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
            .context(applicationContext)
            .defaultBucket("barangay-api")
            //.awsConfiguration(AWSMobileClient.getInstance().configuration)
            .s3Client(s3Client)
            .build()
        val downloadObserver: TransferObserver =     transferUtility.download(fileName + "." + getFileExtension(fileUri!!), localFile);
        downloadObserver.setTransferListener(object : TransferListener {

            override fun onStateChanged(id: Int, state: TransferState) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed download.
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

    private fun getFileExtension(uri: Uri): String? {
        val contentResolver = contentResolver
        val mime = MimeTypeMap.getSingleton()

        return mime.getExtensionFromMimeType(contentResolver.getType(uri))
    }


    override public fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1){


            if(grantResults.count() > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                //Permission Granted

                var clientConfiguration: ClientConfiguration = ClientConfiguration()
                clientConfiguration.setMaxErrorRetry(0);
                clientConfiguration.setConnectionTimeout(3600000);
                clientConfiguration.setSocketTimeout(3600000);
                Log.d("lol", fileName + "." + getFileExtension(fileUri!!))

                val file: File =  File(applicationContext.filesDir,
                    "/jann");

               val result =  file.mkdirs()

                Log.d("lol", file.absolutePath)
                Log.d("lol", result.toString())
                fileName = "${UUID.randomUUID()}.jpg"
                val finalFile : File = File(file, "/" + fileName)

                createFile(getApplicationContext(), fileUri!!, finalFile);
                val credentials = BasicAWSCredentials("AKIAIZGJAQVC2ET4ITYQ", "lskxORlNq8WNtYpsl8EYtTTt+ypAA1h09DsYKVsG")
                val s3Client = AmazonS3Client(credentials,clientConfiguration)
                // Log.d("lol", AWSMobileClient.getInstance().configuration.toString())

                var regions: Region = Region.getRegion(Regions.AP_SOUTHEAST_1)
                //Region.getRegion(.Region)

                s3Client.setRegion(regions)

                val transferUtility = TransferUtility.builder()
                    .context(applicationContext)
                    .defaultBucket("barangay-api")
                    //.awsConfiguration(AWSMobileClient.getInstance().configuration)
                    .s3Client(s3Client)
                    .build()

// "jsaS3" will be the folder that contains the file


                val uploadObserver = transferUtility.upload(fileName + "." + getFileExtension(fileUri!!), finalFile)          //   .upload("jsaS3/" + fileName, file)

                uploadObserver.setTransferListener(object : TransferListener {

                    override fun onStateChanged(id: Int, state: TransferState) {
                        if (TransferState.COMPLETED == state) {
                            // Handle a completed download.
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

// If your upload does not trigger the onStateChanged method inside your
// TransferListener, you can directly check the transfer state as shown here.
                if (TransferState.COMPLETED == uploadObserver.state) {
                    // Handle a completed upload.
                }
            }else{
                //Permission denied
            }



        }


    }


    private fun uploadAFile(fileName: String, uri: Uri) {
// KEY and SECRET are gotten when we create an IAM user above

//TO DO: Maybe input later
//            if (!validateInputFileName(fileName)) {
//                return;
//            }

        this.fileName = fileName
        this.fileUri = uri
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1)
        val permission = ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE)
        }else{

        }


    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    System.out.print(contentURI);
                    Log.d("myTAG", contentURI.toString())
                    val path = saveImage(bitmap)
                    val fileName = contentURI?.pathSegments?.last()

                    Toast.makeText(this@PhotoPicker, "Image Saved!", Toast.LENGTH_SHORT).show()
                    imageview!!.setImageBitmap(bitmap)
                    uploadAFile(fileName!!,contentURI )

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@PhotoPicker, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            imageview!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(this@PhotoPicker, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

//    fun upload(uri: URI) {
//
//        // KEY and SECRET are gotten when we create an IAM user above
//        var credentials = BasicAWSCredentials("AKIAIZGJAQVC2ET4ITYQ", "lskxORlNq8WNtYpsl8EYtTTt+ypAA1h09DsYKVsG")
//        var s3Client = AmazonS3Client(credentials)
//
//        val transferUtility = TransferUtility.builder()
//            .context(applicationContext)
//            .awsConfiguration(AWSMobileClient.getInstance().configuration)
//            .s3Client(s3Client)
//            .build()
//
//// "jsaS3" will be the folder that contains the file
//        val uploadObserver = transferUtility.upload("jsaS3/" + fileName, file)
//
//        uploadObserver.setTransferListener(object : TransferListener {
//
//            override fun onStateChanged(id: Int, state: TransferState) {
//                if (TransferState.COMPLETED == state) {
//                    // Handle a completed upload.
//                }
//            }
//
//            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
//                val percentDonef = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
//                val percentDone = percentDonef.toInt()
//            }
//
//            override fun onError(id: Int, ex: Exception) {
//                // Handle errors
//            }
//        })
//
//// If your upload does not trigger the onStateChanged method inside your
//// TransferListener, you can directly check the transfer state as shown here.
//        if (TransferState.COMPLETED == uploadObserver.state) {
//            // Handle a completed upload.
//        }
//
//    }

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }
}
