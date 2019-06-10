package com.ixap2i.floap

import android.view.View
import android.widget.LinearLayout
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.snackbar.Snackbar

interface LoginService {
    fun loginCallBack(loginButton: LoginButton, flag: Boolean, eventView: LinearLayout, callbackManager: CallbackManager)

    fun checkLoginStatus(eventView: LinearLayout): AccessTokenTracker
}

class LoginServiceImpl: LoginService {
    override fun checkLoginStatus(eventView: LinearLayout): AccessTokenTracker {
        return object: AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
                if(currentAccessToken === null) {
                    eventView.visibility = View.INVISIBLE
                } else if(currentAccessToken !== oldAccessToken) {
                    eventView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun loginCallBack(loginButton: LoginButton, flag: Boolean, eventView: LinearLayout, callbackManager: CallbackManager): Unit {
        loginButton.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onError(error: FacebookException?) {
                Snackbar.make(loginButton,"An error was occured. Please try at good  cellar condition.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            }

            override fun onCancel() {
                if(flag) {
                    eventView.visibility = View.VISIBLE
                } else {
                    eventView.visibility = View.INVISIBLE
                    Snackbar.make(loginButton,"Your login was canceled.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            }

            override fun onSuccess(result: LoginResult?) {
                if(result!!.accessToken !== null) {
                    eventView.visibility = View.VISIBLE
                } else {
                    eventView.visibility = View.INVISIBLE
                }
            }
        })
    }

}