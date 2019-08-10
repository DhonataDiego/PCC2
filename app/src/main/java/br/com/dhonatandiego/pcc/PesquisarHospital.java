package br.com.dhonatandiego.pcc;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PesquisarHospital extends Fragment {
    public ArrayList<Hospital> HospitaisGerais;
    ArrayList<Hospital>AuxArray;
    HospitalCidade hospitalCidade = new HospitalCidade();
    View v;
    ArrayList<Auxiliar>  AuxTeste= new ArrayList<>();
    Character aux1,aux2;
    private DatabaseReference mDatabase;
    ScrollView view;
    String[] numeros;
    ProgressBar progressBar;
    TextView txtItem2;
    ArrayList<String>CidadesPernambuco = new ArrayList<>();
    public PesquisarHospital() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_pesquisar_hospital, container, false);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        view = (ScrollView)v.findViewById(R.id.ScroolEstados2);
        view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        progressBar = v.findViewById(R.id. progressBar2);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        AdicionarLayouts();
        return v;
    }
    public void AdicionarLayouts(){
        aux1=CidadesPernambuco.get(0).trim().charAt(0);
        numeros= new String[]{" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "};
        int j=0;
        for(int i=0;i<CidadesPernambuco.size();i++) {
            aux2=CidadesPernambuco.get(i).trim().charAt(0);
            LinearLayout parentlayout = v.findViewById(R.id.layoutPesquisarHospital);
            LinearLayout Barrinhas = v.findViewById(R.id.layoutBarrinha);

            if(!aux1.equals(aux2)||i==0) {
                LinearLayout newlayout2 = new LinearLayout(getContext());
                newlayout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                txtItem2 = new TextView(getContext());
                txtItem2.setText(" "+aux2);
                txtItem2.setTextColor(Color.WHITE);
                txtItem2.setTextSize(20);
                TextView txt3= new TextView(getContext());
                txt3.setText(""+aux2);
                numeros[j]=""+aux2+""+i;
                j++;
                txt3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView v2 =(TextView) v;
                        for (int i=0;i<numeros.length;i++){
                            if(v2.getText().equals(""+numeros[i].charAt(0))){
                                int j =Integer.parseInt(numeros[i].replace(""+numeros[i].charAt(0),"").trim());
                                view.scrollTo(0,txtItem2.getHeight()*(i+j));
                            }
                        }


                    }
                });
                txt3.setTextSize(18);
                txt3.setTextColor(Color.rgb(255,255,255));
                Barrinhas.addView(txt3);
                newlayout2.addView(txtItem2);
                newlayout2.setBackgroundColor(Color.rgb(118, 17, 22));
                parentlayout.addView(newlayout2);
                aux1=aux2;
            }
            LinearLayout newlayout = new LinearLayout(getContext());
            newlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            TextView txtItem = new TextView(getContext());
            txtItem.setText(CidadesPernambuco.get(i));
            txtItem.setTextSize(20);
            if (i%2==0)
                newlayout.setBackgroundColor(Color.argb(25,118,17,22));
            newlayout.addView(txtItem);
            newlayout.setId(i);
            txtItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView t = (TextView) v;
                    Pesquisar(t);
                }
            });
            Auxiliar a = new Auxiliar();
            a.name =CidadesPernambuco.get(i);
            a.v= newlayout.getRootView();
            AuxTeste.add(a);
            newlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0; i<AuxTeste.size();i++){
                        if(AuxTeste.get(i).v==v){
                            TextView t=new TextView(getContext());
                            t.setText(AuxTeste.get(i).name);
                            Pesquisar(t);
                        }
                    }
                }
            });

            parentlayout.addView(newlayout);
        }
    }
    public void Pesquisar(final TextView b){
        AuxArray=new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Query query = mDatabase.child("Hospitais").orderByChild("MUNICIPIO").equalTo(b.getText().toString().toUpperCase());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                    AuxArray.add(Snap.getValue(Hospital.class));
                }
                /*for(int i=0;i<HospitaisGerais.size();i++){
                    if(HospitaisGerais.get(i).MUNICIPIO.equals(b.getText().toString().toUpperCase())){
                        AuxArray.add(HospitaisGerais.get(i));
                    }
                    }*/
                hospitalCidade.Hospitais=AuxArray;
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                MostrarFrag(hospitalCidade,"fragment");
                //  TextView t3 = findViewById(R.id.Text);
                //  t3.setText("Tamanho: " + MedicosHosp.size() + " 1 " + MedicosHosp.get(0).ESTABELECIMENTO);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*for(int i=0;i<HospitaisGerais.size();i++){
            if(HospitaisGerais.get(i).MUNICIPIO.equals(b.getText().toString().toUpperCase())){
                AuxArray.add(HospitaisGerais.get(i));
            }
        }*/


    }
    public void MostrarFrag(Fragment fragmento, String name){

        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.addToBackStack("pilha");
        transaction.replace (R.id.containerP,fragmento,name);
        transaction.commit();
    }

}
