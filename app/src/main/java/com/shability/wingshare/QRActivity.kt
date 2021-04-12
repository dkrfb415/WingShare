package com.shability.wingshare

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.CaptureManager

class QRActivity : Activity(), DecoratedBarcodeView.TorchListener {

    var flashCheck : Boolean = true
    lateinit var capture : CaptureManager

    lateinit var zxing_barcode_scanner : DecoratedBarcodeView
    lateinit var port_back_frag1 : ImageView
    lateinit var qr_flash_lay : RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r)

        zxing_barcode_scanner = findViewById(R.id.zxing_barcode_scanner)
        port_back_frag1 = findViewById(R.id.port_back_frag1)
        qr_flash_lay = findViewById(R.id.qr_flash_lay)


        zxing_barcode_scanner.setTorchListener(this)
        capture = CaptureManager(this, zxing_barcode_scanner)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode()

        port_back_frag1.setOnClickListener {
            this.finish()
        }

//        qr_number_lay.setOnClickListener {
//            startActivity(Intent(this, QRNumActivity::class.java))
//        }

    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    fun switchFlashlight(view : View) {
        if(flashCheck) {
            zxing_barcode_scanner.setTorchOn()
        }
        else {
            zxing_barcode_scanner.setTorchOff()
        }
    }

    fun hasFlash(): Boolean {
        return applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    override fun onTorchOn() {
        flashCheck = false;
    }

    override fun onTorchOff() {
        flashCheck = true;
    }
}