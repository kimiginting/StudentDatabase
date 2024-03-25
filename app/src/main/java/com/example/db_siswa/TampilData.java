package com.example.db_siswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TampilData extends AppCompatActivity {

    public static final String URLTAMPIL = "http://10.0.2.2/androiddb/siswa/select.php";
    public static final String URLDELETE = "http://10.0.2.2/androiddb/siswa/delete.php";
    public static final String URLINSERT = "http://10.0.2.2/androiddb/siswa/insert.php";
    public static final String URLUBAH = "http://10.0.2.2/androiddb/siswa/edit.php";

    ListView list;
    AlertDialog.Builder dialog;
    List<ConfigureData> itemList = new ArrayList<ConfigureData>();
    MhsAdapter adapter;
    LayoutInflater inflater;
    View dialogView;
    EditText tid, tnik, tname, talamat, tumur;

    String id, nik, name, alamat, kota, jk, umur;
    FloatingActionButton fab;
    private Spinner tkota; // Changed to Spinner
    private RadioGroup tjk; // Changed to RadioGroup

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data); // Ubah ke layout yang benar
        list = findViewById(R.id.list);
        adapter = new MhsAdapter(TampilData.this, itemList);
        list.setAdapter(adapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(TampilData.this);
                // dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                ubah(idx);
                                break;
                            case 1:
                                hapus(idx);
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });

        callVolley(); // Panggil method untuk memuat data dari server
    }


    private void callVolley() {
        itemList.clear();
        adapter.notifyDataSetChanged();

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(URLTAMPIL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        ConfigureData item = new ConfigureData();

                        item.setId(obj.getString("id"));
                        item.setNik(obj.getString("nik"));
                        item.setName(obj.getString("nama"));
                        item.setAlamat(obj.getString("alamat"));
                        item.setKota(obj.getString("kota"));
                        item.setJk(obj.getString("jk"));
                        item.setUmur(obj.getString("umur"));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TampilData.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        });

        // menambah request ke request queue
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(jArr);

    }
    private void ubah(String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLUBAH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);

                            String idx = jObj.getString("id");
                            String nikx = jObj.getString("nik");
                            String namex = jObj.getString("nama");
                            String alamatx = jObj.getString("alamat");
                            String kotax = jObj.getString("kota");
                            String jkx = jObj.getString("jk");
                            String umurx = jObj.getString("umur");

                            DialogForm(idx, nikx, namex, alamatx, kotax, jkx, umurx, "UPDATE");

                            adapter.notifyDataSetChanged();

                        }catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TampilData.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();


                params.put("id", id );
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
    private void hapus(String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLDELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callVolley();
                        Toast.makeText(TampilData.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TampilData.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();


                params.put("id", id );
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
    void simpan(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLINSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callVolley();
                        Toast.makeText(TampilData.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TampilData.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();

                if (id.isEmpty()) {
                    params.put("nik", nik);
                    params.put("name", name);
                    params.put("alamat", alamat);
                    params.put("kota", kota);
                    params.put("jk", jk);
                    params.put("umur", umur);
                    return params;
                }else{
                    params.put("id", id);
                    params.put("nik", nik);
                    params.put("name", name);
                    params.put("alamat", alamat);
                    params.put("kota", kota);
                    params.put("jk", jk);
                    params.put("umur", umur);
                    return params;
                }
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);


    }
    private void DialogForm(String idx, String nikx, String namex, String alamatx, String kotax, String jkx, String umurx,  String button) {
        dialog = new AlertDialog.Builder(TampilData.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_siswa, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.ic_contact);
        dialog.setTitle("Update Data");

        tid = (EditText) dialogView.findViewById(R.id.inId);
        tnik = (EditText) dialogView.findViewById(R.id.inNik);
        tname = (EditText) dialogView.findViewById(R.id.inName);
        talamat = (EditText) dialogView.findViewById(R.id.inAlamat);
        tkota = (Spinner) dialogView.findViewById(R.id.editTextKota); // Initialize spinnerKota
        tjk = (RadioGroup) dialogView.findViewById(R.id.inJk);
        tumur = (EditText) dialogView.findViewById(R.id.inUmur);

        // Set nilai awal EditText dengan data yang diterima
        tid.setText(idx);
        tnik.setText(nikx);
        tname.setText(namex);
        talamat.setText(alamatx);
        // Set nilai spinnerKota dengan data yang diterima
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.daftar_kota, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tkota.setAdapter(adapter);
        if (kotax != null) {
            int spinnerPosition = adapter.getPosition(kotax);
            tkota.setSelection(spinnerPosition);
        }
        // Set nilai radioGroupJk dengan data yang diterima
        if (jkx != null) {
            if (jkx.equals("Laki-Laki")) {
                tjk.check(R.id.laki);
            } else if (jkx.equals("Perempuan")) {
                tjk.check(R.id.perempuan);
            }
        }

        // Set nilai umur dengan data yang diterima
        tumur.setText(umurx);

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Simpan data yang diubah
                id = tid.getText().toString();
                nik = tnik.getText().toString();
                name = tname.getText().toString();
                alamat = talamat.getText().toString();
                kota = tkota.getSelectedItem().toString(); // Ambil nilai dari spinnerKota
                int selectedId = tjk.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = dialogView.findViewById(selectedId); // Gunakan dialogView.findViewById untuk mencari RadioButton dalam dialogView
                    jk = selectedRadioButton.getText().toString(); // Ambil nilai dari radioGroupJk
                } else {
                    // Handle jika tidak ada RadioButton yang dipilih
                    jk = "";
                }
                umur = tumur.getText().toString();

                // Simpan data yang diubah
                simpan();

                dialog.dismiss();
            }
        });


        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });

        dialog.show();
    }

    private void kosong() {
        tid.setText(null);
        tnik.setText(null);
        tname.setText(null);
        talamat.setText(null);
        tkota.setSelection(0); // Mengatur posisi default Spinner kota ke 0
        tjk.clearCheck(); // Mengosongkan RadioGroup jenis kelamin
        tumur.setText(null);
    }
}