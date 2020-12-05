package pt.isec.a2014009081.listadecompras

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edicao_lista.*
import kotlinx.android.synthetic.main.activity_listas_anteriores.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.edicao_lista_item.view.*

class EdicaoListaActivity : AppCompatActivity() {

    lateinit var lista: Lista
    lateinit var adapter : PAdapter
    private val ADD_PRODUTO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edicao_lista)


        lista = intent.getSerializableExtra("LISTA") as Lista

        if(lista != null) {
            btnNovoItem.setOnClickListener { onNovoItem(it) }
                adapter = PAdapter(this, lista)
                lvEdicaoLista.adapter = adapter
                registerForContextMenu(lvEdicaoLista)

                val context = this
                lvEdicaoLista.setOnItemClickListener { _, _, position, _ ->
                    // 1
                    val selectedProduto = lista.lista[position]

                    val intent = Intent(this,InfoDetalhadaActivity::class.java)
                    //enviar objeto para a atividade
                    intent.putExtra("PRODUTO", selectedProduto)
                    startActivity(intent)
                }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if(requestCode == ADD_PRODUTO) {
                if (resultCode == Activity.RESULT_OK) {
                    // 3
                    var produto:Produto = data?.getSerializableExtra("PRODUTO") as Produto
                    lista.lista.add(produto)
                    adapter.notifyDataSetChanged()
                }
            }
    }

    override fun onBackPressed() {
        val intent = Intent()
        //enviar objeto para a atividade
        intent.putExtra("LISTA", lista)
        setResult(Activity.RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menulistacompras, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //este when é só para o caso de adicionarmos mais opções ao menu, se for só uma retirar o when
        when (item.itemId) {
            R.id.carrinho -> {
                val intent = Intent(this,CarrinhoActivity::class.java)
                startActivity(intent)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun onNovoItem(view : View) {
        val intent = Intent(this,NovoItemActivity::class.java)
        startActivityForResult(intent, ADD_PRODUTO)
        //Snackbar.make(view, "Pop-up ou atividade para adicionar artigo", Snackbar.LENGTH_LONG).show()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_eliminar_produto, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterContextMenuInfo

        return when (item.itemId) {
            R.id.eliminar -> {
                lista.lista.removeAt(info.position)
                adapter.notifyDataSetChanged()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class PAdapter(private val context: Context, private val dataSource: Lista) : BaseAdapter() {

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
        val lista = dataSource.lista[p0]

        val nome = rowView.edicaoTituloItem
        val unidades = rowView.edicaoUnidades

        if(nome != null && unidades != null) {
            nome.text = lista.nome
            unidades.text = "${lista.quantidade} Elementos"
        }

        return rowView
    }
}