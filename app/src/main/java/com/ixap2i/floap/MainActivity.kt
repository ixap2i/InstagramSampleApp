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
import io.ktor.application.Application
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module
import java.lang.reflect.ParameterizedType
import java.util.*

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


        startKoin {
            // Android context
            androidContext(this@MainActivity)
            // modules
        }


        val hello = module {
            single<ImageService> { ImageServiceImpl(get()) }
        }

        hello.apply {

        }
        val qualifier: Qualifier? = null
        val parameters: ParametersDefinition? = null

        val sample = getKoin().get<ImageServiceImpl>(qualifier, parameters).getCode()
        Log.d("", "${sample}")

        loginButton = findViewById(R.id.login_button)
        imagesTable = findViewById(R.id.table_view)

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().logInWithReadPermissions(this@MainActivity, Arrays.asList("email"))

        accessTokenTraker  = LoginServiceImpl().checkLoginStatus(imagesTable)
        LoginServiceImpl().loginCallBack(loginButton, isLoggedIn, imagesTable, callbackManager)


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
