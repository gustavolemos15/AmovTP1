package pt.isec.a2014009081.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_novo_item.*
import kotlinx.android.synthetic.main.activity_novo_item.view.*

private const val CODIGO_CAMERA = 40
private const val CODIGO_GALERIA = 41

class NovoItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_item)

        //recebe dados
        val principal = intent.getSerializableExtra("PRINCIPAL") as? Principal
        val idLista = intent.getIntExtra("POSITION",0)
        val lista = principal?.listas?.get(idLista)

        // Se calhar devemos meter isto numa função para tirar do mai
        // https://developer.android.com/guide/topics/ui/controls/spinner
        val spnUni: Spinner = findViewById(R.id.spinnerUnidades)
        ArrayAdapter.createFromResource(
            this,
            R.array.unidades_array,
            android.R.layout.simple_spinner_item
        ).also {adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spnUni.adapter = adapter
        }

        val spnCat: Spinner = findViewById(R.id.spinnerCategoria)
        ArrayAdapter.createFromResource(
            this,
            R.array.categorias_array,
            android.R.layout.simple_spinner_item
        ).also {adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spnCat.adapter = adapter
        }
        //! Se calhar devemos meter isto numa função para tirar do main

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menunovoitem, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //este when usa-se quando se tem mais que uma opção
        when (item.itemId) {
            R.id.menuCategoria -> Toast.makeText(this,"Fazer DialogBox para introduzir nova categoria", Toast.LENGTH_SHORT).show()
            R.id.menuUnidade -> Toast.makeText(this,"Fazer DialogBox para introduzir nova unidade", Toast.LENGTH_SHORT).show()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun onDialogFoto(view: View) {
        val dlg = AlertDialog.Builder(this)
            .setTitle("Origem da foto")
            .setPositiveButton("Galeria", {dialog, which ->  acederGaleria()})
            .setNegativeButton("Camara", {dialog, which -> acederCamara()})
            .setCancelable(true)
            .create()

        dlg.show()
    }

    private fun acederCamara() {

        //intent para lançar a app da camara
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if(takePictureIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(takePictureIntent, CODIGO_CAMERA)
        } else {
            Toast.makeText(this,"Unable to open camera", Toast.LENGTH_SHORT).show()
        }

    }

    private fun acederGaleria() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, CODIGO_GALERIA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
            val fotoCapturada = data?.extras?.get("data") as Bitmap
            imagemItem.setImageBitmap(fotoCapturada)
        }

        if (requestCode == CODIGO_GALERIA && resultCode == Activity.RESULT_OK) {
            val uri = data!!.data
            imagemItem.setImageURI(uri)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun onAdicionar(view: View, principal: Principal, idLista: Int) {

        //TOD Completar com o resto dos atributos e fazer validacao
        /*
        val nome = view.etDesignacao.text.toString()
        val quantidade = view.etQuantidade.text.toString().toInt()

        principal.listas[idLista].lista.add(Produto(nome, quantidade, "marca", "categoria"))
        val intent = Intent(this,EdicaoListaActivity::class.java)
        //enviar objeto para a atividade
        intent.putExtra("PRINCIPAL", principal)
        intent.putExtra("POSITION", idLista)
        startActivity(intent)*/



    }
}


