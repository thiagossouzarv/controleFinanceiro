package br.com.aulapos.unirv.controlefinanceiro

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLogTags
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chartData()
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

