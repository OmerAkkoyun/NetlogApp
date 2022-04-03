package com.example.xooicase.common

import android.app.AlertDialog
import android.content.Context

class AlertHelper {
    companion object {

        fun createAlertDialogWithConfirm(context: Context, message: String, positiveButtonText: String, positiveCallback: () -> Unit) {
            AlertDialog.Builder(context).apply {
                setMessage(message)
                setCancelable(false)
                setPositiveButton(positiveButtonText) { _, _ -> positiveCallback() }
                show()
            }


        }

        fun createAlertDialogForInfo(context: Context, message: String, positiveButtonText: String) {
            AlertDialog.Builder(context).apply {
                setMessage(message)
                setCancelable(false)
                setPositiveButton(positiveButtonText) { _, _ -> }
                show()
            }


        }


    }
}