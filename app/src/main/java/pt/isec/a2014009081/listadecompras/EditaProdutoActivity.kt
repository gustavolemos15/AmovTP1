package pt.isec.a2014009081.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edita_produto.*
import kotlinx.android.synthetic.main.activity_novo_item.*

private const val CODIGO_CAMERA = 40
private const val CODIGO_GALERIA = 41

class EditaProdutoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edita_produto)

        var produto = intent.getSerializableExtra("PROD") as Produto

        btnEditaGravar.setOnClickListener { onAdicionar(it) }

        etEditarDesignacao.setText(produto.nome)
        etEditarMarca.setText(produto.marca)
        etEditarQuantidade.setText(produto.quantidade.toString())
        etEditarNotas.setText(produto.notas)
        //spnEditarCategoria.setSelection(1)
        //spnEditarUnidades.setSelection(1)
    }

    fun criaToast(id:Int) {
        Toast.makeText(
            this@EditaProdutoActivity,
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
            setPositiveButton(R.string.adicionar) { dialog, which ->
                // verificar se está vazia, se sim toast a dizer que nada foi inserido
                // fazer verificação do tipo para saber se foi categoria ou unidade
                // fazer a  verficação também em ingles
                Toast.makeText(
                    this@EditaProdutoActivity,
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
            ivEditarImagem.setImageBitmap(fotoCapturada)

        }

        if (requestCode == CODIGO_GALERIA && resultCode == Activity.RESULT_OK) {
            val uri = data!!.data
            ivEditarImagem.setImageURI(uri)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun onCancelarNovoItem(view: View) {
        Toast.makeText(
            this@EditaProdutoActivity,
            "Alterações discartadas",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onAdicionar(view: View) {
        val nome = etEditarDesignacao.text.toString()
        val strQuantidade = etEditarQuantidade.text.toString()
        var quantidade = 0
        var marca = etEditarMarca.text.toString()
        val strSpinnerUni = " "//spnEditarUnidades.selectedItem.toString()
        val strSpinnerCat = " "//spnEditarCategoria.selectedItem.toString()
        var notas = etEditarNotas.text.toString()
        var imagem = ivEditarImagem
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