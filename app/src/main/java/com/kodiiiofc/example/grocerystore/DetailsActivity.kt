package com.kodiiiofc.example.grocerystore

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailsActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var nameTV: TextView
    private lateinit var priceTV: TextView
    private lateinit var descriptionTV: TextView

    private lateinit var imageIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        imageIV = findViewById(R.id.iv_image)
        nameTV = findViewById(R.id.tv_name)
        priceTV = findViewById(R.id.tv_price)
        descriptionTV = findViewById(R.id.tv_description)

        val grocery = intent.getSerializableExtra("grocery") as Grocery?

        nameTV.text = "${nameTV.text} ${grocery?.name}"
        priceTV.text = "${priceTV.text} ${grocery?.price} руб."
        descriptionTV.text = "${descriptionTV.text} ${grocery?.despription}"

        val image = grocery?.image

        imageIV.setImageURI(Uri.parse((image)))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_exit -> {
                Toast.makeText(applicationContext, "Приложение закрыто", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}