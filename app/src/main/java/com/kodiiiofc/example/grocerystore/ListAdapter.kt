package com.kodiiiofc.example.grocerystore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(context: Context, groceryList: MutableList<Grocery>) : ArrayAdapter<Grocery>(context, R.layout.list_item, groceryList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val grocery = getItem(position)
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val imageViewIV = view?.findViewById<ImageView>(R.id.iv_image)
        val nameTV = view?.findViewById<TextView>(R.id.tv_name)
        val priceTV = view?.findViewById<TextView>(R.id.tv_price)

        imageViewIV?.setImageBitmap(grocery?.image)
        nameTV?.text = grocery?.name
        priceTV?.text = "${grocery?.price} \u20bd"

        return view!!
    }

}