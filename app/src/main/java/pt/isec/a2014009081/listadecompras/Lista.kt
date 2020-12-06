package pt.isec.a2014009081.listadecompras

import java.io.Serializable

class Lista (Nome: String) : Serializable{
    var nome = Nome
    val lista : ArrayList<Produto> = ArrayList()
}