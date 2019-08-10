package br.com.dhonatandiego.pcc;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.dhonatandiego.pcc.exception.ValidationException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CadastrarMedico extends Fragment implements AdapterView.OnItemSelectedListener{

    View v;
    public CadastrarMedico() {


    }
    private DatabaseReference mDatabase;
    String selectedItemText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_cadastrar_medico, container, false);
        final Spinner spinner = (Spinner) v.findViewById(R.id.Medspinner);
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
