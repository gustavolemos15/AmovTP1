package pt.isec.a2014009081.listadecompras

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edita_produto.*
import kotlinx.android.synthetic.main.activity_info_detalhada.*
import kotlinx.android.synthetic.main.activity_info_detalhada.tvCategoria
import kotlinx.android.synthetic.main.activity_info_detalhada.tvNotas
import kotlinx.android.synthetic.main.edicao_lista_item.view.*

class InfoDetalhadaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_detalhada)

        //recebe produto
        val produto = intent.getSerializableExtra("PRODUTO") as? Produto

        if (produto != null){
            tvDesignacao.text = produto.nome
            tvCategoria.text = produto.categoria
            tvMarca.text = produto.marca
            tvNotas.text = produto.notas
            if(produto.imagem != null){
                var foto = BitmapFactory.decodeByteArray(produto.imagem, 0, produto.imagem!!.size);
                imagemItem.setImageBitmap(foto)
            }

            // Tratameto para a quantidade
            val a = produto.quantidade; val b = " "; val c = produto.unidade; val sb = StringBuilder()
            sb.append(a).append(b).append(c)
            tvQuantidade.text = sb.toString()
        }
    }
}