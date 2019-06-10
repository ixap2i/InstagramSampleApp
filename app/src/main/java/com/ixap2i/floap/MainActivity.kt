package com.ixap2i.floap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.facebook.login.LoginManager

import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import com.facebook.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
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
            modules(module)
        }

        callbackManager = CallbackManager.Factory.create()
        accessTokenTraker  = object: AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
                if(currentAccessToken === null) {
                    imagesTable.visibility = View.INVISIBLE
                } else if(currentAccessToken !== oldAccessToken) {
                    imagesTable.visibility = View.VISIBLE
                }
            }
        }

        loginButton = findViewById(R.id.login_button)

        LoginManager.getInstance().logInWithReadPermissions(this@MainActivity, Arrays.asList("email"))


        imagesTable = findViewById(R.id.table_view)

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
