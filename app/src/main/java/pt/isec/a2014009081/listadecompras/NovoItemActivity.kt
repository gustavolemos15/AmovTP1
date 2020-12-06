package pt.isec.a2014009081.listadecompras

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_novo_item.*


private const val CODIGO_CAMERA = 40
private const val CODIGO_GALERIA = 41

class NovoItemActivity : AppCompatActivity() {

    lateinit var categorias : ArrayList<String>
    lateinit var unidades : ArrayList<String>
    lateinit var foto :Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_item)

        categorias = intent.getSerializableExtra("CATEGORIAS") as ArrayList<String>
        unidades = intent.getSerializableExtra("UNIDADES") as ArrayList<String>

        btnGravar.setOnClickListener { onAdicionar(it) }

        if (checkPermission()) {

        } else {
            requestPermission();
        }

        // Se calhar devemos meter isto numa função para tirar do mai
        // https://developer.android.com/guide/topics/ui/controls/spinner
        val spnUni: Spinner = findViewById(R.id.spinnerUnidades)
        ArrayAdapter(
            this, android.R.layout.simple_spinner_item, unidades
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spnUni.adapter = adapter
        }

        val spnCat: Spinner = findViewById(R.id.spinnerCategoria)
        ArrayAdapter(
            this, android.R.layout.simple_spinner_item, categorias
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spnCat.adapter = adapter
        }
        //! Se calhar devemos meter isto numa função para tirar do main

    }

    private fun checkPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            false
        } else true
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            CODIGO_CAMERA
        )
    }

    fun criaToast(id: Int) {
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
        val title = java.lang.String.format(resources.getString(R.string.dlgUniCatTitle), tipo)
        with(dlgBuilder) {
            setTitle(title)
            setPositiveButton(R.string.adicionar) { dialog, which ->
                if(tipo == "Categoria")
                categorias.add(et.text.toString())
                else
                    unidades.add(et.text.toString())
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
            .setPositiveButton("Galeria", { dialog, which -> acederGaleria() })
            .setNegativeButton("Camara", { dialog, which -> acederCamara() })
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
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
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
            foto = fotoCapturada

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
        //produto.imagem = foto


        val intent = Intent()
        //enviar objeto para a atividade
        intent.putExtra("PRODUTO", produto)
        intent.putExtra("CATEGORIAS", categorias)
        intent.putExtra("UNIDADES", unidades)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}


