package br.com.dhonatandiego.pcc;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InfoMedico extends Fragment {
    ArrayList<Medicos> Medicos ;
    HlistMedicos hlist;

    public InfoMedico() {}
    View v;
    int inicio=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_info_medico, container, false);
        carregarFrag();
        return v;
    }
    public void carregarFrag(){
            for (int i = inicio; i <inicio+50; i++) {
                if(i<Medicos.size()) {
                    hlist = new HlistMedicos();
                    hlist.m = Medicos.get(i);
                    MostrarFrag(hlist, "fragment", i);
                    if(inicio+50<Medicos.size()) {
                        Button b = v.findViewById(R.id.CarregarMais);
                        b.setVisibility(View.VISIBLE);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                inicio+=50;
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
        layoutParams.setMargins(0,15,0,0);
        newlayout.setLayoutParams(layoutParams);
        newlayout.setId(i+1);
        LinearLayout parentlayout = v.findViewById(R.id.fragmentInfoMedicos);

        if(i%2==0){
            newlayout.setBackgroundColor(Color.argb(25,118,17,22));
        }

        parentlayout.addView(newlayout);

        transaction.replace (newlayout.getId(),fragmento,name);
        transaction.commit();
    }
}
