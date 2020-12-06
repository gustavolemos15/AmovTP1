package pt.isec.a2014009081.listadecompras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edita_produto.*
import kotlinx.android.synthetic.main.activity_info_detalhada.*
import kotlinx.android.synthetic.main.activity_info_detalhada.tvCategoria
import kotlinx.android.synthetic.main.activity_info_detalhada.tvNotas

class InfoDetalhadaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_detalhada)

        //recebe produto
        val produto = intent.getSerializableExtra("PRODUTO") as? Produto


        //TODO: criar layout para mostrar a informacao do produto
        if (produto != null){
            //TODO: fazer aqui a ligacao entre o produto e o layout para mostrar a info nas TextViews
            tvDesignacao.text = produto.nome
            tvCategoria.text = produto.categoria
            tvMarca.text = produto.marca
            tvNotas.text = produto.notas

            // Tratameto para a quantidade
            val a = produto.quantidade; val b = " "; val c = produto.unidade; val sb = StringBuilder()
            sb.append(a).append(b).append(c)
            tvQuantidade.text = sb.toString()
        }
    }
}