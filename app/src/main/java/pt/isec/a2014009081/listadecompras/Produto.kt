package pt.isec.a2014009081.listadecompras

import java.io.Serializable

class Produto(Nome : String, Quantidade: Int, Marca: String, Categoria: String) : Serializable {
    val nome  = Nome
    var quantidade = Quantidade
    val marca = Marca
    val categoria = Categoria

    val notas : ArrayList<String> = ArrayList()

}