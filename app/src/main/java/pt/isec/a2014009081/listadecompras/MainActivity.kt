package pt.isec.a2014009081.listadecompras

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.edit_text_dialog.view.*

// Activity em vez de AppCompatActivity para tirar a titlebar
class MainActivity : Activity() {

    lateinit var principal: Principal
    private val ADD_LISTA = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //"class main" -> nome principal
        principal = Principal()

        //id do botao
        btnListasAnteriores.setOnClickListener{ onListasAnteriores(it, principal) }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_LISTA) {
            if (resultCode == Activity.RESULT_OK) {
                // 3
                var lista = data?.getSerializableExtra("LISTA") as Lista?
                if(lista != null) {
                    principal.listas.add(0, lista)
                }
            }
        }
    }

    fun onNovaLista(view: View) {
        val dlgBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dlgLayout = inflater.inflate(R.layout.edit_text_dialog, null)
        val editText = dlgLayout.etDialog
        val intent = Intent(this,EdicaoListaActivity::class.java)

        with(dlgBuilder) {
            setTitle(R.string.primeiroBotao)
            setPositiveButton(R.string.adicionar) { dialog, which ->
                // verificar se está vazia, se sim toast a dizer que nada foi inserido
                if(editText.text.toString().isEmpty())
                    return@setPositiveButton
                principal.listas.add(0, Lista(editText.text.toString()))
                intent.putExtra("LISTA", principal.listas[0])
                startActivityForResult(intent, ADD_LISTA)
            }
            setView(dlgLayout)
            setCancelable(true)
            show()
        }

    }

    fun onListasAnteriores(view: View, principal : Principal) {
        val intent = Intent(this,ListasAnterioresActivity::class.java)
        //enviar objeto para a atividade
        intent.putExtra("PRINCIPAL", principal)
        startActivity(intent)
    }

    fun onGestaoDados(view: View) {
        val intent = Intent(this,GestaoDadosActivity::class.java)
        startActivity(intent)
    }

}

