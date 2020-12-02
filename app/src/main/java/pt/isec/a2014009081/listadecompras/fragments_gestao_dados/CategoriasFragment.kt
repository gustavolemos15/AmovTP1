package pt.isec.a2014009081.listadecompras.fragments_gestao_dados

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_categorias.*
import pt.isec.a2014009081.listadecompras.GestaoDadosActivity
import pt.isec.a2014009081.listadecompras.MainActivity
import pt.isec.a2014009081.listadecompras.R


class CategoriasFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val context = context as GestaoDadosActivity

       // val valores = resources.getStringArray(R.array.categorias_array)
        // val adapter = ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,valores)
       //  lvCategorias.adapter = adapter

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorias, container, false)
    }

}