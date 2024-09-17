package com.kodiiiofc.example.grocerystore

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class StoreActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 302
    var bitmap: Bitmap? = null
    var groceryList: MutableList<Grocery> = mutableListOf()

    private lateinit var nameET: EditText
    private lateinit var priceET: EditText

    private lateinit var imageInputIV: ImageView

    private lateinit var submitBTN: Button

    private lateinit var groceriesLV: ListView

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        nameET = findViewById(R.id.et_name)
        priceET = findViewById(R.id.et_price)

        imageInputIV = findViewById(R.id.iv_image_input)

        submitBTN = findViewById(R.id.btn_submit)

        groceriesLV = findViewById(R.id.lv_groceries)

        val listAdapter = ListAdapter(this, groceryList)
        groceriesLV.adapter = listAdapter

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        imageInputIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        submitBTN.setOnClickListener {
            val name = nameET.text.toString()
            val price = priceET.text.toString()
            val image = bitmap

            val grocery = Grocery(name, price, image)

            groceryList.add(grocery)

            listAdapter.notifyDataSetChanged()

            nameET.text.clear()
            priceET.text.clear()
            imageInputIV.setImageResource(R.drawable.noimage)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_exit -> {
                Toast.makeText(applicationContext, "Приложение закрыто", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageInputIV = findViewById(R.id.iv_image_input)
        when (requestCode) {
            GALLERY_REQUEST -> {
                if (resultCode === RESULT_OK) {
                    val selectedImage: Uri? = data?.data
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    imageInputIV.setImageBitmap(bitmap)
                }
            }
        }
    }

}