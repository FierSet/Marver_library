package com.aoz.marver_library

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ListAdapter(private val element: List<ListElement>) : RecyclerView.Adapter<ListAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val layoutInflater = LayoutInflater.from(p0.context).inflate(R.layout.unit_list, p0, false)
        return  ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
        p0.bind(element[p1])
    }

    override fun getItemCount(): Int = element.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {

        private val imagen  = view.findViewById<ImageView>(R.id.imagen_heroe)
        private val id = view.findViewById<TextView>(R.id.id_heroie_element)
        private val nombre = view.findViewById<TextView>(R.id.name_real)
        private val desc  = view.findViewById<TextView>(R.id.desc)

        @SuppressLint("SetTextI18n")
        fun bind(element: ListElement)
        {
            var direc = element.imagen
            val target = "http"
            direc = direc.replace(target, "https")
            Picasso.get().load( direc ).into(imagen)

            id.text = element.id.toString()
            nombre.text = element.name
            desc.text = "Last update: ${element.modif}"

            view.setOnClickListener {

                val fragmento = caracteristicas()

                val data = Bundle()
                data.putInt("id", element.id)
                data.putString("name", element.name)
                data.putString("desc", element.desc)
                data.putString("mod", element.modif)
                data.putString("imagen", direc)

                fragmento.arguments = data

                val activity = view.context as MainActivity


                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.base, fragmento)
                    .addToBackStack(null)
                    .commit()

            }
        }

        /*
        fun mensaje(mensaje : String)
        {
            Toast.makeText(view.context, mensaje, Toast.LENGTH_LONG).show()
        }
        */
    }
}