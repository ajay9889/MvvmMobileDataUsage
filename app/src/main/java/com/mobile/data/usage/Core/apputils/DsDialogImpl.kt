package com.education.comic.ebook.core.designsystem
import android.app.AlertDialog
import android.content.Context
import com.mobile.data.usage.R


class DsDialogImpl(val context: Context) : DsDialog {
    private val dialog = AlertDialog.Builder(context, R.style.DsProgressDialog)
            .setView(R.layout.ds_view_progress_dialog)
            .setCancelable(false)
            .create()

    override fun showProgressDialog() {
       try{
           dialog.show()
       }catch (e:Exception){e.printStackTrace()}
    }

    override fun hideProgressDialog() {
        try{
            dialog.cancel()
        }catch (e:Exception){e.printStackTrace()}
    }
}