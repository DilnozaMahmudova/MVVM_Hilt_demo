package com.company.dilnoza.mvvm_hilt.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.FileProvider
import com.company.dilnoza.mvvm_hilt.databinding.ActivityShareBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
@AndroidEntryPoint

class ShareAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.add.setOnClickListener { sendApkFile(this) }
        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java))
            finish() }
    }

    private fun sendApkFile(context: Context) {
        try {
            val pm = context.packageManager
            val ai = pm.getApplicationInfo(context.packageName, 0)
            val srcFile = File(ai.publicSourceDir)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            val uri: Uri = FileProvider.getUriForFile(this, context.packageName, srcFile)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            context.grantUriPermission(
                context.packageManager.toString(),
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}