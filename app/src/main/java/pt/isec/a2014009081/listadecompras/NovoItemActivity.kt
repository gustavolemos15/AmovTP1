package pt.isec.a2014009081.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_novo_item.*

private const val CODIGO_CAMERA = 40
private const val CODIGO_GALERIA = 41

class NovoItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_item)


        btnGravar.setOnClickListener { onAdicionar(it) }

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

    fun criaToast(id:Int) {
        Toast.makeText(
            this@NovoItemActivity,
            resources.getString(id),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun editTextDialog(tipo: String) {
        val dlgBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dlgLayout = inflater.inflate(R.layout.edit_text_dialog, null)
        val et = dlgLayout.findViewById<EditText>(R.id.etDialog)
        val title = java.lang.String.format(resources.getString(R.string.dlgUniCatTitle),tipo)
        with(dlgBuilder) {
            setTitle(title)
            setPositiveButton(R.string.adicionar) {dialog, which ->
                // verificar se está vazia, se sim toast a dizer que nada foi inserido
                // fazer verificação do tipo para saber se foi categoria ou unidade
                // fazer a  verficação também em ingles
                Toast.makeText(
                    this@NovoItemActivity,
                    et.text.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
            setView(dlgLayout)
            setCancelable(true)
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menunovoitem, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //este when usa-se quando se tem mais que uma opção
        when (item.itemId) {
            R.id.menuCategoria -> editTextDialog(resources.getString(R.string.categoria))
            R.id.menuUnidade -> editTextDialog(resources.getString(R.string.unidade))
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
            val bitmap =
                MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun onCancelarNovoItem(view: View) {
        Toast.makeText(
            this@NovoItemActivity,
            "Alterações discartadas",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onAdicionar(view: View) {
        val nome = etDesignacao.text.toString()
        val strQuantidade = etQuantidade.text.toString()
        var quantidade = 0
        var marca = etMarca.text.toString()
        val strSpinnerUni = spinnerUnidades.selectedItem.toString()
        val strSpinnerCat = spinnerCategoria.selectedItem.toString()
        var notas = etNotas.text.toString()
        var imagem = imagemItem
        //continuar aqui
        //e adicionar permissao camara


        if(nome == null || nome.length < 2) {
            criaToast(R.string.nomeInvalido)
            return
        }
        if(strQuantidade == null || strQuantidade.length <= 0) {
            criaToast(R.string.quantidadeInvalida)
            return
        }else{
            try {
                quantidade = strQuantidade.toInt()
            }catch (e: NumberFormatException){
                criaToast(R.string.campoInvalido)
                return
            }
        }
                //TODO: get string Categoria e Quantidade e Marca, get foto

        val produto = Produto(nome, quantidade, marca, strSpinnerCat, strSpinnerUni, notas)


        val intent = Intent()
        //enviar objeto para a atividade
        intent.putExtra("PRODUTO", produto)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}


