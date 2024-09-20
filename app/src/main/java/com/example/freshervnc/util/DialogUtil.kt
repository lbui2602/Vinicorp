package com.example.freshervnc.util

import android.content.Context
import androidx.appcompat.app.AlertDialog

class DialogUtil {

    companion object {
        fun showDialog(
            context: Context,
            message: String,
            positiveText: String = "OK",
            positiveAction: (() -> Unit)? = null,
            negativeText: String? = null,
            negativeAction: (() -> Unit)? = null
        ) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(message)
                .setPositiveButton(positiveText) { dialogInterface, _ ->
                    positiveAction?.invoke()
                    dialogInterface.dismiss()
                }

            if (!negativeText.isNullOrEmpty()) {
                builder.setNegativeButton(negativeText) { dialogInterface, _ ->
                    negativeAction?.invoke()
                    dialogInterface.dismiss()
                }
            }

            builder.show()
        }
    }
}
