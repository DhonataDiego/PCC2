package br.com.dhonatandiego.pcc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HospitalFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public HospitalFragment() {
    }
    View v;
    Hospital h;
    InfoHospital hf;
    ArrayList<Medicos>MedicosGerais = new ArrayList<>();
    ArrayList<Medicos>MedicosHosp= new ArrayList<>();
    InfoMedico infoMedico=new InfoMedico();
    private DatabaseReference mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_info_hospital, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner2);
        CompararHospital();
        if(spinner!=null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.H_Array, android.R.layout.simple_spinner_item);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
        }
        else{
            Toast.makeText(getContext(),"spinner null",Toast.LENGTH_LONG);
        }
        return v;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String selectedItemText = (String) parent.getItemAtPosition(position);
            if (selectedItemText.equals("Informações")) {
                MostrarFrag(hf, "fragment");
            }
            if (selectedItemText.equals("Medicos")) {
                MedicosGerais.clear();
                    Query query = mDatabase.child("MedicosT").child(h.MUNICIPIO);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                                MedicosGerais.add(Snap.getValue(Medicos.class));
                            }
                            MedicosHosp = new ArrayList<>();
                            for (int i = 0; i < MedicosGerais.size(); i++) {
                                try {
                                    if (MedicosGerais.get(i).ESTABELECIMENTO.equals(h.NOME_FANTASIA)) {
                                        MedicosHosp.add(MedicosGerais.get(i));
                                    }
                                } catch (Exception e) {
                                }
                            }
                            //Toast.makeText(getContext(),""+MedicosHosp.size() + " 1 " + MedicosHosp.get(0).NOME,Toast.LENGTH_LONG).show();
                            //  TextView t3 = findViewById(R.id.Text);
                            //  t3.setText("Tamanho: " + MedicosHosp.size() + " 1 " + MedicosHosp.get(0).ESTABELECIMENTO);
                            infoMedico.Medicos = MedicosHosp;
                            MostrarFrag(infoMedico, "fragment");
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

    }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void MostrarFrag(Fragment fragmento, String name){

        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace (R.id.HContainer,fragmento,name);
        transaction.commit();
    }
    public void CompararHospital(){
                hf=new InfoHospital();
                Bundle args = new Bundle();
                args.putString("usuario",h.NOME_FANTASIA);
                args.putString("logradouro",""+h.LOGRADOURO);
                args.putString("bairro",""+h.BAIRRO);
                args.putString("cidade",h.MUNICIPIO);
                args.putString("UF",""+h.UF);
                args.putString("tipo",""+h.TIPO_GESTAO);
                args.putString("CEP",h.CEP);
                args.putString("CNPJM",""+h.CNPJ_MANTENEDORA);
                args.putString("CNPJP",""+h.CNPJ_PROPRIO);
                args.putString("CNES",h.CNES);
                args.putString("razao",""+h.RAZAO_SOCIAL);
                args.putString("numero",""+h.NUMERO);
                args.putString("IBGE",h.IBGE);
                hf.latitude=h.LATITUDE;
                hf.longitude=h.LONGITUDE;
                hf.setArguments(args);
    }
}
