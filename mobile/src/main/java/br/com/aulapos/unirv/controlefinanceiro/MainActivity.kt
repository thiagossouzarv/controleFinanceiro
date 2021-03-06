package br.com.aulapos.unirv.controlefinanceiro

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.github.ag.floatingactionmenu.OptionsFabLayout
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_lateral.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupToolbar()
        chartData()
        floatingMenu()
        setClickMenuLateral()
        //abreTelas(this, ReceitaActivity::class.java)

    }

    fun setClickMenuLateral() {

    }

    @SuppressLint("ResourceAsColor")
    fun setupToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.titulo_toolbar)
        setSupportActionBar(toolbar)

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return super.onCreateOptionsMenu(menu)

    }


    private fun floatingMenu() {
        //Set mini fab's colors.
        fab_l.setMiniFabsColors(
                R.color.darkGreen,
                R.color.darkRed);
        //Set main fab clicklistener.
        fab_l.setMainFabOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                //Toast.makeText(this@MainActivity, "Main fab clicked!", Toast.LENGTH_SHORT).show()
            }
        })

        //Set mini fabs clicklisteners.
        fab_l.setMiniFabSelectedListener(object : OptionsFabLayout.OnMiniFabSelectedListener {
            override fun onMiniFabSelected(fabItem: MenuItem) {
                 when (fabItem.getItemId()) {
                    R.id.fab_receita->abreTelas(applicationContext, ReceitaActivity::class.java)

                    R.id.fab_despesa->abreTelas(applicationContext, DespesaActivity::class.java)

                     else -> {
                    }
                 }
            }
        })
    }

    private fun abreTelas(applicationContext: Context, classe: Class<*>) {
        val intent = Intent(applicationContext, classe)
        startActivity(intent)
    }


    private fun chartData() {

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(4400F, 0F))
        entries.add(PieEntry(2200F, 1F))


        val piecolors = intArrayOf(Color.rgb(202, 46, 22), Color.rgb(28, 176, 19))

        val dataset = PieDataSet(entries, "")

        val labels = ArrayList<String>()
        labels.add("Receita")
        labels.add("Despesa")


        val data = PieData(dataset as IPieDataSet?)
        dataset.colors = ColorTemplate.createColors(piecolors) //
        data.setValueTextColor(Color.WHITE)
        val descricao = Description()
        descricao.text = "Grafico Receita/Despesas"
        pieChart.description = descricao
        pieChart.setData(data)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_despesas) {
            navegarListaDespesa() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }


    fun navegarListaDespesa() {
        var intent = Intent(this, ListaDespesasActivity::class.java)
        startActivity(intent)
    }


}

