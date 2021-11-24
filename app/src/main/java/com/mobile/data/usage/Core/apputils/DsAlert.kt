package com.mobile.data.usage.Core.apputils

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog

object DsAlert {
    fun showAlertDialog(
        context: Context,
        title: String = "",
        message: String = "",
        positiveButton: String? = null,
        positiveButtonClick: (() -> Unit)? = null,
        negativeButton: String? = null,
        negativeButtonClick: (() -> Unit)? = null,
        isCancelable: Boolean = true
    ) {
        AlertDialog.Builder(context, android.R.style.ThemeOverlay_Material_Dialog_Alert)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(isCancelable)
            .apply {
                positiveButton?.let { positiveString ->
                    setPositiveButton(positiveString) { dialog, which ->
                        positiveButtonClick?.invoke()
                    }
                }
                negativeButton?.let { negativeString ->
                    setNegativeButton(negativeString) { dialog, which ->
                        negativeButtonClick?.invoke()
                    }
                }
            }
            .show()
    }

    fun show(context: Activity,
             title: String,
             message: String,
             positiveButton: String
    ): AlertDialog {
       return  AlertDialog.Builder(context,android.R.style.ThemeOverlay_Material_Dialog_Alert)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .show()
    }
}