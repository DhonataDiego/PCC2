package br.com.dhonatandiego.pcc;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PesquisarHospADM extends Fragment {


    public PesquisarHospADM() {
        // Required empty public constructor
    }
    Spinner spinner,spinner2;
    View v;
    TextView textView;
    TextView t3;
    LatLng latLng;
    HospitalCidade hospitalCidade = new HospitalCidade();
    FragmentTransaction transaction;
    private DatabaseReference mDatabase;
    HospitalFragment IH;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_pesquisar_hosp_adm, container, false);
        spinner= (Spinner) v.findViewById(R.id.spinnerCid);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        spinner2 = (Spinner) v.findViewById(R.id.spinnerMedHosp);
        textView = v.findViewById(R.id.textPesquisar);
        // Initializing a String Array
        String[] estados = new String[]{
                "Selecione","Abreu e Lima","Afogados da Ingazeira","Afranio","Agrestina","Agua Preta","Aguas Belas","Alagoinha","Alianca","Altinho","Amaraji", "Angelim",
                "Aracoiaba","Araripina","Arcoverde","Barra de Guabiraba","Barreiros","Belem de Maria","Belem de Sao Francisco","Belo Jardim","Betania",
                "Bezerros", "Bodoco", "Bom Conselho","Bom Jardim","Bonito","Brejao","Brejinho","Brejo da Madre de Deus","Buenos Aires","Buique",
                "Cabo de Santo Agostinho","Cabrobo","Cachoeirinha","Caetes","Calcado","Calumbi","Camaragibe","Camocim de Sao Felix","Camutanga","Canhotinho",
                "Capoeiras","Carnaiba","Carnaubeira da Penha","Carpina","Caruaru","Casinhas","Catende","Cedro","Cha Grande","Cha de Alegria","Condado",
                "Correntes","Cortes","Cumaru","Cupira","Custodia","Dormentes","Escada","Exu","Feira Nova","Ferreiros","Flores","Floresta", "Frei Miguelinho",
                "Gameleira","Garanhuns","Gloria do Goita","Goiana","Granito","Gravata","Iati","Ibimirim","Ibirajuba","Igarassu", "Iguaraci", "Inaja",
                "Ingazeira","Ipojuca","Ipubi","Itacuruba","Itaiba","Itamaraca","Itambe","Itapetim","Itapissuma","Itaquitinga","Jaboatao dos Guararapes","Jaqueira",
                "Jatauba","Jatoba","Joao Alfredo","Joaquim Nabuco","Jucati","Jupi","Jurema","Lagoa Grande","Lagoa do Carro","Lagoa do Itaenga",
                "Lagoa do Ouro","Lagoa dos Gatos","Lajedo","Limoeiro","Macaparana","Machados","Manari","Maraial","Mirandiba","Moreilandia","Moreno",
                "Nazare da Mata","Olinda","Orobo","Oroco","Ouricuri","Palmares","Palmeirina","Panelas","Paranatama","Parnamirim","Passira","Paudalho",
                "Paulista","Pedra","Pesqueira","Petrolandia","Petrolina","Pocao","Pombos", "Primavera", "Quipapa","Quixaba","Recife","Riacho das Almas",
                "Ribeirao","Rio Formoso","Saire","Salgadinho","Salgueiro","Saloa","Sanharo","Santa Cruz da Baixa Verde","Santa Cruz do Capibaribe",
                "Santa Cruz","Santa Filomena","Santa Maria da Boa Vista","Santa Maria do Cambuca","Santa Terezinha","Sao Benedito do Sul",
                "Sao Bento do Una","Sao Caitano","Sao Joao","Sao Joaquim do Monte","Sao Jose da Coroa Grande","Sao Jose do Belmonte","Sao Jose do Egito",
                "Sao Lourenco da Mata","Sao Vicente Ferrer","Serra Talhada","Serrita","Sertania","Sirinhaem","Solidao","Surubim","Tabira","Tacaimbo",
                "Tacaratu","Tamandare","Taquaritinga do Norte","Terezinha","Terra Nova","Timbauba","Toritama","Tracunhaem","Trindade","Triunfo",
                "Tupanatinga","Tuparetama","Venturosa","Verdejante","Vertente do Lerio","Vertentes","Vicencia","Vitoria de Santo Antao","Xexeu"
        };
        String[]MedHosp=new String[]{"Selecione","Medico","Hospital"};
        final List<String> plantsList = new ArrayList<>(Arrays.asList(estados));
        final List<String> plantsList2 = new ArrayList<>(Arrays.asList(MedHosp));
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
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                getContext(),R.layout.spinner_item,plantsList2){
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
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        v.findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pesquisar();
            }
        });
        spinner.setAdapter(spinnerArrayAdapter);
        spinner2.setAdapter(spinnerArrayAdapter2);
        return v;
    }
    public void Pesquisar(){
        final String municipio=spinner.getSelectedItem().toString().toUpperCase();
        String Chave=spinner2.getSelectedItem().toString();
        String nome = textView.getText().toString();
        transaction= getFragmentManager().beginTransaction();

        if(municipio.equals("SELECIONE")){
            Toast.makeText(getContext(),"Selecione o Municipio!",Toast.LENGTH_LONG).show();
        }
        else if(Chave.equals("Hospital")) {
            final ArrayList<Hospital> AuxArray = new ArrayList<>();
            Query query = mDatabase.child("Hospitais");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                        Hospital h = Snap.getValue(Hospital.class);
                        if (h.NOME_FANTASIA.contains(textView.getText().toString().toUpperCase())&&h.MUNICIPIO.equals(municipio)) {
                            AuxArray.add(h);
                        }
                    }
                        hospitalCidade.Hospitais=AuxArray;
                        hospitalCidade.latLng=latLng;
                        transaction.addToBackStack("pilha");
                        transaction.replace (R.id.ContainerPesq,hospitalCidade,"Fragment");
                        transaction.commit();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(Chave.equals("Medico")){
            final ArrayList<Medicos> AuxArray = new ArrayList<>();
            Query query = mDatabase.child("MedicosT").child(municipio);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                        Medicos h = Snap.getValue(Medicos.class);
                        if (h.NOME.contains(textView.getText().toString().toUpperCase())) {
                            AuxArray.add(h);
                        }
                    }
                    InfoMedico infoMed = new InfoMedico();
                    infoMed.Medicos=AuxArray;
                    transaction.addToBackStack("pilha");
                    transaction.replace (R.id.ContainerPesq,infoMed,"Fragment");
                    transaction.commit();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            Toast.makeText(getContext(), "Selecione o tipo de busca!", Toast.LENGTH_LONG).show();
        }
    }
}
