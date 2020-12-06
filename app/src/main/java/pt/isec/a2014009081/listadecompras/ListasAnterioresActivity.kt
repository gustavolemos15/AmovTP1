package pt.isec.a2014009081.listadecompras

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_listas_anteriores.*
import kotlinx.android.synthetic.main.listas_anteriores_item.view.*
import kotlin.math.log

class ListasAnterioresActivity : AppCompatActivity() {

    lateinit var principal: Principal
    lateinit var adapter : ListasAdapter
    private val RECEBE_LISTA = 4
    var posicao : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listas_anteriores)

        //receber objeto vindo de outra atividade
        principal = intent.getSerializableExtra("PRINCIPAL") as Principal
        val listas = principal?.listas

        if (listas != null) {
            
            adapter = ListasAdapter(this, principal)
            listasAnterioresView.adapter = adapter

              val context = this
              listasAnterioresView.setOnItemClickListener { _, _, position, _ ->
                // 1
                val selectedLista = principal.listas[position]
                  val categorias = principal.categorias
                  val unidades = principal.unidades

                    posicao = position
                  val intent = Intent(this,EdicaoListaActivity::class.java)
                  //enviar objeto para a atividade
                  intent.putExtra("LISTA", selectedLista)
                  intent.putExtra("CATEGORIAS", categorias)
                  intent.putExtra("UNIDADES", unidades)
                  startActivityForResult(intent, RECEBE_LISTA)
              }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RECEBE_LISTA) {
            if (resultCode == Activity.RESULT_OK) {
                // 3
                var lista = data?.getSerializableExtra("LISTA") as Lista?
                var cat = data?.getSerializableExtra("CATEGORIAS") as ArrayList<String>?
                var un = data?.getSerializableExtra("UNIDADES") as ArrayList<String>?

                if (un != null && cat != null) {
                    principal.categorias = cat
                    principal.unidades = un
                }
                if(lista != null && posicao >= 0) {
                    principal.listas.removeAt(posicao)
                    principal.listas.add(posicao, lista)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        //enviar objeto para a atividade
        intent.putExtra("PRINCIPAL", principal)
        setResult(Activity.RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }
}

class ListasAdapter(private val context: Context, private val dataSource: Principal) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.listas.size
    }

    override fun getItem(p0: Int): Any {
        return dataSource.listas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val rowView = inflater.inflate(R.layout.listas_anteriores_item, p2, false)
        val lista = dataSource.listas[p0]

        val titulo = rowView.listasAnterioresTitulo
        val nElementos = rowView.listasAnterioresItems


        titulo.text = lista.nome
        nElementos.text = "${lista.lista.size} Elementos"

        return rowView
    }
}

