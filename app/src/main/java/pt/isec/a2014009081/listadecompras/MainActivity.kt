package pt.isec.a2014009081.listadecompras

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

// Activity em vez de AppCompatActivity para tirar a titlebar
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //"class main" -> nome principal
        val principal = Principal()

        //id do botao
        listasAnteriores.setOnClickListener { onListasAnteriores(it, principal) }

    }

    fun onNovaLista(view: View) {
        editTextDialog()
        //passagem de atividade passou para o editTextDialog no positiveButton
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

    private fun editTextDialog() {
        val dlgBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dlgLayout = inflater.inflate(R.layout.edit_text_dialog, null)
        val et = dlgLayout.findViewById<EditText>(R.id.etDialog)

        with(dlgBuilder) {
            setTitle(resources.getString(R.string.primeiroBotao))
            setPositiveButton(R.string.adicionar) {dialog, which ->
                // consegues a string introduzida com "et.text.toString()" PS: Podes apagar este comentário
                Toast.makeText(
                    this@MainActivity,
                    et.text.toString(),
                    Toast.LENGTH_SHORT
                ).show()

                //Passagem de atividade apenas após inserir o titulo
                val intent = Intent(this@MainActivity,EdicaoListaActivity::class.java)
                startActivity(intent)
            }
            setView(dlgLayout)
            setCancelable(true)
            show()
        }
    }

}

