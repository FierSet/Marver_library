package com.aoz.marver_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

val public_key = "4153bb800cda6963d152f7c06d116851"
val private_key = "8453fd9f397f24663ee23ededb7d9b38da49605e"
val hash = "ed3ebe17a7443217d3cc803de8fda7dc"
val ts = "3000"
val tipo = "characters"
var filtro = ""

var Element = ArrayList<ListElement>()

lateinit var lista : RecyclerView
lateinit var  entratatexto : EditText
lateinit var cantidad_elementos : TextView
class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cantidad_elementos = findViewById(R.id.contador_label)
        entratatexto = findViewById(R.id.nombre)
        lista = findViewById(R.id.lista)

        entratatexto.addTextChangedListener(object : TextWatcher
        {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
            {
                if(entratatexto.text == null)
                    filtro = ""
                else
                    filtro = "nameStartsWith=${entratatexto.text.toString()}&"

                acceda_a_db()
            }
        })
        acceda_a_db()
    }

    private fun links() : String
    {
        return "https://gateway.marvel.com/v1/public/$tipo?" + filtro + "ts=$ts&apikey=$public_key&hash=$hash"
    }

    private fun acceda_a_db()
    {
        val queue = Volley.newRequestQueue(this)
        val stringRequest =  StringRequest(Request.Method.GET, links(),
            Response.Listener<String>
            { response ->
                extracionjason(response)
            },
            Response.ErrorListener
            {
                mensaje("error. ")
            })
        queue.add(stringRequest)
    }

    private fun extracionjason(dato : String)
    {
        Element.clear()

        try
        {
            val data = JSONObject(dato)

            val datas = data.getJSONObject("data")

            val elementheroes = datas.getJSONArray("results")

            cantidad_elementos.text = "${elementheroes.length()} Elements"

            for ( i in ( 0 until elementheroes.length() ) )
            {
                val obj = elementheroes.getJSONObject(i)

                val imagen = obj.getJSONObject("thumbnail")
                val linkimag = imagen["path"].toString() + "." + imagen["extension"].toString()

                Element.add(
                    ListElement(obj.optInt("id"),
                                obj.optString("name"),
                                obj.optString("description"),
                                obj.optString("modified"),
                                linkimag),
                )

            }
            lista.layoutManager = LinearLayoutManager(this)
            val adapter = ListAdapter(Element)
            lista.adapter = adapter

        }
        catch (e: JSONException)
        {
            mensaje("JSON Error. ${e.message.toString()}")
        }
    }

    private fun mensaje(mensaje : String)
    {
        Toast.makeText(applicationContext, mensaje, Toast.LENGTH_LONG).show()
    }


}