package com.example.db_siswa;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.db_siswa.ConfigureData;
import com.example.db_siswa.R;

import java.util.List;

public class MhsAdapter extends BaseAdapter {
    Activity activity;
    List<ConfigureData> items;
    private LayoutInflater inflater;

    public MhsAdapter(Activity activity, List<ConfigureData> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) convertView = inflater.inflate(R.layout.list, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView nik = (TextView) convertView.findViewById(R.id.nik);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamat);
        TextView kota = (TextView) convertView.findViewById(R.id.kota);
        TextView jk = (TextView) convertView.findViewById(R.id.jk);
        TextView umur = (TextView) convertView.findViewById(R.id.umur);

        ConfigureData data = items.get(position);

        id.setText(data.getId());
        nik.setText(data.getNik());
        name.setText(data.getName());
        alamat.setText(data.getAlamat());
        kota.setText(data.getKota());
        jk.setText(data.getJk());
        umur.setText(data.getUmur());

        return convertView;
    }
}
