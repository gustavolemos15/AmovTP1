package pt.isec.a2014009081.listadecompras

import java.io.Serializable

class Principal : Serializable{

    val listas : ArrayList<Lista> = ArrayList()
    val produtos : ArrayList<Produto> = ArrayList()
    var categorias : ArrayList<String> = ArrayList()
    var unidades : ArrayList<String> = ArrayList()

    init {
        listas.add(Lista("Lista 1"))
        listas.add(Lista("Lista 2"))
        listas.add(Lista("Lista 3"))
        listas.add(Lista("Lista 4"))
        // inicializar unidades e categorias
        // ler ficheiros
    }
}