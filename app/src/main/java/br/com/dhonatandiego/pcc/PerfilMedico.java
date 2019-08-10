package br.com.dhonatandiego.pcc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilMedico extends Fragment {


    public PerfilMedico() {
        // Required empty public constructor
    }

    View v;
    ImageButton b1,b2,b3;
    EditText Tnome,Tcns,Tsexo,Tibge,Tmunicipio,Tcbo,Tdescricao_cbo,Tcnes,Tcnpj,Tuf,Testabelecimento,Tnatureza_juridica,Tdescricao_natureza_juridica,
    Tgestao,Tsus,Tresidente,Tpreceptor,Tvinculo_estabelecimento,Tvinculo_empregador,Tdetalhamento_do_vinculo,Tch_outro,Tch_amb,Tch_hosp;
    int click=0;
    String ID;
    Medicos m;
    private DatabaseReference mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_perfil_medico, container, false);

        mDatabase= FirebaseDatabase.getInstance().getReference();
        b1= v.findViewById(R.id.Editar_Medico);
        b2= v.findViewById(R.id.Deletar_Medico);
        b3 =v.findViewById(R.id.btnSalvarMedico);


        Tnome= v.findViewById(R.id.MedNome);
        Tcns= v.findViewById(R.id.MedCNS);
        Tsexo = v.findViewById(R.id.Medsexo);
        Tibge = v.findViewById(R.id.MedIBGE);
        Tmunicipio = v.findViewById(R.id.Medmunicipio);
        Tcbo= v.findViewById(R.id.MedCBO);
        Tdescricao_cbo = v.findViewById(R.id.Meddescricao_cbo);
        Tcnes= v.findViewById(R.id.MedCNES);
        Tcnpj = v.findViewById(R.id.MedCNPJ);
        Tuf = v.findViewById(R.id.Medspinner);
        Testabelecimento = v.findViewById(R.id.Medestabelecimento);
        Tnatureza_juridica = v.findViewById(R.id.Mednatureza_juridica);
        Tdescricao_natureza_juridica = v.findViewById(R.id.Meddescricao_natureza_juridica);
        Tgestao= v.findViewById(R.id.Medgestao);
        Tsus = v.findViewById(R.id.MedSUS);
        Tresidente = v.findViewById(R.id.Medresidente);
        Tpreceptor = v.findViewById(R.id.Medpreceptor);
        Tvinculo_estabelecimento = v.findViewById(R.id.Medvinculo_estabelecimento);
        Tvinculo_empregador= v.findViewById(R.id.Medvinculo_empregador);
        Tdetalhamento_do_vinculo= v.findViewById(R.id.Meddetalhamento_do_vinculo);
        Tch_outro = v.findViewById(R.id.Medch_outro);
        Tch_amb= v.findViewById(R.id.Medch_amb);
        Tch_hosp= v.findViewById(R.id.Medch_hosp);


        String nome = getArguments().getString("nome");
        String cns = getArguments().getString("cns");
        String sexo = getArguments().getString("sexo");
        String ibge = getArguments().getString("ibge");
        String municipio = getArguments().getString("municipio");
        String uf = getArguments().getString("uf");
        String cbo = getArguments().getString("cbo");
        String descricao_cbo= getArguments().getString("descricao_cbo");
        String cnes= getArguments().getString("cnes");
        String cnpj= getArguments().getString("cnpj");
        String estabelecimento= getArguments().getString("estabelecimento");
        String natureza_juridica= getArguments().getString("natureza_juridica");
        String descricao_natureza_juridica= getArguments().getString("descricao_natureza_juridica");
        String gestao = getArguments().getString("gestao");
        String sus= getArguments().getString("sus");
        String residente= getArguments().getString("residente");
        String preceptor= getArguments().getString("preceptor");
        String vinculo_estabelecimento= getArguments().getString("vinculo_estabelecimento");
        String vinculo_empregador = getArguments().getString("vinculo_empregador");
        String detalhamento_do_vinculo= getArguments().getString("detalhamento_do_vinculo");
        String ch_outro= getArguments().getString("ch_outro");
        String ch_amb= getArguments().getString("ch_amb");
        String ch_hosp= getArguments().getString("ch_hosp");


        Tnome.setText(nome);
        Tcns.setText(cns);
        Tsexo.setText(sexo);
        Tibge.setText(ibge);
        Tmunicipio.setText(municipio);
        Tcbo.setText(cbo);
        Tdescricao_cbo.setText(descricao_cbo);
        Tcnes.setText(cnes);
        Tcnpj.setText(cnpj);
        Tuf.setText(uf);
        Testabelecimento.setText(estabelecimento);
        Tnatureza_juridica.setText(natureza_juridica);
        Tdescricao_natureza_juridica.setText(descricao_natureza_juridica);
        Tgestao.setText(gestao);
        Tsus.setText(sus);
        Tresidente.setText(residente);
        Tpreceptor.setText(preceptor);
        Tvinculo_estabelecimento.setText(vinculo_estabelecimento);
        Tvinculo_empregador.setText(vinculo_empregador);
        Tdetalhamento_do_vinculo.setText(detalhamento_do_vinculo);
        Tch_outro.setText(ch_outro);
        Tch_amb.setText(ch_amb);
        Tch_hosp.setText(ch_hosp);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("MedicosT").child(ID).removeValue();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(click==0) {
                Tnome.setEnabled(true);
                Tcns.setEnabled(true);
                Tsexo.setEnabled(true);
                Tibge.setEnabled(true);
                Tmunicipio.setEnabled(true);
                Tcbo.setEnabled(true);
                Tdescricao_cbo.setEnabled(true);
                Tcnes.setEnabled(true);
                Tcnpj.setEnabled(true);
                Tuf.setEnabled(true);
                Testabelecimento.setEnabled(true);
                Tnatureza_juridica.setEnabled(true);
                Tdescricao_natureza_juridica.setEnabled(true);
                Tgestao.setEnabled(true);
                Tsus.setEnabled(true);
                Tresidente.setEnabled(true);
                Tpreceptor.setEnabled(true);
                Tvinculo_estabelecimento.setEnabled(true);
                Tvinculo_empregador.setEnabled(true);
                Tdetalhamento_do_vinculo.setEnabled(true);
                Tch_outro.setEnabled(true);
                Tch_amb.setEnabled(true);
                Tch_hosp.setEnabled(true);
                b3.setVisibility(View.VISIBLE);
                click++;
            }
            else if(click==1){
                Tnome.setEnabled(false);
                Tcns.setEnabled(false);
                Tsexo.setEnabled(false);
                Tibge.setEnabled(false);
                Tmunicipio.setEnabled(false);
                Tcbo.setEnabled(false);
                Tdescricao_cbo.setEnabled(false);
                Tcnes.setEnabled(false);
                Tcnpj.setEnabled(false);
                Tuf.setEnabled(false);
                Testabelecimento.setEnabled(false);
                Tnatureza_juridica.setEnabled(false);
                Tdescricao_natureza_juridica.setEnabled(false);
                Tgestao.setEnabled(false);
                Tsus.setEnabled(false);
                Tresidente.setEnabled(false);
                Tpreceptor.setEnabled(false);
                Tvinculo_estabelecimento.setEnabled(false);
                Tvinculo_empregador.setEnabled(false);
                Tdetalhamento_do_vinculo.setEnabled(false);
                Tch_outro.setEnabled(false);
                Tch_amb.setEnabled(false);
                Tch_hosp.setEnabled(false);
                b3.setVisibility(View.INVISIBLE);
                click=0;
            }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                    DatabaseReference usersRef = mDatabase.child("MedicosT").child(Tmunicipio.getText().toString()).child(m.ID);
                    usersRef.setValue(m);

                    Tnome.setEnabled(false);
                    Tcns.setEnabled(false);
                    Tsexo.setEnabled(false);
                    Tibge.setEnabled(false);
                    Tmunicipio.setEnabled(false);
                    Tcbo.setEnabled(false);
                    Tdescricao_cbo.setEnabled(false);
                    Tcnes.setEnabled(false);
                    Tcnpj.setEnabled(false);
                    Tuf.setEnabled(false);
                    Testabelecimento.setEnabled(false);
                    Tnatureza_juridica.setEnabled(false);
                    Tdescricao_natureza_juridica.setEnabled(false);
                    Tgestao.setEnabled(false);
                    Tsus.setEnabled(false);
                    Tresidente.setEnabled(false);
                    Tpreceptor.setEnabled(false);
                    Tvinculo_estabelecimento.setEnabled(false);
                    Tvinculo_empregador.setEnabled(false);
                    Tdetalhamento_do_vinculo.setEnabled(false);
                    Tch_outro.setEnabled(false);
                    Tch_amb.setEnabled(false);
                    Tch_hosp.setEnabled(false);
                    b3.setVisibility(View.INVISIBLE);
                    click=0;
                  Toast.makeText(getContext(),"Alterado com Sucesso",Toast.LENGTH_LONG).show();
                }catch (Exception e ){
                    Toast.makeText(getContext(),"Erro Tente novamente mais tarde",Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }
}
