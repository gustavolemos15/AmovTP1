package pt.isec.a2014009081.listadecompras

import android.graphics.Bitmap
import java.io.Serializable

class Produto(Nome : String, Quantidade: Int, Marca: String = " ", Categoria: String = " ", Unidade: String = " ", Notas: String = " ") : Serializable {
    val nome  = Nome
    var quantidade = Quantidade
    val marca = Marca
    val categoria = Categoria
    val unidade = Unidade
    var notas = Notas
    var carrinho : Boolean = false

    var histPrecos : ArrayList<Float> = ArrayList()
    var imagem : ByteArray? = null
}
