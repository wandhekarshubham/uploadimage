package com.example.uploaddata

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewOnReceiveContentListener
import com.google.android.material.bottomappbar.BottomAppBar
import java.io.File
import java.nio.file.attribute.AclEntry
import java.util.jar.Manifest
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val Requestcode:Int=111
    private val requestcode_gallery:Int=222
    var filepathstring:String=""
    var filepathcheck:Boolean=false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button_upload:Button=findViewById(R.id.button_upload)
        val button_choose:Button=findViewById(R.id.button_choose)
        val progressbar:ProgressBar=findViewById(R.id.progressbar)


        button_upload.setOnClickListener {
            if(filepathcheck==true){
                Toast.makeText(this, "uploadtask fun called", Toast.LENGTH_SHORT).show()

            }
            else
            {
                Toast.makeText(this, "Please first choose a file", Toast.LENGTH_SHORT).show()
            }

        }
        button_choose.setOnClickListener {
            if(Build.VERSION.SDK_INT>=23){
                if(checkpermission()){
                    filepicker()
                }
                else{
                    requestpermission()
                }
            }
            else{
                filepicker()
            }
        }



    }

    private fun uploadtask() {

    }

    private fun filepicker() {
            Toast.makeText(this,"filepicker fun called",Toast.LENGTH_SHORT).show()
        // lets pick the file
        var intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,requestcode_gallery)
    }

    fun requestpermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,android.Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            Toast.makeText(this,"please give persmission to upload",Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),Requestcode)
        }
        else
        {

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),Requestcode)

        }
    }
    fun checkpermission():Boolean{
        var result=ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if(result==PackageManager.PERMISSION_GRANTED){
            return true
        }
        else
        {
            return false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            Requestcode->
            {
                if(grantResults.size>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"permissions sucessful",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this,"permissions failed",Toast.LENGTH_SHORT).show()

                }
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 222) {
            if(data!=null){
                val imageuri=data.data
                val imageView:ImageView=findViewById(R.id.imageView_preview)
                imageView.setImageURI(imageuri)
                Toast.makeText(this, ""+imageuri, Toast.LENGTH_SHORT).show()
                var filepath: TextView =findViewById(R.id.filepath)

                filepath.text="filePath:"+imageuri
                if(filepath!=null){
                    filepathcheck=true
                    filepathstring=filepath.toString()
                }

/*
                filepath.text="filePath:"+imageuri
*/
            }


            /*val imageurl =data!!.extras?.get("data") as Bitmap
            val imageview: ImageView = findViewById(R.id.imageView_preview)
            imageview.setImageBitmap(imageurl)*/


        }

    }

}