package br.com.dhonatandiego.pcc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HlistMedicos extends Fragment {

    public HlistMedicos() {
    }
    View v;
    Medicos m;
    PerfilMedico p2=new PerfilMedico();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_hlist_medicos, container, false);
        FrameLayout b=v.findViewById(R.id.Frame);
        try{
            TextView text = v.findViewById(R.id.MNome);
            TextView text2 = v.findViewById(R.id.MEspecialidade);
            text.setText(m.NOME);
            text2.setText(m.DESCRICAO_CBO);

        }
        catch (Exception e){}

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoMedico();
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
    public void DeletarMedico(){
        /* Fragment frg = null;
        frg = getFragmentManager().findFragmentById(R.id.MContainer);
        final android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();*/
    }
    public void InfoMedico(){
        Bundle args = new Bundle();
        args.putString("nome", m.NOME);
        args.putString("cns", m.CNS);
        args.putString("sexo", m.SEXO);
        args.putString("ibge", m.IBGE);
        args.putString("municipio", m.MUNICIPIO);
        args.putString("uf", m.UF);
        args.putString("cbo", m.CBO);
        args.putString("descricao_cbo", m.DESCRICAO_CBO);
        args.putString("cnes", m.CNES);
        args.putString("cnpj", m.CNPJ);
        args.putString("estabelecimento", m.ESTABELECIMENTO);
        args.putString("natureza_juridica", m.NATUREZA_JURIDICA);
        args.putString("descricao_natureza_juridica", m.DESCRICAO_NATUREZA_JURIDICA);
        args.putString("gestao", m.GESTAO);
        args.putString("sus", m.SUS);
        args.putString("residente", m.RESIDENTE);
        args.putString("preceptor", m.PRECEPTOR);
        args.putString("vinculo_estabelecimento",m.VINCULO_ESTABELECIMENTO);
        args.putString("vinculo_empregador", m.VINCULO_EMPREGADOR);
        args.putString("detalhamento_do_vinculo", m.DETALHAMENTO_DO_VINCULO);
        args.putString("ch_outro", m.CH_OUTROS);
        args.putString("ch_amb", m.CH_AMB);
        args.putString("ch_hosp", m.CH_HOSP);
        p2.ID= m.ID;
        p2.m=m;
        p2.setArguments(args);

        MostrarFrag(p2,"fragment");
    }
}
