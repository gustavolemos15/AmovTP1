package pt.isec.a2014009081.listadecompras

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

// Activity em vez de AppCompatActivity para tirar a titlebar
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //"class main" -> nome principal
        val principal = Principal()

        //id do botao
        btnListasAnteriores.setOnClickListener{ onListasAnteriores(it, principal) }

    }

    fun onNovaLista(view: View) {
        val intent = Intent(this,EdicaoListaActivity::class.java)
        startActivity(intent)
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

