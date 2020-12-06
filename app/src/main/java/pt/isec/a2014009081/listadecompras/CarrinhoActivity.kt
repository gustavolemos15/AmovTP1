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
import kotlinx.android.synthetic.main.activity_carrinho.*
import kotlinx.android.synthetic.main.activity_edicao_lista.*
import kotlinx.android.synthetic.main.carrinho_item.*
import kotlinx.android.synthetic.main.carrinho_item.view.*
import kotlinx.android.synthetic.main.edicao_lista_item.view.*

class CarrinhoActivity : AppCompatActivity() {

    lateinit var lista: Lista
    lateinit var adapter: CarrinhoAdapter
    var sortAscendente = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrinho)

        lista = intent.getSerializableExtra("LISTA") as Lista

        adapter = CarrinhoAdapter(this, lista)
        lvCarrinho.adapter = adapter
        registerForContextMenu(lvCarrinho)

        lvCarrinho.setOnItemClickListener { _, _, position, _ ->
            // 1
            val selectedProduto = lista.lista[position]

            Toast.makeText(
                this,
                "Toque",
                Toast.LENGTH_SHORT
            ).show()


            /*
            selectedProduto.carrinho = true
            checkBoxCarrinho.isChecked = true*/

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menucarrinho, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*when (item.itemId) {
            R.id.btnSort -> {
                if(sortAscendente) ,
            }
            else -> return super.onOptionsItemSelected(item)
        }*/
        return true
    }
}

class CarrinhoAdapter(private val context: Context, private val dataSource: Lista) : BaseAdapter() {

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

        val rowView = inflater.inflate(R.layout.carrinho_item, p2, false)
        val produto = dataSource.lista[p0]

        val nome = rowView.tvTituloItemCarrinho
        val unidades = rowView.tvQuantidadeItemCarrinho
        val check = rowView.checkBoxCarrinho

        if(nome != null && unidades != null) {
            nome.text = produto.nome
            unidades.text = "${produto.quantidade} ${produto.unidade}"
            check.setChecked(produto.carrinho)
        }

        return rowView
    }
}