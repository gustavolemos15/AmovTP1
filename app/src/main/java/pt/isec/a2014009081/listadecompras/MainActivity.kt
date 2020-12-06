package pt.isec.a2014009081.listadecompras

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.edit_text_dialog.view.*
import java.io.*
import java.lang.Exception
import java.lang.IllegalStateException

// Activity em vez de AppCompatActivity para tirar a titlebar
class MainActivity : Activity() {

    lateinit var principal: Principal
    private val ADD_LISTA = 3
    private val LISTAS_ANTERIORES= 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //"class main" -> nome principal
        principal = Principal()

        val gson = Gson()
        var text = ""
        //Make sure to use a try-catch statement to catch any errors
        try {
            val filePath: String = this.filesDir.toString() + "/" + "Save"
            val myFile = File(filePath)
            //Make an InputStream with your File in the constructor
            val inputStream: InputStream = FileInputStream(myFile)
            val stringBuilder = StringBuilder()
            //Check to see if your inputStream is null
            //If it isn't use the inputStream to make a InputStreamReader
            //Use that to make a BufferedReader
            //Also create an empty String
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                //Use a while loop to append the lines from the Buffered reader
                while (bufferedReader.readLine().also({ receiveString = it }) != null) {
                    stringBuilder.append(receiveString)
                }
                //Close your InputStream and save stringBuilder as a String
                inputStream.close()
                text = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            //Log your error with Log.e
        } catch (e: IOException) {
            //Log your error with Log.e
        }catch (e: Exception){

        }
        try {
            principal = gson.fromJson(text, Principal::class.java)
        }catch (e: IllegalStateException){
            saveData()
        }


        //id do botao
        btnListasAnteriores.setOnClickListener{ onListasAnteriores(it, principal) }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_LISTA) {
            if (resultCode == Activity.RESULT_OK) {
                // 3
                var lista = data?.getSerializableExtra("LISTA") as Lista?
                if(lista != null) {
                    principal.listas.add(0, lista)
                    saveData()
                }
            }
        }
        if(requestCode == LISTAS_ANTERIORES) {
            if (resultCode == Activity.RESULT_OK) {
                // 3
                var p = data?.getSerializableExtra("PRINCIPAL") as Principal?
                if(p != null) {
                    principal = p
                    saveData()
                }
            }
        }
    }

    fun saveData(){
        val gson = Gson()
        val principalJson: String = gson.toJson(principal)
        //Get  FilePath and use it to create File
        val filePath = this.filesDir.toString() + "/" + "Save"
        val myfile = File(filePath)
        //Create FileOutputStream, yourFile is part of the constructor
        val fileOutputStream = FileOutputStream(myfile)
        //Convert your JSON String to Bytes and write() it
        fileOutputStream.write(principalJson.toByteArray())
        //Finally flush and close your FileOutputStream
        fileOutputStream.flush()
        fileOutputStream.close()
        println("dados guardados")
    }

    fun onNovaLista(view: View) {
        val dlgBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dlgLayout = inflater.inflate(R.layout.edit_text_dialog, null)
        val editText = dlgLayout.etDialog
        val intent = Intent(this, EdicaoListaActivity::class.java)

        with(dlgBuilder) {
            setTitle(R.string.primeiroBotao)
            setPositiveButton(R.string.adicionar) { dialog, which ->
                // verificar se está vazia, se sim toast a dizer que nada foi inserido
                if(editText.text.toString().isEmpty())
                    return@setPositiveButton
                intent.putExtra("LISTA", Lista(editText.text.toString()))
                startActivityForResult(intent, ADD_LISTA)
            }
            setView(dlgLayout)
            setCancelable(true)
            show()
        }

    }

    fun onListasAnteriores(view: View, principal: Principal) {
        val intent = Intent(this, ListasAnterioresActivity::class.java)
        //enviar objeto para a atividade
        intent.putExtra("PRINCIPAL", principal)
        startActivityForResult(intent, LISTAS_ANTERIORES)
    }

    fun onGestaoDados(view: View) {
        val intent = Intent(this, GestaoDadosActivity::class.java)
        startActivity(intent)
    }

}

