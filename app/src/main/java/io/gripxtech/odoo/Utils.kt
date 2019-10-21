package io.gripxtech.odoo

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.TaskStackBuilder
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.view.inputmethod.InputMethodManager
import com.google.gson.*
import com.odoo.common.KEY_ACCOUNT_TYPE
import io.gripxtech.odoo.core.authenticator.SplashActivity
import io.gripxtech.odoo.core.entities.Many2One
import io.gripxtech.odoo.core.entities.session.authenticate.AuthenticateResult
import com.odoo.common.utils.encryptAES
import retrofit2.Response
import java.lang.Exception

const val RECORD_LIMIT = 1000


fun Context.createOdooUser(authenticateResult: AuthenticateResult): Boolean {
    val accountManager = AccountManager.get(this)
    val account = Account(authenticateResult.androidName, KEY_ACCOUNT_TYPE)
    val result = try {
        accountManager.addAccountExplicitly(
            account,
            authenticateResult.password.encryptAES(),
            authenticateResult.toBundle
        )
    } catch (e: Exception) {
        false
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        accountManager.notifyAccountAuthenticated(account)
    }
    return result
}

val JsonElement.isManyToOne: Boolean get() = isJsonArray && asJsonArray.size() == 2

val JsonElement.asManyToOne: Many2One
    get() = if (isManyToOne) {
        Many2One(asJsonArray)
    } else {
        Many2One(JsonArray().apply { add(0); add("") })
    }


@Suppress("DEPRECATION")
val Response<*>.errorBodySpanned: Spanned
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        Html.fromHtml(errorBody()!!.string(), Html.FROM_HTML_MODE_COMPACT)
    else
        Html.fromHtml(errorBody()!!.string())

fun AppCompatActivity.hideSoftKeyboard() {
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun AppCompatActivity.restartApp() {
    TaskStackBuilder.create(this)
        .addNextIntent(Intent(this, SplashActivity::class.java))
        .startActivities()
}

var alertDialog: AlertDialog? = null

fun AppCompatActivity.showMessage(
    title: CharSequence? = null,
    message: CharSequence?,
    cancelable: Boolean = false,
    positiveButton: CharSequence = getString(R.string.ok),
    positiveButtonListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { _, _ -> },
    showNegativeButton: Boolean = false,
    negativeButton: CharSequence = getString(R.string.cancel),
    negativeButtonListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { _, _ -> }
): AlertDialog {
    alertDialog?.dismiss()
    alertDialog = AlertDialog.Builder(this, R.style.AppAlertDialogTheme)
        .setTitle(title)
        .setMessage(
            if (message?.isNotEmpty() == true) {
                message
            } else {
                getString(R.string.generic_error)
            }
        )
        .setCancelable(cancelable)
        .setPositiveButton(positiveButton, positiveButtonListener)
        .apply {
            if (showNegativeButton) {
                setNegativeButton(negativeButton, negativeButtonListener)
            }
        }
        .show()
    return alertDialog!!
}

@Suppress("DEPRECATION")
fun AppCompatActivity.showServerErrorMessage(
    response: Response<*>,
    positiveButtonListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { _, _ -> }
): AlertDialog =
    showMessage(
        title = getString(R.string.server_request_error, response.code(), response.body()),
        message = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(response.errorBody()!!.string(), Html.FROM_HTML_MODE_COMPACT)
        else
            Html.fromHtml(response.errorBody()!!.string()),
        positiveButtonListener = positiveButtonListener
    )

fun AppCompatActivity.closeApp(message: String = getString(R.string.generic_error)): AlertDialog =
    showMessage(
        getString(R.string.fatal_error),
        message,
        false,
        getString(R.string.exit),
        DialogInterface.OnClickListener { _, _ ->
            ActivityCompat.finishAffinity(this)
        })


fun AppCompatActivity.filteredErrorMessage(errorMessage: String): String = when (errorMessage) {
    "Expected singleton: res.users()" -> {
        getString(R.string.login_credential_error)
    }
    else -> {
        errorMessage
    }
}

@Suppress("DEPRECATION")
fun AppCompatActivity.getProgressDialog(): android.app.ProgressDialog {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        return android.app.ProgressDialog(this, R.style.AppAlertDialogTheme)
    }
    return android.app.ProgressDialog(this)
}

fun AppCompatActivity.isDeviceOnline(): Boolean {
    var isConnected = false
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nInfo = manager.activeNetworkInfo
    if (nInfo != null && nInfo.isConnectedOrConnecting) {
        isConnected = true
    }
    return isConnected
}


