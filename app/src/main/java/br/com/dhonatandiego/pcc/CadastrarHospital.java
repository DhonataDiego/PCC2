package br.com.dhonatandiego.pcc;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CadastrarHospital extends Fragment implements AdapterView.OnItemSelectedListener {


    public CadastrarHospital() {
        // Required empty public constructor
    }
    private DatabaseReference mDatabase;
    View v;
    TextView t1,t2;
    String selectedItemText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_cadastrar_hospital, container, false);
        final Spinner spinner = (Spinner) v.findViewById(R.id.HUF_Cad);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        // Initializing a String Array
        String[] estados = new String[]{
                "UF","AC", "AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA"
                ,"PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","SP"
        };

        final List<String> plantsList = new ArrayList<>(Arrays.asList(estados));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position==0) {
                    // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
        return v;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedItemText= (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
