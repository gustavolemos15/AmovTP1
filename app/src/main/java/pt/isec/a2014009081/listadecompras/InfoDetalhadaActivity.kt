package pt.isec.a2014009081.listadecompras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info_detalhada.*

class InfoDetalhadaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_detalhada)

        //recebe produto
        val produto = intent.getSerializableExtra("PRODUTO") as? Produto


        //TODO: criar layout para mostrar a informacao do produto
        if (produto != null){
            //TODO: fazer aqui a ligacao entre o produto e o layout para mostrar a info nas TextViews
           // tvDesignacao.text = produto.nome
        }
    }
}