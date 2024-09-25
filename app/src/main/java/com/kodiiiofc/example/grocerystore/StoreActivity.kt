package com.kodiiiofc.example.grocerystore

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.E

class StoreActivity : AppCompatActivity(), Removable, Updatable {

    private val GALLERY_REQUEST = 302
    private val EDIT_REQUEST = 303

    var photoUri: Uri? = null
    val noImageString = "android.resource://com.kodiiiofc.example.grocerystore/drawable/noimage"

    var groceryList: MutableList<Grocery> = mutableListOf()
    var listAdapter: ListAdapter? = null
    var item: Int? = null
    var grocery: Grocery? = null

    private lateinit var nameET: EditText
    private lateinit var priceET: EditText
    private lateinit var descriptionET: EditText

    private lateinit var imageInputIV: ImageView

    private lateinit var submitBTN: Button

    private lateinit var groceriesLV: ListView

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        nameET = findViewById(R.id.et_name)
        priceET = findViewById(R.id.et_price)
        descriptionET = findViewById(R.id.et_description)

        imageInputIV = findViewById(R.id.iv_image_input)

        submitBTN = findViewById(R.id.btn_submit)

        groceriesLV = findViewById(R.id.lv_groceries)

        listAdapter = ListAdapter(this, groceryList)
        groceriesLV.adapter = listAdapter

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        imageInputIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        submitBTN.setOnClickListener {
            val name = nameET.text.toString().trim()
            val price = priceET.text.toString()
            val description = descriptionET.text.toString().trim()
            val image = if (photoUri == null) noImageString else photoUri.toString()

            val grocery = Grocery(name, description, price, image)
            groceryList.add(grocery)
            listAdapter?.notifyDataSetChanged()

            photoUri = null
            nameET.text.clear()
            descriptionET.text.clear()
            priceET.text.clear()
            imageInputIV.setImageResource(R.drawable.noimage)
        }

        groceriesLV.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            grocery = listAdapter!!.getItem(position)
            item = position
            val dialog = MyAlertDialog()
            val args = Bundle()
            args.putSerializable("grocery", grocery)
            dialog.arguments = args
            dialog.show(supportFragmentManager, "custom")
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
                    photoUri = data?.data
                    imageInputIV.setImageURI(photoUri)
                }
            }
        }
    }

    override fun remove(grocery: Grocery) {
        listAdapter?.remove(grocery)
    }

    private val launchDetailsActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->

        if (result.resultCode == RESULT_OK) {
            val data = result.data
            groceryList = data?.extras?.getSerializable("list") as MutableList<Grocery>

            listAdapter = ListAdapter(this, groceryList)
            groceriesLV.adapter = listAdapter

        }

    }

    override fun update(grocery: Grocery) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("grocery", grocery)
        intent.putExtra("list", groceryList as ArrayList<Grocery>)
        intent.putExtra("position", item)

        launchDetailsActivity.launch(intent)
    }

}