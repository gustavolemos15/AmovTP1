package pt.isec.a2014009081.listadecompras

import java.io.Serializable

class Produto(Nome : String, Quantidade: Int, Marca: String, Categoria: String) : Serializable {
    var nome  = Nome
    var quantidade = Quantidade
    var marca = Marca
    var categoria = Categoria

    //var notas : ArrayList<String> = ArrayList()

}