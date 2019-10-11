package com.julio.carwashtogo3

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import androidx.navigation.findNavController
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.julio.carwashtogo3.common.Constantes
import com.julio.carwashtogo3.ui.administrador.promocion.ListarPromociones

class MainActivity : AppCompatActivity() ,ListarPromociones.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_listar_empresas,
                R.id.navigation_listar_paquetes,
                R.id.navigation_crear_empresas,
                R.id.navigation_agregar_promociones,
                R.id.navigation_lista_encargados,
                R.id.navigation_nuevo_encargado,
                R.id.navigation_perfil,
                R.id.navigation_cuponera,
                R.id.navigation_catalogo_productos,
                R.id.navigation_nuevo_paquete
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val datos : Bundle? =intent.extras
        if (datos != null){
            val rol : String?  =datos.getString(Constantes.ROL_USER)

            if (rol != null){
                hideOptionMenu(rol)
                setNavigationDestination(rol)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //oculta las opciones de menu segun el tipo de usuario
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
                menu.findItem(R.id.navigation_cuponera).isVisible = true
                menu.findItem(R.id.navigation_add_vehiculo).isVisible = true
                menu.findItem(R.id.navigation_catalogo_productos).isVisible = true
            }
            "administrador" -> {
                //cliente desactivados
                menu.findItem(R.id.navigation_cuponera).isVisible = false
                menu.findItem(R.id.navigation_add_vehiculo).isVisible = false
                menu.findItem(R.id.navigation_catalogo_productos).isVisible = false

                //administrado
                 menu.findItem(R.id.navigation_listar_paquetes).isVisible = true
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

    //establece el fragment de inicio para cada tipo de usuario
    //tipo: String, navigationView: NavigationView, navController: NavController
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
                        graph.startDestination = R.id.navigation_listar_paquetes
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


    }
}
