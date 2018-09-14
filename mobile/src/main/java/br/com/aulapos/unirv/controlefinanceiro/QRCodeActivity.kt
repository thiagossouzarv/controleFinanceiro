package br.com.aulapos.unirv.controlefinanceiro

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QRCodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mScannerView = ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: com.google.zxing.Result?) {

        Log.v("testeQRCODE", rawResult!!.text)
        finish()

//        mScannerView!!.resumeCameraPreview(this);
    }


}
