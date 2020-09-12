package com.marshsoft.gadsleaderboard

import android.app.ActionBar
import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_project_submission.*

class ProjectSubmission : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_submission)
        btnSubmit.setOnClickListener { showConfirmationDialog() }
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setCustomView(R.layout.custom_actionbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)

    }

    private fun showConfirmationDialog() {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val confirmationDialogView = LayoutInflater.from(this).inflate(R.layout.custom_confirmation_dialog,viewGroup,false)
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setView(confirmationDialogView)
        val alertDialog = alertBuilder.create()
        alertDialog.show()
        val button = alertDialog.findViewById<Button>(R.id.btnConfirm)
        button.setOnClickListener {
            //send code to api
            alertDialog.dismiss()
            progressBar.visibility = View.VISIBLE
            val postUrl = "https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse"


            val queue = Volley.newRequestQueue(this)

            val request: StringRequest = object : StringRequest( Method.POST, postUrl,
                Response.Listener { response ->
                    progressBar.visibility = View.INVISIBLE
                    val successDialog = LayoutInflater.from(this).inflate(R.layout.custom_success_dialog,viewGroup,false)
                    val sucessAlertBuilder = AlertDialog.Builder(this)
                    sucessAlertBuilder.setView(successDialog)
                    val successAlertDialog = sucessAlertBuilder.create()
                    successAlertDialog.show()

                },
                Response.ErrorListener { error ->
                    progressBar.visibility = View.INVISIBLE
                    val failureDialog = LayoutInflater.from(this).inflate(R.layout.error_submission,viewGroup,false)
                    val failureAlertBuilder = AlertDialog.Builder(this)
                    failureAlertBuilder.setView(failureDialog)
                    val failureAlertDialog = failureAlertBuilder.create()
                    failureAlertDialog.show()
                    println(error.message)
                }) {
                override fun getParams(): Map<String, String> {

                    val params = HashMap<String, String>()
                    params["entry.1824927963"] = etEmail.text.toString()
                    params["entry.1877115667"] = etFirstName.text.toString()
                    params["entry.2006916086"] = etLastName.text.toString()
                    params["entry.284483984"] = etGithubLink.text.toString()
                    return params
                }

                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/x-www-form-urlencoded"
                    return headers
                }
            }


            queue.add(request)
        }
        val closeImage = alertDialog.findViewById<ImageView>(R.id.imgClose)
        closeImage.setOnClickListener { alertDialog.dismiss() }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}