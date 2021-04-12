package com.shability.wingshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class QRScannerActivity : AppCompatActivity() {

    lateinit var integrator : IntentIntegrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        integrator = IntentIntegrator(this)
    }

    override fun onResume() {
        super.onResume()

        integrator.setBeepEnabled(false)
        integrator.setCaptureActivity(QRActivity ::class.java)
        integrator.setOrientationLocked(false)
////                        integrator.setCaptureActivity(QRActivity ::class.java)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null)
        {
            if(result.contents != null)
                Log.d("QRscanner", "QRscanner : " + result.contents)
            val qrData = result.contents.replace("https://station.wingstation.kr/station/request/?code=", "")
            Log.d("QRscanner", "QRscanner : " + qrData.toString())
            var intent = Intent(this, ChargeStartActivity::class.java)
            intent.putExtra("result", qrData.toString())
            startActivity(intent)
            this.finish()

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}