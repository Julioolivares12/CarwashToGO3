package com.julio.carwashtogo3

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import androidx.navigation.findNavController
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.julio.carwashtogo3.common.Constantes
import com.julio.carwashtogo3.model.CompraPaquete
import com.julio.carwashtogo3.ui.administrador.Pedidos.PedidoFragment
import com.julio.carwashtogo3.ui.administrador.promocion.ListarPromociones
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity() ,ListarPromociones.OnFragmentInteractionListener,PedidoFragment.OnListFragmentInteractionListener {
    lateinit var mAuth : FirebaseAuth
    override fun onFragmentInteraction(uri: Uri?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onListFragmentInteraction(item: CompraPaquete?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        mAuth = FirebaseAuth.getInstance()
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_listar_empresas,
                R.id.navigation_listarPaquetes,
                R.id.navigation_crear_empresas,
                R.id.navigation_agregar_promociones,
                R.id.navigation_lista_encargados,
                R.id.navigation_nuevo_encargado,
                R.id.navigation_perfil,
                R.id.navigation_lista_pedidos
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val datos : Bundle? =intent.extras
        if (datos != null){
            val rol : String?  =datos.getString(Constantes.ROL_USER)

            if (rol != null){
                //hideOptionMenu(rol)
                //setNavigationDestination(rol)
                //suscribirUsuarioPaquetes(rol)
                //suscribirUsuarioAPromociones(rol)
            }


        }

    }

    fun enviarTokenToServer(){

    }
    fun suscribirUsuarioPaquetes(rol : String){
        if (rol == "cliente"){
            FirebaseMessaging.getInstance().subscribeToTopic("paquetes").addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(applicationContext,"suscrito",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun suscribirUsuarioAPromociones(rol : String){
        if (rol == "cliente"){
            FirebaseMessaging.getInstance().subscribeToTopic("promociones").addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(applicationContext,"suscrito a promociones",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    //programacion opciones menu action bar
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

//            R.id.action_location -> {
//                val intent = Intent(this@MainActivity, MapsActivity::class.java)
//                startActivity(intent)
//                return true
//            }
            R.id.action_acerca-> {

                // Initialize a new instance of
                val builder = AlertDialog.Builder(this@MainActivity)

                // Set the alert dialog title
                builder.setTitle("Acerca de Carwash TO Go")

                // Display a message on alert dialog
                builder.setMessage(Html.fromHtml("Julio Roberto Olivares Perez<br>"+
                        "Juan Carlos Garcia Santos<br>"+
                        "Marvin Josue Cortez Rodas<br>"+
                        "Isaac Everaldo Molina Ponce<br>"+
                        "Rebeca Sarai Orellana Mendez<br>"+
                        "Cristian Adalberto Torres Alfaro"))


                // Set a positive button and its click listener on alert dialog
                builder.setPositiveButton("OK"){dialog, which ->
                    // Do something when user press the positive button
                    //Toast.makeText(applicationContext,"Ok, we change the app background.",Toast.LENGTH_SHORT).show()

                }


                // Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()

                // Display the alert dialog on app interface
                dialog.show()

                return true
            }
            R.id.action_cerrar_sesion -> {

                mAuth.signOut()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                this@MainActivity.finish()
                return true
            }
            R.id.action_salir -> {

                mAuth.signOut()
                finishAffinity()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //oculta las opciones de menu segun el tipo de usuario
    /*
    private fun hideOptionMenu(tipoUser: String) {
        val navView : NavigationView = findViewById(R.id.nav_view)
        val menu = navView.menu
        when (tipoUser) {
            "cliente" -> {
                //administrador desactivados
                menu.findItem(R.id.navigation_agregar_promociones).isVisible = false
                menu.findItem(R.id.navigation_crear_empresas).isVisible = false
                menu.findItem(R.id.navigation_lista_encargados).isVisible = false
                menu.findItem(R.id.navigation_nuevo_encargado).isVisible = false
                menu.findItem(R.id.navigation_cuponera).isVisible = false
                menu.findItem(R.id.navigation_add_vehiculo).isVisible = false
                menu.findItem(R.id.navigation_nuevo_paquete).isVisible = false

                //cliente activados
                menu.findItem(R.id.navigation_cuponera).isVisible = false
                menu.findItem(R.id.navigation_add_vehiculo).isVisible = false
                menu.findItem(R.id.navigation_catalogo_productos).isVisible = true
            }
            "administrador" -> {
                //cliente desactivados
                menu.findItem(R.id.navigation_cuponera).isVisible = false
                menu.findItem(R.id.navigation_add_vehiculo).isVisible = false
                menu.findItem(R.id.navigation_catalogo_productos).isVisible = false

                //administrado
                 menu.findItem(R.id.navigation_listarPaquetes).isVisible = true
                menu.findItem(R.id.navigation_agregar_promociones).isVisible = true
                menu.findItem(R.id.navigation_crear_empresas).isVisible = true
                menu.findItem(R.id.navigation_lista_encargados).isVisible = true
                menu.findItem(R.id.navigation_nuevo_encargado).isVisible = true
                menu.findItem(R.id.navigation_nuevo_paquete).isVisible = true
            }
            "encargado" -> {
            }
        }
    }

     */

    //establece el fragment de inicio para cada tipo de usuario
    //tipo: String, navigationView: NavigationView, navController: NavController
    /*
    private fun setNavigationDestination(tipo: String) {


        val navigationView : NavigationView = findViewById(R.id.nav_view)
        val navController : NavController = findNavController(R.id.nav_host_fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        if (navHostFragment != null) {
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.mobile_navigation)
            if (inflater != null) {
                when (tipo) {
                    "administrador" -> {
                        //NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                        //assert navHostFragment != null;
                        //inflater = navHostFragment.getNavController().getNavInflater();
                        //NavGraph graph = inflater.inflate(R.navigation.mobile_navigation);
                        graph.startDestination = R.id.navigation_listar_empresas
                        //graph.startDestination = R.id.navigation_listar_paquetes
                        //NavigationView navigationView = findViewById(R.id.nav_view);
                        NavigationUI.setupWithNavController(navigationView, navController)
                    }
                    "cliente" -> {

                        graph.startDestination = R.id.navigation_catalogo_productos
                        NavigationUI.setupWithNavController(navigationView, navController)
                    }
                }
            }
        }


    }*/
}
