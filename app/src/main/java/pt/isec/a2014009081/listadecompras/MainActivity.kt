package pt.isec.a2014009081.listadecompras

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window

// Activity em vez de AppCompatActivity para tirar a titlebar
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var teste = 0
    }

    fun onNovaLista(view: View) {
        val intent = Intent(this,EdicaoListaActivity::class.java)
        startActivity(intent)
    }

    fun onListasAnteriores(view: View) {
        val intent = Intent(this,ListasAnterioresActivity::class.java)
        startActivity(intent)
    }

    fun onGestaoDados(view: View) {
        val intent = Intent(this,GestaoDadosActivity::class.java)
        startActivity(intent)
    }
}
