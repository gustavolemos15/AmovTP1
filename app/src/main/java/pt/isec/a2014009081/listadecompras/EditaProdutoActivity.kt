package pt.isec.a2014009081.listadecompras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import kotlinx.android.synthetic.main.activity_edita_produto.*
import kotlinx.android.synthetic.main.activity_novo_item.*

class EditaProdutoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edita_produto)

        var produto = intent.getSerializableExtra("PROD") as Produto

        etEditarDesignacao.setText(produto.nome)
        etEditarMarca.setText(produto.marca)
        etEditarQuantidade.setText(produto.quantidade.toString())
        etEditarNotas.setText(produto.notas)
        //spnEditarCategoria.setSelection(1)
        //spnEditarUnidades.setSelection(1)
    }
}