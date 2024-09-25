package com.kodiiiofc.example.grocerystore

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var nameET: EditText
    private lateinit var priceET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var saveBTN: Button

    private lateinit var imageIV: ImageView

    val GALLERY_REQUEST = 402

    var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        imageIV = findViewById(R.id.iv_image)
        nameET = findViewById(R.id.et_name)
        priceET = findViewById(R.id.et_price)
        descriptionET = findViewById(R.id.et_description)
        saveBTN = findViewById(R.id.btn_save)

        val grocery = intent.getSerializableExtra("grocery") as Grocery?

        nameET.setText("${grocery?.name}")
        priceET.setText("${grocery?.price}")
        descriptionET.setText("${grocery?.despription}")

        val list = intent.extras?.getSerializable("list") as MutableList<Grocery>
        val item = intent.extras?.getInt("position")


        photoUri = Uri.parse(grocery?.image)

        imageIV.setImageURI(photoUri)

        imageIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        saveBTN.setOnClickListener {
            val name = nameET.text.toString().trim()
            val price = priceET.text.toString()
            val description = descriptionET.text.toString().trim()
            val image = photoUri.toString()

            val editedGrocery = Grocery(name, description, price, image)
            list.set(item!!, editedGrocery)
            val intent = Intent()
            intent.putExtra("list", list as ArrayList<Grocery>)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_exit -> {
                Toast.makeText(applicationContext, "Приложение закрыто", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
            R.id.menu_back -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageIV = findViewById(R.id.iv_image)
        when (requestCode) {
            GALLERY_REQUEST -> {
                if (resultCode === RESULT_OK) {
                    photoUri = data?.data
                    imageIV.setImageURI(photoUri)
                }
            }
        }
    }
}