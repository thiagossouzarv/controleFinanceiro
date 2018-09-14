package br.com.aulapos.unirv.controlefinanceiro

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.afollestad.materialdialogs.MaterialDialog
import com.wonderkiln.camerakit.CameraKitEventCallback
import com.wonderkiln.camerakit.CameraKitImage
import com.wonderkiln.camerakit.CameraView
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {
    var mCameraView: CameraView? = null
    private val REQUEST_CAMERA = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupToolbar()
        abreCamera()
    }

    @SuppressLint("ResourceAsColor")
    fun setupToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.titulo_toolbar_camera)
        setSupportActionBar(toolbar)

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(false)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_write)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    fun abreCamera(){
        checkCamera()
        btn_take_pic.setOnClickListener(View.OnClickListener {
            camera.captureImage(CameraKitEventCallback { cameraKitImage ->
                saveImage(cameraKitImage)
                finish()
            })
        })
    }

    override fun onResume() {
        super.onResume()
        camera.start()
    }

    override fun onPause() {
        camera.stop()
        super.onPause()
    }


    private fun checkCamera() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA)
        }
    }

    private fun saveImage(cameraKitImage: CameraKitImage): String {
        var file: File? = null
        var fOut: OutputStream? = null
        try {
            file = createImageFile()
            fOut = FileOutputStream(file!!)
            fOut.write(cameraKitImage.jpeg)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fOut!!.close()
            } catch (e: IOException) {
                Log.d("CAMERA_ERROR", e.message)
            }

        }
        if (file != null) {
            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())

            var imagem: ImageView = findViewById(R.id.imagemComprovanteDespesa)
            imagem.setImageBitmap(myBitmap)
        }
        return file!!.absolutePath
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = //File.createTempFile(DIRETORY_PICTURES,"");
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
                imageFileName, /* prefix */
                ".jpeg", /* suffix */
                storageDir      /* directory */
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {


        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.size > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //TODO ABRO A CAMERA
                } else {
                    showError(R.string.error_permission_not_granted)
                }
            } else {
                showError(R.string.permission_request_canceled)
            }
        }
    }

    fun showError(idStringDescription: Int) {
        MaterialDialog.Builder(this)
                .title(R.string.title_error)
                .content(idStringDescription)
                .positiveText(R.string.label_ok)
                .show()
    }




}
