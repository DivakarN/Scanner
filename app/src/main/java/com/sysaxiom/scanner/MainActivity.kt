package com.sysaxiom.scanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

const val REQUEST_CAMERA_PERMISSION = 1001
const val ScannerActivityResultCode = 1002

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }

        button.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this,"Camera Permission Needed",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else{
                val intent = Intent(this, ScannerActivity::class.java)
                startActivityForResult(intent,ScannerActivityResultCode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ScannerActivityResultCode){
            Toast.makeText(this,data?.getStringExtra("Data"),Toast.LENGTH_LONG).show()
        }
    }

}
