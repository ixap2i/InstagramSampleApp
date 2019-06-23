package com.ixap2i.floap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.facebook.login.widget.LoginButton
import com.facebook.login.LoginManager

import android.content.Intent
import android.util.Log
import android.widget.LinearLayout
import com.facebook.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.Arrays

class MainActivity : AppCompatActivity() {
    lateinit var loginButton: LoginButton
    lateinit var callbackManager: CallbackManager
    lateinit var accessTokenTraker: AccessTokenTracker
    lateinit var imagesTable: LinearLayout
    val accessToken = AccessToken.getCurrentAccessToken()
    val isLoggedIn = accessToken != null && !accessToken.isExpired


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val viewModel: ImageListViewModel by viewModel()

        val appModule = module {
            single { FloapApplication() }
            single { KtorFloapApi() }
            single<ImageRepository> { KtorFloapApi() }
            viewModel {
                ImageListViewModel(application)
            }
        }

        startKoin {
            // declare used Android context
            androidContext(this@MainActivity)
            // declare modules
            modules(appModule)
        }
        loginButton = findViewById(R.id.login_button)
        imagesTable = findViewById(R.id.table_view)

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().logInWithReadPermissions(this@MainActivity, Arrays.asList("email"))

        accessTokenTraker  = LoginServiceImpl().checkLoginStatus(imagesTable)
        LoginServiceImpl().loginCallBack(loginButton, isLoggedIn, imagesTable, callbackManager)

        val imgRepo = get<ImageRepository>()
        GlobalScope.launch {
            val result = imgRepo.getUserImage()
            var datas = (result as Result.Success).value
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
