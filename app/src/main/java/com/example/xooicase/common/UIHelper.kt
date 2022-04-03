package com.example.xooicase.common

import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.ActionBar


class UIHelper {

    companion object {

        fun hideSystemUI(window: Window, supportActionBar: ActionBar) {
          window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
           supportActionBar.hide()

        }

        fun showSystemUI(window: Window, supportActionBar: ActionBar) {
          window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
           supportActionBar.show()

        }


    }

}