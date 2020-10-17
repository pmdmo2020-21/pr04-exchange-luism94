package es.iessaladillo.pedrojoya.exchange.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

//Objeto SingleTon del que se genera una sola instancia
//Funciones top level porque la clase SoftInputUtils solo contiene estos metodos
fun showSoftInput(view: View): Boolean {
    if (view.requestFocus()) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null) {
            return imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
    return false
}

fun hideSoftKeyboard(view: View): Boolean {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return imm?.hideSoftInputFromWindow(view.windowToken, 0) ?: false
}