package utng.edu.mx.wsasentamiento;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etCodigoPostal;
    private EditText etConsecutivoCp;
    private EditText etCvEstado;
    private EditText etAsentamiento;
    private EditText etActivo;
    private EditText etMunicipio;
    private EditText etTipoAsentamiento;
    private EditText etCiudadAsentamiento;
    private Button btnSave;
    private Button btnList;

    private Asentamiento asentamiento = null;

    final String NAMESPACE = "http://ws.utng.edu.mx";
    final SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
    static String URL = "http://192.168.24.186:8080/WSAsentamiento/services/AsentamientoWS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        etCodigoPostal = (EditText) findViewById(R.id.tv_codigo_postal);
        etConsecutivoCp = (EditText) findViewById(R.id.tv_consecutivo_cp);
        etCvEstado = (EditText) findViewById(R.id.tv_cve_estado);
        etAsentamiento = (EditText) findViewById(R.id.tv_asentamiento);
        etActivo = (EditText) findViewById(R.id.tv_activo);
        etMunicipio = (EditText) findViewById(R.id.tv_municipio);
        etTipoAsentamiento = (EditText) findViewById(R.id.tv_tipo_asentamiento);
        etCiudadAsentamiento = (EditText) findViewById(R.id.tv_ciudad_asentamiento);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnList = (Button) findViewById(R.id.btn_list);
        btnSave.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consume_w, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnSave.getId()) {
            try {
                if (getIntent().getExtras().getString("accion")
                        .equals("modificar")) {
                    TaskWSUpdate tarea = new TaskWSUpdate();
                    tarea.execute();
                }

            } catch (Exception e) {
                //Cuando no se haya mandado una accion por defecto es insertar.
                TaskWSInsert tarea = new TaskWSInsert();
                tarea.execute();
            }
        }
        if (btnList.getId() == v.getId()) {
            startActivity(new Intent(MainActivity.this, ListAsentamientos.class));
        }
    }

    private class TaskWSInsert extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            final String METHOD_NAME = "addAsentamiento";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            asentamiento = new Asentamiento();
            asentamiento.setProperty(0, 0);

            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("asentamiento");
            info.setValue(asentamiento);
            info.setType(asentamiento.getClass());
            request.addProperty(info);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE, "Asentamiento", Asentamiento.class);

            /* Para serializar flotantes y otros tipos no cadenas o enteros*/
            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive response =
                        (SoapPrimitive) envelope.getResponse();
                String res = response.toString();
                if (!res.equals("1")) {
                    result = false;
                }

            } catch (Exception e) {
                Log.e("Error ", e.getMessage());
                result = false;
            }


            return result;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(),
                        "Registro exitoso.",
                        Toast.LENGTH_SHORT).show();
                cleanBox();

            } else {
                Toast.makeText(getApplicationContext(),
                        "Error al insertar.",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//

    private void cleanBox() {
        etCodigoPostal.setText("");
        etConsecutivoCp.setText("");
        etCvEstado.setText("");
        etAsentamiento.setText("");
        etActivo.setText("");
        etMunicipio.setText("");
        etTipoAsentamiento.setText("");
        etCiudadAsentamiento.setText("");


    }

    private class TaskWSUpdate extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "updateAsentamiento";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            asentamiento = new Asentamiento();
            asentamiento.setProperty(0, getIntent().getExtras().getString("valor0"));
            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("asentamiento");
            info.setValue(asentamiento);
            info.setType(asentamiento.getClass());

            request.addProperty(info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "Asentamiento", asentamiento.getClass());

            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
                String res = resultado_xml.toString();

                if (!res.equals("1")) {
                    result = false;
                }

            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
            } catch (XmlPullParserException e) {
                Log.e("Error XmlPullParser", e.toString());
            }


            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(), "Actualizado OK",
                        Toast.LENGTH_SHORT).show();
                cleanBox();
                startActivity(new Intent(MainActivity.this, MainActivity.class));

            } else {
                Toast.makeText(getApplicationContext(), "Error al actualizar",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//

    private void getData() {
        asentamiento.setProperty(1, etCodigoPostal.getText().toString());
        asentamiento.setProperty(2, Integer.parseInt(etConsecutivoCp.getText().toString()));
        asentamiento.setProperty(3, Integer.parseInt(etCvEstado.getText().toString()));
        asentamiento.setProperty(4, etAsentamiento.getText().toString());
        asentamiento.setProperty(5, Integer.parseInt(etActivo.getText().toString()));
        asentamiento.setProperty(6, etMunicipio.getText().toString());
        asentamiento.setProperty(7, etTipoAsentamiento.getText().toString());
        asentamiento.setProperty(8, Integer.parseInt(etCiudadAsentamiento.getText().toString()));


    }//

    @Override
    protected void onResume() {
        super.onResume();
        Bundle datosRegreso = this.getIntent().getExtras();
        try {
            Log.i("Dato", datosRegreso.getString("valor8"));

            etCodigoPostal.setText(datosRegreso.getString("valor1"));
            etConsecutivoCp.setText(datosRegreso.getString("valor2"));
            etCvEstado.setText(datosRegreso.getString("valor3"));
            etAsentamiento.setText(datosRegreso.getString("valor4"));
            etActivo.setText(datosRegreso.getString("valor5"));
            etMunicipio.setText(datosRegreso.getString("valor6"));
            etTipoAsentamiento.setText(datosRegreso.getString("valor7"));
            etCiudadAsentamiento.setText(datosRegreso.getString("valor8"));


        } catch (Exception e) {
            Log.e("Error al Recargar", e.toString());
        }
    }
}