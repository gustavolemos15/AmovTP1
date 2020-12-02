package pt.isec.a2014009081.listadecompras

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_edicao_lista.*
import kotlinx.android.synthetic.main.activity_edicao_lista.view.*
import kotlinx.android.synthetic.main.edicao_lista_item.view.*

class EdicaoListaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edicao_lista)

        /*
        val nomes = resources.getStringArray(R.array.nomeItems_array)
        val qtds = resources.getStringArray(R.array.qtdItems_array)
        */
        //receber objeto vindo de outra atividade
        val principal = intent.getSerializableExtra("PRINCIPAL") as? Principal
        val idLista = intent.getIntExtra("POSITION",0)
        val listas = principal?.listas

        if (listas != null) {
            val adapter = ProdutoAdapter(this, principal.listas[idLista])
            lvEdicaoLista.adapter = adapter
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

    fun onNovoItem(view: View, principal: Principal, posicao: Int) {
        val intent = Intent(this,NovoItemActivity::class.java)
        intent.putExtra("PRINCIPAL", principal)
        intent.putExtra("POSITION", posicao)
        startActivity(intent)
        //Snackbar.make(view, "Pop-up ou atividade para adicionar artigo", Snackbar.LENGTH_LONG).show()
    }
}

class ProdutoAdapter(private val context: Context, private val dataSource: Lista) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.lista.size
    }

    override fun getItem(p0: Int): Any {
        return dataSource.lista[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val rowView = inflater.inflate(R.layout.edicao_lista_item, p2, false)
        val item = dataSource.lista[p0]

        val nome = rowView.tvTituloItem
        val quantidade = rowView.tvQuantidadeItem

        nome.text = item.nome
        quantidade.text = "${item.quantidade} Elementos"

        return rowView
    }
}