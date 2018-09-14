package adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import br.com.aulapos.unirv.controlefinanceiro.R
import model.Despesa

class DespesasAdapter : RecyclerView.Adapter<DespesasAdapter.ViewHolder> {


    private var mDespesas: List<Despesa>? = null

    // Pass in the contact array into the constructor
    constructor(despesas: List<Despesa>) {
        mDespesas = despesas
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
        val button = viewHolder.messageButton
        button?.setText(despesa?.categoria!!)
    }

    override fun getItemCount(): Int {
        return mDespesas?.size!!
    }


    inner class ViewHolder
    (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView? = null
        var messageButton: Button? = null


        fun ViewHolder(itemView: View) {
            super.itemView
            nameTextView = itemView.findViewById(R.id.rvDescricao) as TextView
            messageButton = itemView.findViewById(R.id.rvMessage_button) as Button
        }
    }


}