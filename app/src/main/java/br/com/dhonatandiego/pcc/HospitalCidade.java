package br.com.dhonatandiego.pcc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HospitalCidade extends Fragment {

    ArrayList<Hospital> Hospitais = new ArrayList<>();
    public HospitalCidade() {

    }
    HlistHospitais hlist;
    View v;
    LatLng latLng;
    String Permissão;
    int inicio=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       v=inflater.inflate(R.layout.fragment_hospital_cidade, container, false);
       carregarFrag();
        return v;
    }
    public void carregarFrag(){
        for (int i = inicio; i <inicio+50; i++) {
            if(i<Hospitais.size()){
                hlist = new HlistHospitais();
                hlist.h = Hospitais.get(i);
                hlist.currentLocationLatLong=latLng;
                MostrarFrag(hlist, "fragment",i);
                if(inicio+50<Hospitais.size()) {
                    Button b = v.findViewById(R.id.CarregarMais2);
                    b.setVisibility(View.VISIBLE);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            inicio += 50;
                            carregarFrag();
                        }
                    });
                }
            }
        }

    }
    public void MostrarFrag(Fragment fragmento, String name,int i){

        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        LinearLayout newlayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        newlayout.setLayoutParams(layoutParams);
        newlayout.setId(i+1);
        LinearLayout parentlayout = v.findViewById(R.id.layoutHospitalCidade);
        parentlayout.addView(newlayout);

        transaction.replace (newlayout.getId(),fragmento,name);
        transaction.commit();
    }
}
