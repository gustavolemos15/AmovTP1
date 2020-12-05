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
        var p1 = Produto("Batatas", 3, "Pingo Doce", "Legumes", "Unidades")
        listas[0].lista.add(p1)
        var p2 = Produto("Leite", 6, "Pingo Doce", "Legumes", "Litros")
        listas[0].lista.add(p2)
        produtos.add(p2)
        // inicializar unidades e categorias
        // ler ficheiros
    }
}