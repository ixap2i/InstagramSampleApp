package com.ixap2i.floap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import com.facebook.CallbackManager
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.login.widget.LoginButton
import com.facebook.login.LoginManager
import com.facebook.AccessToken

import android.content.Intent
import android.view.View
import android.widget.ScrollView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var loginButton: LoginButton
    lateinit var callbackManager: CallbackManager
    lateinit var imagesTable: ScrollView

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


        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        loginButton = findViewById(R.id.login_button)
        imagesTable = findViewById(R.id.table_view)

        if(isLoggedIn) {
            LoginManager.getInstance().logInWithReadPermissions(this@MainActivity, Arrays.asList("public_profile"));
            LoginManager.getInstance().logInWithReadPermissions(this@MainActivity, Arrays.asList("pages_show_list"))
            imagesTable.visibility = View.VISIBLE
        } else {
            imagesTable.visibility = View.INVISIBLE

            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        loginButton.setPermissions("email")
                        LoginManager.getInstance().logInWithReadPermissions(this@MainActivity, Arrays.asList("public_profile"))
                        LoginManager.getInstance().logInWithReadPermissions(this@MainActivity, Arrays.asList("pages_show_list"))
                        imagesTable.visibility = View.VISIBLE
                    }

                    override fun onCancel() {
                        //　ここ記入するとひたすらモーダル出てくるの繰り返し
                        // imagesTable.visibility = View.INVISIBLE
                    }

                    override fun onError(exception: FacebookException) {
                        //　ここ記入するとひたすらモーダル出てくるの繰り返し
//                        imagesTable.visibility = View.INVISIBLE
                    }
                })

        }

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
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
