package pt.isec.a2014009081.listadecompras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.math.log

class ListasAnterioresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listas_anteriores)

        //receber objeto vindo de outra atividade
        val principal = intent.getSerializableExtra("PRINCIPAL") as? Principal
    }
}
