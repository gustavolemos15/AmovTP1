package pt.isec.a2014009081.listadecompras

import java.io.Serializable

class Principal : Serializable{

    val listas : ArrayList<Lista> = ArrayList()
    val produtos : ArrayList<Produto> = ArrayList()
    var categorias : ArrayList<String> = ArrayList()
    var unidades : ArrayList<String> = ArrayList()

    init {
        // inicializar unidades e categorias
        // ler ficheiros
    }
}