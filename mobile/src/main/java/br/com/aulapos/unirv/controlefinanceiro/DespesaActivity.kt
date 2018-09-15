package br.com.aulapos.unirv.controlefinanceiro

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_despesa.*
import model.Despesa
import model.DespesaDAO
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.File


class DespesaActivity : AppCompatActivity() {
    var imagem: Bitmap? = null
    var imagemURL: String? = null
    var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesa)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupToolbar()

        buscaCampos()

        botao_salvar_despesa.setOnClickListener(View.OnClickListener {
            salvarDespesa()
        })
    }


    fun salvarDespesa() {
        var despesa = Despesa()
        despesa.descricao = descricao.text.toString()
        despesa.valor = valor.text.toString().toFloat()
        despesa.categoria = categoria.text.toString()
        despesa.imagem = this!!.imagemURL.toString()
        despesa.id = id

        var despesasDAO = DespesaDAO(this)

        if (id == 0) {
            despesasDAO.create(despesa)
        } else {
            despesasDAO.update(despesa)
        }
        Toast.makeText(this, "Despesa Cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
        finish()

    }

    fun buscaCampos() {
        val intent = getIntent()
        id = intent?.getIntExtra("id", 0)!!
        if (id != 0) {
            var despesasDAO = DespesaDAO(this)
            var despesa: Despesa? = despesasDAO.getDespesaById(id!!)

            if (despesa != null) {
                descricao.setText(despesa.descricao)
                valor.setText(despesa.valor.toString())
                categoria.setText(despesa.categoria)
                imagemURL = despesa.imagem
                imagem = BitmapFactory.decodeFile(imagemURL)
                var imagemTela: ImageView = findViewById(R.id.imagemComprovanteDespesa);
                imagemTela.setImageBitmap(imagem)
            }
        }


    }

    override fun onStart() {
        EventBus.getDefault().unregister(this)
        var imagemTela: ImageView = findViewById(R.id.imagemComprovanteDespesa);
        imagemTela.setImageBitmap(imagem)
        super.onStart()
    }


    override fun onStop() {
        EventBus.getDefault().register(this)
        super.onStop()
    }


    @Subscribe
    fun OnEvent(bitmap: Bitmap) {
        imagem = bitmap
    }

    @Subscribe
    fun OnEvent(imagem: String) {
        imagemURL = imagem
    }


    @SuppressLint("ResourceAsColor")
    fun setupToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.titulo_toolbar_despesa)
        setSupportActionBar(toolbar)

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(false)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_write)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_despesas, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }

        if (item.itemId == R.id.menuCamera) {
            var intent = Intent(this,CameraActivity::class.java)
            startActivity(intent)
        }

        if (item.itemId == R.id.QRCode) {
            var intent = Intent(this, QRCodeActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {

        fun setImagemCamera(imagemTela: ImageView, imgFile: File) {
            if (imgFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
                imagemTela.setImageBitmap(myBitmap)
            }
        }
    }


}

