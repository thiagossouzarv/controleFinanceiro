package br.com.aulapos.unirv.controlefinanceiro

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
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
import model.Despesa
import model.DespesaDAO
import model.Receita
import model.ReceitaDAO

class MenuLateralActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var totalDespesas: Float? = null
    var totalReceita: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_lateral)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupToolbar()

        floatingMenu()
    }

    override fun onResume() {
        calculaTotalReceitaDespesa()
        chartData()
        super.onResume()
    }

    fun calculaTotalReceitaDespesa() {
        var despesaDAO = DespesaDAO(this)
        var despesas = despesaDAO.getAll()
        var despesa: Despesa? = null
        totalDespesas = 0.0f
        for (despesa in despesas) {
            totalDespesas = totalDespesas!! + despesa.valor
        }

        var receitaDAO = ReceitaDAO(this)
        var receitas = receitaDAO.getAll()
        var receita: Receita? = null
        totalReceita = 0.0f
        for (receita in receitas) {
            totalReceita = totalReceita!! + receita.valor
        }

        totalDespesaTela.text = "R$ " + totalDespesas.toString()
        totalReceitaTela.text = "R$ " + totalReceita.toString()


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

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        //
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
                    R.id.fab_receita -> abreTelas(applicationContext, ReceitaActivity::class.java)

                    R.id.fab_despesa -> abreTelas(applicationContext, DespesaActivity::class.java)

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
        entries.add(PieEntry(this!!.totalDespesas!!, 0F))
        entries.add(PieEntry(this!!.totalReceita!!, 1F))


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



    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_despesas) {
            navegarListaDespesa()
        } else if (id == R.id.nav_receita) {
            navegarListaReceita()
        } else if (id == R.id.nav_compartilhar) {

        } else if (id == R.id.nav_enviar) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun navegarListaDespesa() {
        var intent = Intent(this, ListaDespesasActivity::class.java)
        startActivity(intent)
    }

    fun navegarListaReceita() {
        var intent = Intent(this, ListaReceitaActivity::class.java)
        startActivity(intent)
    }



}