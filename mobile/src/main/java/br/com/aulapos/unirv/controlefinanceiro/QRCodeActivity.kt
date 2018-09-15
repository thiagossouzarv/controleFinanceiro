package br.com.aulapos.unirv.controlefinanceiro

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import me.dm7.barcodescanner.zxing.ZXingScannerView
import web.WebTaskQRCode

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

        var UF = ""
        var chave = ""
        //var teste = "http://nfe.sefaz.go.gov.br/nfeweb/sites/nfce/danfeNFCe?p=52180819046218002159650020001351771390180977|2|1|1|FF9ED4C642DB9782E974A643F0829BAD28D9341B"

        var sp = rawResult!!.text.split("sefaz.")
        if (sp.size > 1) {
            if (sp[1].length >= 2) {
                UF = sp[1].substring(0, 2)
                var segundaStr = sp[1].split("NFCe?p=")
                if (segundaStr.size > 1) {
                    if (segundaStr[1].length >= 44) {
                        chave = segundaStr[1].substring(0, 44)
                        val buscar = WebTaskQRCode(this, chave, UF)
                        buscar.execute()
                    }
                } else {
                    Toast.makeText(this, "QRCode nao e um Cupom Fiscal Eletronico!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "QRCode nao e um Cupom Fiscal Eletronico!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "QRCode nao e um Cupom Fiscal Eletronico!", Toast.LENGTH_SHORT).show()
        }
        finish()
    }


}
