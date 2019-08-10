package br.com.dhonatandiego.pcc;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MedicosHospital extends Fragment {

    public MedicosHospital() {
        // Required empty public constructor
    }
    ArrayList<Auxiliar>  AuxTeste= new ArrayList<>();
    ArrayList<Medicos>medicos;
    View v;
    InfoMedico infoMed;
    Character aux1='A',aux2;
    String aux;
    ArrayList<Medicos>MedicosCidade = new ArrayList<>();
    ArrayList<Medicos>Profissionais =new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_medicos_hospital, container, false);
        AdicionarLayouts();
        return v;
    }
    public void AdicionarLayouts(){
        aux1=Profissionais.get(0).DESCRICAO_CBO.replace("MEDICO","").trim().charAt(0);
        for(int i=0;i<Profissionais.size();i++) {
            aux2=Profissionais.get(i).DESCRICAO_CBO.replace("MEDICO","").trim().charAt(0);
            LinearLayout parentlayout = v.findViewById(R.id.LayoutMedicoHosp);

            if(!aux1.equals(aux2)||i==0) {
                LinearLayout newlayout2 = new LinearLayout(getContext());
                newlayout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TextView txtItem2 = new TextView(getContext());
                txtItem2.setText(" "+aux2);
                txtItem2.setTextColor(Color.WHITE);
                txtItem2.setTextSize(20);
                newlayout2.addView(txtItem2);
                newlayout2.setBackgroundColor(Color.rgb(118, 17, 22));
                parentlayout.addView(newlayout2);
                aux1=aux2;
            }
            LinearLayout newlayout = new LinearLayout(getContext());
            newlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            TextView txtItem = new TextView(getContext());
            txtItem.setText(Profissionais.get(i).DESCRICAO_CBO);
            txtItem.setTextSize(20);
            if (i%2==0) {
                newlayout.setBackgroundColor(Color.argb(25, 118, 17, 22));
            }
            newlayout.addView(txtItem);
            newlayout.setId(i);
            Auxiliar a = new Auxiliar();
            a.name =Profissionais.get(i).DESCRICAO_CBO;
            a.v= newlayout.getRootView();
            AuxTeste.add(a);
            newlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0; i<AuxTeste.size();i++){
                        if(AuxTeste.get(i).v==v){
                            Pesquisar(AuxTeste.get(i).name);
                        }
                    }
                }
            });
            aux=Profissionais.get(i).DESCRICAO_CBO;
            txtItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView t = (TextView) v;
                    Pesquisar(t.getText().toString());
                }
            });
            parentlayout.addView(newlayout);
        }
    }
    public void Pesquisar(String p){
        medicos = new ArrayList();
        infoMed=new InfoMedico();
        for(int i=0;i<MedicosCidade.size();i++){
           if(MedicosCidade.get(i).DESCRICAO_CBO.equals(p)) {
               medicos.add(MedicosCidade.get(i));
           }
        }
        infoMed.Medicos=medicos;
        MostrarFrag(infoMed,"fragment");
    }
    public void MostrarFrag(Fragment fragmento, String name){

        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.addToBackStack("pilha");
        transaction.replace (R.id.containerP,fragmento,name);
        transaction.commit();
    }
}
