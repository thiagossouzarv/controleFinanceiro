package adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.com.aulapos.unirv.controlefinanceiro.R
import model.Receita
import model.ReceitaDAO

class ReceitaAdapter : RecyclerView.Adapter<ReceitaAdapter.ViewHolder> {


    private var mReceita: List<Receita>? = null
    var contexto: Context? = null

    // Pass in the contact array into the constructor
    constructor(receita: List<Receita>, context: Context) {
        mReceita = receita
        contexto = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ReceitaAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.activity_item_receita, parent, false)

        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: ReceitaAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val receita = mReceita?.get(position)


        var textView = viewHolder.nameTextView
        textView?.setText(receita?.descricao)

        textView = viewHolder.categoria
        textView?.setText(receita?.categoria)

        val valor = viewHolder.valor
        val valorTexto = "R$ " + receita?.valor.toString()
        valor?.setText(valorTexto)

        val button = viewHolder.button
        button?.setOnClickListener(View.OnClickListener {
            deletaReceita(receita?.id!!, this.contexto!!)

        })


    }

    fun deletaReceita(id: Int, context: Context) {
        var receitaDAO = ReceitaDAO(context)
        var receita = Receita()
        receita.id = id
        receitaDAO.delete(receita)
        Toast.makeText(context, "Deletado com sucesso!", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return mReceita?.size!!
    }


    inner class ViewHolder : RecyclerView.ViewHolder {
        var nameTextView: TextView? = null
        var valor: TextView? = null
        var button: Button? = null
        var categoria: TextView? = null

        constructor(itemView: View) : super(itemView) {
            nameTextView = itemView.findViewById(R.id.rvDescricaoReceita) as TextView
            valor = itemView.findViewById(R.id.rvValorReceita) as TextView
            categoria = itemView.findViewById(R.id.rvCategoriaReceita)
            button = itemView.findViewById(R.id.rvDelete_button_receita) as Button

        }
    }


}




