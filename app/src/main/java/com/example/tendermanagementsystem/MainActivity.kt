package com.example.tendermanagementsystem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tendermanagementsystem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Holds the most recently saved tenant.
    private var lastTenant: Tenant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TENANT_LIFECYCLE", "MainActivity: onCreate")

        // Inflate and display the existing Practical 1 layout.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Restore the last saved tenant after Activity recreation.
        if (savedInstanceState != null) {
            val name = savedInstanceState.getString("tenant_name")
            val phone = savedInstanceState.getString("tenant_phone")
            val rent = savedInstanceState.getString("tenant_rent")

            if (name != null && phone != null && rent != null) {
                val tenant = Tenant(name, phone, rent)

                lastTenant = tenant
                binding.tenant = tenant
            }
        }

        // Preserve the login email message from Practical 1.
        val email = intent.getStringExtra("EMAIL")

        if (email != null) {
            Toast.makeText(
                this,
                "Logged in as $email",
                Toast.LENGTH_SHORT
            ).show()
        }

        // SAVE TENANT
        binding.saveButton.setOnClickListener {

            val name = binding.tenantNameEditText.text
                .toString().trim()

            val phone = binding.phoneEditText.text
                .toString().trim()

            val rent = binding.rentEditText.text
                .toString().trim()

            if (name.isEmpty()) {
                binding.tenantNameEditText.error = "Required"
                return@setOnClickListener
            }

            if (phone.isEmpty()) {
                binding.phoneEditText.error = "Required"
                return@setOnClickListener
            }

            if (rent.isEmpty()) {
                binding.rentEditText.error = "Required"
                return@setOnClickListener
            }

            val tenant = Tenant(name, phone, rent)

            // Update the Data Binding variable and remembered tenant.
            binding.tenant = tenant
            lastTenant = tenant

            Toast.makeText(
                this,
                "Tenant saved",
                Toast.LENGTH_SHORT
            ).show()
        }

        // CALL TENANT
        binding.callButton.setOnClickListener {

            val tenant = lastTenant

            if (tenant == null) {
                Toast.makeText(
                    this,
                    "Save a tenant first",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val dialIntent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:${tenant.phone}")
            )

            startActivity(dialIntent)
        }

        // SHARE TENANT
        binding.shareButton.setOnClickListener {

            val tenant = lastTenant

            if (tenant == null) {
                Toast.makeText(
                    this,
                    "Save a tenant first",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    tenant.summary()
                )
            }

            startActivity(
                Intent.createChooser(shareIntent, "Share tenant")
            )
        }
    }

    // SAVE STATE before Android recreates the Activity.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d(
            "TENANT_LIFECYCLE",
            "MainActivity: onSaveInstanceState"
        )

        val tenant = lastTenant

        if (tenant != null) {
            outState.putString("tenant_name", tenant.name)
            outState.putString("tenant_phone", tenant.phone)
            outState.putString("tenant_rent", tenant.rent)
        }
    }

    // LIFECYCLE METHODS

    override fun onStart() {
        super.onStart()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onDestroy")
    }
}