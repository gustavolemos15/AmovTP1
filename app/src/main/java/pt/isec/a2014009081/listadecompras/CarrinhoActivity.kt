package pt.isec.a2014009081.listadecompras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_carrinho.*

class CarrinhoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrinho)

        val nomes = resources.getStringArray(R.array.nomeItems_array)
        val qtds = resources.getStringArray(R.array.qtdItems_array)

        val adapter = ArrayAdapter<String>(this, R.layout.carrinho_item, R.id.tvTituloItemCarrinho, nomes)
        //adapter = ArrayAdapter<String>(this, R.layout.edicao_lista_item, R.id.tvTituloItem, nomes)

        lvCarrinho.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menucarrinho, menu)
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

    fun onCheackedItem() {

    }
}
