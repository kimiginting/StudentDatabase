package com.example.db_siswa;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import static android.widget.Toast.makeText;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextNik;
    private EditText editTextNama;
    private EditText editTextAlamat;
    private Spinner spinnerKota; // Changed to Spinner
    private RadioGroup radioGroupJk; // Changed to RadioGroup
    private EditText editTextUmur;
    private Button buttonAdd, view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        editTextNik = findViewById(R.id.editTextNik);
        editTextNama = findViewById(R.id.editTextNama); // Ganti editTextName menjadi editTextNama
        editTextAlamat = findViewById(R.id.editTextAlamat);
        spinnerKota = findViewById(R.id.editTextKota);
        radioGroupJk = findViewById(R.id.editTextJk);
        editTextUmur = findViewById(R.id.editTextUmur);
        buttonAdd = findViewById(R.id.buttonAdd);
        view = (Button) findViewById(R.id.view);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TampilData.class);
                startActivity(intent);
            }
        });

        // Set listener to button
        buttonAdd.setOnClickListener(this);
    }

    private void addEmployee() {
        final String name = editTextNama.getText().toString().trim(); // Ubah menjadi editTextNama
        final String nik = editTextNik.getText().toString().trim();
        final String alamat = editTextAlamat.getText().toString().trim();
        final String kota = spinnerKota.getSelectedItem().toString();
        final String jk;
        int selectedId = radioGroupJk.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            jk = selectedRadioButton.getText().toString();
        } else {
            // Handle jika tidak ada RadioButton yang dipilih
            return;
        }
        final String umur = editTextUmur.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Menambahkan...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                // Handle response message
                if (s.trim().equals("Berhasil Menambahkan Pegawai")) {
                    Toast.makeText(MainActivity.this, "Data Gagal ditambahkan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data Siswa berhasil ditambahkan ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_NIK, nik);
                params.put(konfigurasi.KEY_EMP_NAMA, name);
                params.put(konfigurasi.KEY_EMP_ALAMAT, alamat);
                params.put(konfigurasi.KEY_EMP_KOTA, kota);
                params.put(konfigurasi.KEY_EMP_KELAMIN, jk);
                params.put(konfigurasi.KEY_EMP_UMUR, umur);
                RequestHandler rh = new RequestHandler();
                return rh.sendPostRequest(konfigurasi.URL_ADD, params);
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonAdd) {
            addEmployee();
        }
    }
}

