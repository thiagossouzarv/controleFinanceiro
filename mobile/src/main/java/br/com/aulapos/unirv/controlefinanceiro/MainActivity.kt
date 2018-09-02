package br.com.aulapos.unirv.controlefinanceiro

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.github.ag.floatingactionmenu.OptionsFabLayout
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chartData()
        floatingMenu()
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
                    R.id.fab_receita -> Toast.makeText(
                            applicationContext,
                            fabItem.getTitle(),
                            Toast.LENGTH_SHORT).show()
                    R.id.fab_despesa -> Toast.makeText(applicationContext,
                            fabItem.getTitle(),
                            Toast.LENGTH_SHORT).show()
                    else -> {
                    }
                }
            }
        })
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

}

