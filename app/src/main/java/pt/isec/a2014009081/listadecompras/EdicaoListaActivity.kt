package pt.isec.a2014009081.listadecompras

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edicao_lista.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.edicao_lista_item.view.*

class EdicaoListaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edicao_lista)

        val nomes = resources.getStringArray(R.array.nomeItems_array)
        val qtds = resources.getStringArray(R.array.qtdItems_array)

        val principal = intent.getSerializableExtra("PRINCIPAL") as? Principal
        val idLista = intent.getIntExtra("IDLISTA",0)
        val listas = principal?.listas
        println(idLista)

        if(listas != null) {
            btnNovoItem.setOnClickListener { onNovoItem(it, principal, idLista) }
            if(listas[idLista].lista.size > 0) {
                val adapter = PAdapter(this, principal, idLista)
                lvEdicaoLista.adapter = adapter
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menulistacompras, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //este when é só para o caso de adicionarmos mais opções ao menu, se for só uma retirar o when
        when (item.itemId) {
            R.id.carrinho -> Toast.makeText(this,"Passar para a atividade de compras", Toast.LENGTH_SHORT).show()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun onNovoItem(view : View, principal: Principal, idLista: Int) {
        val intent = Intent(this,NovoItemActivity::class.java)
        intent.putExtra("PRINCIPAL", principal)
        intent.putExtra("IDLISTA", idLista)
        startActivity(intent)
        //Snackbar.make(view, "Pop-up ou atividade para adicionar artigo", Snackbar.LENGTH_LONG).show()
    }
}

class PAdapter(private val context: Context, private val dataSource: Principal, private val idLista : Int) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.listas[idLista].lista.size
    }

    override fun getItem(p0: Int): Any {
        return dataSource.listas[idLista].lista[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val rowView = inflater.inflate(R.layout.edicao_lista_item, p2, false)
        val lista = dataSource.listas[idLista].lista[p0]

        val nome = rowView.edicaoTituloItem
        val unidades = rowView.edicaoUnidades

        if(nome != null && unidades != null) {
            nome.text = lista.nome
            unidades.text = "${lista.quantidade} Elementos"
        }

        return rowView
    }
}