package com.ixap2i.floap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.facebook.login.widget.LoginButton
import com.facebook.login.LoginManager

import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.Arrays
import android.os.StrictMode



class MainActivity : AppCompatActivity() {
    lateinit var loginButton: LoginButton
    lateinit var callbackManager: CallbackManager
    lateinit var accessTokenTraker: AccessTokenTracker
    lateinit var imagesTable: LinearLayout
    lateinit var warnText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var images: List<ThumbnailImage>

    val accessToken = AccessToken.getCurrentAccessToken()
    val isLoggedIn = accessToken != null && !accessToken.isExpired

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val appModule = module {
            single { FloapApplication() }
            single { KtorFloapApi() }
            single<ImageRepository> { KtorFloapApi() }
        }

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        loginButton = findViewById(R.id.login_button)
        imagesTable = findViewById(R.id.table_view)
        warnText = findViewById(R.id.warnText)
        viewManager = LinearLayoutManager(this@MainActivity)
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().logInWithReadPermissions(this@MainActivity, Arrays.asList("email"))

        accessTokenTraker = LoginServiceImpl().checkLoginStatus(imagesTable)
        LoginServiceImpl().loginCallBack(loginButton, isLoggedIn, imagesTable, callbackManager)
        val imgRepo = get<ImageRepository>()

        try {
            GlobalScope.launch(Dispatchers.Main) {
                val result = imgRepo.getUserImage()

                if(result.isSuccess()) {
                    images = (result as Result.Success).value.data
                    viewAdapter = ImageAdapter(images)
                    recyclerView = findViewById<RecyclerView>(R.id.main_album_rows).apply {
                        setHasFixedSize(true)
                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                } else {
                    warnText.visibility = View.VISIBLE
                }
            }
        } catch(ex: Exception) {
            ex.localizedMessage
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
