package com.aoz.marver_library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso


class caracteristicas : Fragment() {

    lateinit var rootView: View
    lateinit var cerrar : Button

    lateinit var id : TextView
    lateinit var nombre : TextView
    lateinit var desc : TextView
    lateinit var mod : TextView
    lateinit var imagen : ImageView
    lateinit var base_transp : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        rootView = inflater.inflate(R.layout.fragment_caracteristicas, container, false)

        cerrar = rootView.findViewById(R.id.cerrar)
        base_transp = rootView.findViewById(R.id.base_trassp)

        id = rootView.findViewById(R.id.id)
        nombre = rootView.findViewById(R.id.name)
        desc = rootView.findViewById(R.id.description_unit)
        mod = rootView.findViewById(R.id.last_update)
        imagen = rootView.findViewById(R.id.imagen_heroe_unit)

        val bundle = arguments
        val ids = bundle!!.getInt("id")
        val names = bundle.getString("name")
        val descs = bundle.getString("desc")
        val mods = bundle.getString("mod")
        val imagens = bundle.getString("imagen")

        id.text = ids.toString()
        nombre.text = names
        desc.text = descs
        mod.text = mods

        Picasso.get().load( imagens ).into(imagen)

        cerrar.setOnClickListener {

            val activity = context as MainActivity
            activity.supportFragmentManager.beginTransaction().remove(this).commit()
        }

        base_transp.setOnClickListener {
            val activity = context as MainActivity
            activity.supportFragmentManager.beginTransaction().remove(this).commit()
        }
        return rootView
    }

}