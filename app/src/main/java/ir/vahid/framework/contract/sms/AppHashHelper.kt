package ir.vahid.framework.contract.sms

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import java.security.MessageDigest
import java.util.LinkedHashSet

object AppHashHelper {

    fun getAppHashes(context: Context): List<String> {
        val pkg = context.packageName
        val sigs = getSignerStrings(context, pkg)
        if (sigs.isEmpty()) return emptyList()

        val out = LinkedHashSet<String>()
        sigs.forEach { sig ->
            val msg = "$pkg $sig"                       // EXACT format
            val sha256 = MessageDigest.getInstance("SHA-256")
                .digest(msg.toByteArray(Charsets.UTF_8))
            val first9 = sha256.copyOfRange(0, 9)
            val b64 = Base64.encodeToString(first9, Base64.NO_PADDING or Base64.NO_WRAP)
            out += if (b64.length >= 11) b64.substring(0, 11) else b64
        }
        return out.toList()
    }

    fun log(context: Context, tag: String = "APP_HASH") {
        val hashes = getAppHashes(context)
        if (hashes.isEmpty()) Log.w(tag, "No hashes found")
        hashes.forEach { Log.d(tag, it) }
    }

    // --- internals ---
    private fun getSignerStrings(context: Context, packageName: String): List<String> {
        val pm = context.packageManager
        return if (Build.VERSION.SDK_INT >= 28) {
            // Request SIGNING_CERTIFICATES correctly on all new APIs
            val pi = if (Build.VERSION.SDK_INT >= 33) {
                pm.getPackageInfo(
                    packageName,
                    PackageManager.PackageInfoFlags.of(PackageManager.GET_SIGNING_CERTIFICATES.toLong())
                )
            } else {
                @Suppress("DEPRECATION")
                pm.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            }

            val si = pi.signingInfo ?: return emptyList() // can be null if flags were wrong
            val signers = when {
                si.hasMultipleSigners() -> si.apkContentsSigners
                si.signingCertificateHistory != null && si.signingCertificateHistory.isNotEmpty() ->
                    si.signingCertificateHistory
                else -> si.apkContentsSigners
            }
            signers.map { it.toCharsString() }
        } else {
            @Suppress("DEPRECATION")
            pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
                .signatures?.map { it.toCharsString() } ?: emptyList()
        }
    }
}




class AppSignatureHelper(private val context: Context) {
    @RequiresApi(Build.VERSION_CODES.P)
    fun getAppSignatures(): List<String> {
        val packageName = context.packageName
        val signatures = context.packageManager
            .getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            .signingInfo?.apkContentsSigners?.map { it.toCharsString() }

        return signatures?.mapNotNull { signature ->
            try {
                val message = "$packageName $signature"
                val md = MessageDigest.getInstance("SHA-256")
                val digest = md.digest(message.toByteArray(Charsets.UTF_8))
                val hash = Base64.encodeToString(digest.copyOfRange(0, 9), Base64.NO_PADDING or Base64.NO_WRAP)
                // trim to 11 chars per spec
                hash.substring(0, 11)
            } catch (_: Exception) { null }
        } ?: emptyList()
    }
}
