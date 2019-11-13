package com.julio.carwashtogo3

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth

class MenuClienteActivity : AppCompatActivity() {

   private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_cliente)

        val navView : BottomNavigationView = findViewById(R.id.nav_view_cliente)

        val navController = findNavController(R.id.nav_host_fragment2)

        mAuth = FirebaseAuth.getInstance()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_catalogo_productos,R.id.navigation_compras,R.id.navigation_perfil)
        )
        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.action_settings -> {
                return true
            }
            R.id.action_acerca -> {
                val builder = AlertDialog.Builder(this@MenuClienteActivity)
                builder.setTitle(getString(R.string.acercaDe))
                builder.setMessage(Html.fromHtml(
                        "Julio Roberto Olivares Perez<br>"+
                                "Juan Carlos Garcia Santos<br>"+
                                "Marvin Josue Cortez Rodas<br>"+
                                "Isaac Everaldo Molina Ponce<br>"+
                                "Rebeca Sarai Orellana Mendez<br>"+
                                "Cristian Adalberto Torres Alfaro"
                    )
                )
                builder.setPositiveButton("OK"){
                    _,_->
                }
                val dialog : AlertDialog = builder.create()
                dialog.show()
                return  true
            }
            R.id.action_cerrar_sesion ->{
                mAuth.signOut()
                startActivity(Intent(applicationContext,LoginActivity::class.java))
                this@MenuClienteActivity.finish()
                return true
            }
            R.id.action_salir ->{
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}
