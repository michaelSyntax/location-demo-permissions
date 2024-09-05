package com.example.locationdemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.locationdemo.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val TAG = "PERMISSION"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { (permission, isGranted) ->
            if (isGranted) {
                Log.i(TAG, "Berechtigung wurde erteilt: $permission")
            } else {
                Toast.makeText(
                    this,
                    "Bitte erteile die vollständigen Berechtigungen um die App nutzen zu können",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btLocation.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val locationString =
                            "Latitude: " + location.latitude.toString() + "\nLongitude: " + location.longitude.toString()
                        binding.tvLocation.text = locationString
                    } else {
                        // lastLocation ist null, starte eine explizite Standortanfrage.
                        requestNewLocationData()
                    }
                }
            } else {
                requestInitialPermissions()
            }
        }
    }

    private fun requestNewLocationData() {
        val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000
        ).setMinUpdateIntervalMillis(2000)
            .build()
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                object : com.google.android.gms.location.LocationCallback() {
                    override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                        super.onLocationResult(locationResult)
                        val location = locationResult.lastLocation
                        if (location != null) {
                            val locationString =
                                "Latitude: " + location.latitude.toString() + "\nLongitude: " + location.longitude.toString()
                            binding.tvLocation.text = locationString
                        } else {
                            // Standort immer noch nicht verfügbar, zeige dem Benutzer eine Fehlermeldung.
                            Toast.makeText(
                                this@MainActivity,
                                "Standort konnte nicht ermittelt werden!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                null
            )
        }
    }

    private fun showRationaleDialog(onOk: () -> Unit) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Benötigt Berechtigung")
            .setMessage("Die App benötigt die folgende Berechtigung, um deine Aktivität zu tracken.")
            .setPositiveButton("OK") { _, _ -> onOk() }
            .show()
    }

    private fun requestInitialPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            showRationaleDialog {
                // Zeige den Dialog mit der Erklärung und fordere anschließend die Berechtigungen an
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        } else {
            // Überprüfen, ob die Berechtigung dauerhaft abgelehnt wurde (Don't ask again)
            val isPermissionDeniedPermanently =
                !ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            if (isPermissionDeniedPermanently) {
                // Zeige dem Benutzer eine Nachricht und leite ihn zu den App-Einstellungen.
                showSettingsDialog()
            } else {
                // Fordere die Berechtigungen direkt an, da sie noch nicht abgelehnt wurden
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    /**
     * Der Benutzer hat dauerhaft die Berechtigung verweigert.
     * Zeige einen AlertDialog der den Benutzer zu den App-Einstellungen leiten kann.
     */
    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Berechtigungen dauerhaft abgelehnt")
            .setMessage("Die Standortberechtigungen wurden dauerhaft abgelehnt. Du musst sie in den App-Einstellungen manuell aktivieren.")
            .setPositiveButton("Zu den Einstellungen") { _, _ ->
                // Leite den Benutzer zu den App-Einstellungen
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton("Abbrechen", null)
            .show()
    }
}