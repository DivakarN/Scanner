package com.sysaxiom.scanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.util.isNotEmpty
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_scanner.*

class ScannerActivity : AppCompatActivity() {

    lateinit var cameraSource: CameraSource
    lateinit var detector: BarcodeDetector
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        detector = BarcodeDetector.Builder(applicationContext).build()
        cameraSource  = CameraSource.Builder(this@ScannerActivity,detector)
            .setAutoFocusEnabled(true)
            .build()
        surfaceView.holder.addCallback(surfaceCallBack)
        detector.setProcessor(processor)
        if (!detector.isOperational) {
            return
        }
    }

    private val surfaceCallBack = object : SurfaceHolder.Callback {

        override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

        }

        override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
            try{
                cameraSource.start(surfaceHolder)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        override fun surfaceDestroyed(p0: SurfaceHolder?) {
            cameraSource.stop()
        }

    }

    private val processor = object : Detector.Processor<Barcode>{
        override fun release() {

        }

        override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
            if(detections!=null && detections.detectedItems.isNotEmpty()){
                val qrCodes = detections.detectedItems
                val code= qrCodes.valueAt(0)
                val intent = Intent()
                intent.putExtra("Data",code.displayValue)
                setResult(ScannerActivityResultCode,intent)
                finish()
            }
        }

    }
}
