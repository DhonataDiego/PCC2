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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dependentes extends Fragment  {


    public Dependentes() {

    }

    View v;
    public Usuario u;
    private DatabaseReference mDatabase;
    ArrayList<Dependente>dependentes;
    HlistDependentes hld = new HlistDependentes();
    int i;
    ImageButton b;
    public TextView pesq;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_dependentes, container, false);
        mDatabase= FirebaseDatabase.getInstance().getReference();

        b=v.findViewById(R.id.BProcurarDependentes);
        v.findViewById(R.id.BAdicionarDependentes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CadastrarDependente cd= new CadastrarDependente();
               cd.usuario=u;
               MostrarFrag2(cd,"fragment");
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarregarFrags();
            }
        });
        return v;
    }
    public void atualizar(){
        Fragment frg = null;
        frg = getFragmentManager().findFragmentById(R.id.DContainer);
        final android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }
    public void CarregarFrags(){
        dependentes = new ArrayList<>();
        Query q = mDatabase.child("Dependente").orderByChild("IDref").equalTo(u.getEmail());
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                   dependentes.add(Snap.getValue(Dependente.class));
                }
                if(dependentes!=null) {
                    for (i = 0; i < dependentes.size(); i++) {
                        hld = new HlistDependentes();
                        hld.u =dependentes.get(i);
                        hld.usuario=u;
                        MostrarFrag(hld, "Fragment");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void MostrarFrag(Fragment fragmento, String name){
    try {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        LinearLayout newlayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        newlayout.setLayoutParams(layoutParams);
        newlayout.setId(i + 1);
        LinearLayout parentlayout = v.findViewById(R.id.DContainer);
        parentlayout.addView(newlayout);
        transaction.replace(newlayout.getId(), fragmento, name);
        transaction.commit();
    }catch (Exception e ){

    }
    }

    public void MostrarFrag2(Fragment fragmento, String name){
        try {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.addToBackStack("pilha");
            transaction.replace(R.id.containerP, fragmento, name);
            transaction.commit();
        }catch (Exception e){}
        }

}
