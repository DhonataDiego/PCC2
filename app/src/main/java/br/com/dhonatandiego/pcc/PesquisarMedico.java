package br.com.dhonatandiego.pcc;

import android.graphics.Color;
import android.graphics.Paint;
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


public class PesquisarMedico extends Fragment {

    public PesquisarMedico() {
    }

    public ArrayList <Medicos>MedicosGerais;
    ArrayList<Medicos>AuxArray;
    View v;
    Character aux1,aux2;
    MedicosHospital medicosHospital;
    ArrayList<String>CidadesPernambuco = new ArrayList<>();
    ScrollView view;
    ArrayList<Auxiliar>  AuxTeste= new ArrayList<>();
    private DatabaseReference mDatabase;
    ProgressBar progressBar;
    String[] numeros;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pesquisar_medico, container, false);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        view = (ScrollView)v.findViewById(R.id.ScroolEstados);
        view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        view.setFocusable(true);
        progressBar = v.findViewById(R.id.progressBar);
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
            LinearLayout parentlayout = v.findViewById(R.id.layoutPesquisarMedico);
            LinearLayout Barrinhas = v.findViewById(R.id.layoutBarrinha);

            if(!aux1.equals(aux2)||i==0) {
                LinearLayout newlayout2 = new LinearLayout(getContext());
                newlayout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                final TextView txtItem2 = new TextView(getContext());
                txtItem2.setText(" "+aux2);
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
            txtItem.setText(CidadesPernambuco.get(i));
            txtItem.setTextSize(20);
            if (i%2==0)
                newlayout.setBackgroundColor(Color.argb(25, 118, 17, 22));

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

            newlayout.addView(txtItem);
            newlayout.setId(i);
            txtItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView t = (TextView) v;
                    Pesquisar(t);
                }
            });


            parentlayout.addView(newlayout);
        }
    }
    public void Pesquisar(TextView b){
        AuxArray=new ArrayList<>();
        medicosHospital=new MedicosHospital();
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Query q = mDatabase.child("MedicosT").child(b.getText().toString().toUpperCase());
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                   AuxArray.add(Snap.getValue(Medicos.class));
                }
                medicosHospital.MedicosCidade = AuxArray;
                for (int i = 0; i < AuxArray.size(); i++) {
                    if (medicosHospital.Profissionais.size() > 0) {
                        try{
                            if (!AuxArray.get(i).DESCRICAO_CBO.equals(medicosHospital.Profissionais.get(medicosHospital.Profissionais.size() - 1).DESCRICAO_CBO)) {
                                medicosHospital.Profissionais.add(AuxArray.get(i));}
                        }catch (Exception e){}
                    }
                    else {
                        medicosHospital.Profissionais.add(AuxArray.get(i));
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    MostrarFrag(medicosHospital, "fragment");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
           /* for (int i = 0; i < MedicosGerais.size(); i++) {
               try{
                    if (MedicosGerais.get(i).MUNICIPIO.equals(b.getText().toString().toUpperCase())) {
                        AuxArray.add(MedicosGerais.get(i));
                    }
                }catch (Exception e){
               }
            }*/

    }

    public void MostrarFrag(Fragment fragmento, String name){

        FragmentTransaction transaction= getFragmentManager().beginTransaction();
      //  transaction.addToBackStack("pilha");
        transaction.replace (R.id.containerP,fragmento,name);
        transaction.commit();
    }

}
