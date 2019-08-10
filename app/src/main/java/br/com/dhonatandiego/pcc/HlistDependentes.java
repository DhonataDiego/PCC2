package br.com.dhonatandiego.pcc;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class HlistDependentes extends Fragment {
    int contador=0;
    View v;
    Dependente u;
    Usuario usuario;
    Perfil p = new Perfil();

    private DatabaseReference mDatabase;

    public HlistDependentes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contador=0;
        v=inflater.inflate(R.layout.fragment_hlist_dependentes, container, false);



        mDatabase= FirebaseDatabase.getInstance().getReference();

        TextView text = v.findViewById(R.id.DNome);
        TextView text2 = v.findViewById(R.id.DSUS);
        text.setText(u.Nome);
        text2.setText("N°SUS: "+u.SUS);

        v.findViewById(R.id.viewDependente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoDependente();
            }
        });
        return v;
    }
    public void MostrarFrag(Fragment fragmento, String name){

        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.addToBackStack("pilha");
        transaction.replace (R.id.containerP,fragmento,name);
        transaction.commit();

    }
    public void DeletarDependente(){
      // u.Dependentes.remove(u);
        Fragment frg = null;
        frg = getFragmentManager().findFragmentById(R.id.DContainer);
        final android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }
    public void InfoDependente(){
        Bundle args = new Bundle();
        args.putString("nome", u.Nome);
        args.putString("email", u.Email);
        args.putString("telefone", u.Telefone);
        args.putString("bairro", u.Bairro);
        args.putString("numero", u.Numero);
        args.putString("uf", u.UF);
        args.putString("cpf", u.CPF);
        args.putString("rg", u.RG);
        args.putString("endereço", u.Endereço);
        args.putString("cidade", u.Cidade);
        args.putString("SUS", u.SUS);
        p.setArguments(args);
        p.d=usuario;
        p.chave2="dependente";
        MostrarFrag(p,"fragment");
         /*  Query query = mDatabase.child("Usuario");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                 u= Snap.getValue(Usuario.class);


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }
}
