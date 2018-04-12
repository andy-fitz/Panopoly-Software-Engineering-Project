package pw.jcollado.segamecontroller.utils

import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by jcolladosp on 13/02/2018.
 */
var progressBar: ProgressDialog? = null


fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)


}

fun Context.loadDialog(con: Context,message:String){
        progressBar = ProgressDialog(this)
        progressBar?.setMessage(message)
        progressBar?.setCancelable(false)
        progressBar?.show()

}
fun Context.closeLoadingDialog(){

    progressBar?.dismiss()

}



