package br.com.aulapos.unirv.controlefinanceiro

import adapter.DespesasAdapter
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_lista_despesas.*
import model.Despesa
import model.DespesaDAO
import java.util.*

class ListaDespesasActivity : AppCompatActivity() {

    internal var despesas: ArrayList<Despesa>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_despesas)
        setupToolbar()
        createRecicleView()

    }

    fun createRecicleView() {


        val despesaDAO = DespesaDAO(this)
        despesas = despesaDAO.getAll()

        val adapter: DespesasAdapter = DespesasAdapter(despesas!!)
        rvDespesas.adapter = adapter
        rvDespesas.layoutManager = LinearLayoutManager(this)

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

}
