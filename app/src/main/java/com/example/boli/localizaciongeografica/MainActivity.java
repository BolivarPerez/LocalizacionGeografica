package com.example.boli.localizaciongeografica;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

   private  TextView  lblLatitud, lblLongitud,lblPresicion, lblEstado;
   private Button btnActualizar, btnDesactivar;

    private LocationManager locManager;
    private LocationListener locListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnDesactivar = (Button) findViewById(R.id.btnDesactivar);
        lblLatitud = (TextView) findViewById(R.id.lblposLatitud);
        lblLongitud = (TextView) findViewById(R.id.lblposLongitud);
        lblPresicion = (TextView) findViewById(R.id.lblposPrecision);
        lblEstado = (TextView) findViewById(R.id.lblEstado);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comenzarLocalizacion();
            }
        });
        btnDesactivar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                locManager.removeUpdates(locListener);
            }
        });
    }

    private void mostrarPosicion (Location loc) {
        if (loc != null) {
            lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            lblLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            lblPresicion.setText("Precision: " + String.valueOf(loc.getAccuracy()));

        } else {
            lblLatitud.setText("Latitud: (sin_datos)");
            lblLongitud.setText("Longitud: (sin_datos)");
            lblPresicion.setText("Presicion: (sin_datos)");
        }
    }

    private void comenzarLocalizacion()
    {
        locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

       Location loc =  locManager .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        mostrarPosicion(loc);

        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

                Log.i("", "Provider Status: " + status);
                lblEstado.setText("Provider Status: " + status);

            }

            @Override
            public void onProviderEnabled(String provider) {
                lblEstado.setText("Provider ON ");

            }

            @Override
            public void onProviderDisabled(String provider) {
                lblEstado.setText("Provider OFF");

            }
        };

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
