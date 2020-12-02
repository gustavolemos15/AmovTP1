package pt.isec.a2014009081.listadecompras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_gestao_dados.*
import pt.isec.a2014009081.listadecompras.fragments_gestao_dados.CategoriasFragment
import pt.isec.a2014009081.listadecompras.fragments_gestao_dados.UnidadesFragment
import pt.isec.a2014009081.listadecompras.fragments_gestao_dados.adapters.ViewPagerAdapter

//link do video de referencia: https://www.youtube.com/watch?v=ZxK7GomWRP8&t

class GestaoDadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestao_dados)
        setUpTabs()
    }

    private fun setUpTabs () {

val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(CategoriasFragment(), getString(R.string.categorias))
        adapter.addFragment(UnidadesFragment(), getString(R.string.unidades))
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_category_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_calculate_24)
    }
}
