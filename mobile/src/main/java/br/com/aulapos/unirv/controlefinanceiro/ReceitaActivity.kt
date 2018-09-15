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
import kotlinx.android.synthetic.main.activity_receita.*
import model.Receita
import model.ReceitaDAO
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ReceitaActivity : AppCompatActivity() {
    var imagem: Bitmap? = null
    var imagemURL: String? = null
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receita)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupToolbar()

        buscaCampos()

        botao_salvar_receita.setOnClickListener(View.OnClickListener {
            salvarReceita()
        })
    }

    fun salvarReceita() {
        var receita = Receita()
        receita.descricao = descricaoReceita.text.toString()
        receita.valor = valorReceita.text.toString().toFloat()
        receita.categoria = categoriaReceita.text.toString()
        receita.imagem = this!!.imagemURL.toString()
        receita.id = id

        var receitaDAO = ReceitaDAO(this)

        if (id == 0) {
            receitaDAO.create(receita)
        } else {
            receitaDAO.update(receita)
        }
        Toast.makeText(this, "Receita Cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
        finish()

    }

    fun buscaCampos() {
        val intent = getIntent()
        id = intent?.getIntExtra("id", 0)!!
        if (id != 0) {
            var receitaDAO = ReceitaDAO(this)
            var receita: Receita? = receitaDAO.getDespesaById(id!!)

            if (receita != null) {
                descricaoReceita.setText(receita.descricao)
                valorReceita.setText(receita.valor.toString())
                categoriaReceita.setText(receita.categoria)
                imagemURL = receita.imagem
                imagem = BitmapFactory.decodeFile(imagemURL)
                var imagemTela: ImageView = findViewById(R.id.imagemComprovanteReceita);
                imagemTela.setImageBitmap(imagem)
            }
        }


    }


    @SuppressLint("ResourceAsColor")
    fun setupToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.titulo_toolbar_receita)
        setSupportActionBar(toolbar)

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(false)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_write)
        }

    }

    override fun onStart() {
        EventBus.getDefault().unregister(this)
        var imagemTela: ImageView = findViewById(R.id.imagemComprovanteReceita);
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



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_receita, menu)
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

        return super.onOptionsItemSelected(item)
    }


}
