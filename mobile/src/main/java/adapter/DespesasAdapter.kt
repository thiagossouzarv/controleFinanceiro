package adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import br.com.aulapos.unirv.controlefinanceiro.R
import model.Despesa
import model.DespesaDAO

class DespesasAdapter : RecyclerView.Adapter<DespesasAdapter.ViewHolder> {


    private var mDespesas: List<Despesa>? = null
    var contexto: Context? = null

    // Pass in the contact array into the constructor
    constructor(despesas: List<Despesa>, context: Context) {
        mDespesas = despesas
        contexto = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): DespesasAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.activity_item_despesa, parent, false)

        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: DespesasAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val despesa = mDespesas?.get(position)


        val textView = viewHolder.nameTextView
        textView?.setText(despesa?.descricao)

        val valor = viewHolder.valor
        val valorTexto = "R$ " + despesa?.valor.toString()
        valor?.setText(valorTexto)

        val button = viewHolder.button
        button?.setOnClickListener(View.OnClickListener {
            deletaDespesa(despesa?.id!!, this.contexto!!)

        })


    }

    fun deletaDespesa(id: Int, context: Context) {
        var despesasDAO = DespesaDAO(context)
        var despesa = Despesa()
        despesa.id = id
        despesasDAO.delete(despesa)
    }

    override fun getItemCount(): Int {
        return mDespesas?.size!!
    }


    inner class ViewHolder : RecyclerView.ViewHolder {
        var nameTextView: TextView? = null
        var valor: TextView? = null
        var button: Button? = null

        constructor(itemView: View) : super(itemView) {
            nameTextView = itemView.findViewById(R.id.rvDescricao) as TextView
            valor = itemView.findViewById(R.id.rvValor) as TextView
            button = itemView.findViewById(R.id.rvDelete_button) as Button

        }
    }


}




