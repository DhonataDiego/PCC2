package br.com.dhonatandiego.pcc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoHospital extends Fragment {


    public InfoHospital() {
        // Required empty public constructor
    }

    ImageButton bexc,bedit,bsalv;
    View v;
    String latitude,longitude;
    int click=0;
    private DatabaseReference mDatabase;
    TextView text,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11,text12,text13;
    String Permissão;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_hospital, container, false);
        bexc = v.findViewById(R.id.imageButton4);
        bedit= v.findViewById(R.id.imageButton3);
        bsalv = v.findViewById(R.id.imageButton6);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        text = v.findViewById(R.id.HNome);
        text2 = v.findViewById(R.id.Hcidade);
        text3 = v.findViewById(R.id.Hlogradouro);
        text4 = v.findViewById(R.id.Hbairro);
        text5 = v.findViewById(R.id.Htipo);
        text6 = v.findViewById(R.id.HUF);
        text7 = v.findViewById(R.id.HCEP);
        text8 = v.findViewById(R.id.HCNPJMANT);
        text9 = v.findViewById(R.id.HCNPJPROP);
        text10 = v.findViewById(R.id.HCNES);
        text11 = v.findViewById(R.id.HRazao);
        text12 = v.findViewById(R.id.HNumero);
        text13 = v.findViewById(R.id.HIBGE);
            bexc.setVisibility(View.VISIBLE);
            bedit.setVisibility(View.VISIBLE);
        final String unidade = getArguments().getString("usuario");
        String logradouro = getArguments().getString("logradouro");
        String cidade = getArguments().getString("cidade");
        String bairro = getArguments().getString("bairro");
        String tipo = getArguments().getString("tipo");
        String UF = getArguments().getString("UF");
        String cep= getArguments().getString("CEP");
        String cnpjm= getArguments().getString("CNPJM");
        String cnpjp=getArguments().getString("CNPJP");
        String cnes= getArguments().getString("CNES");
        String razao= getArguments().getString("razao");
        String numero= getArguments().getString("numero");
        String ibge= getArguments().getString("IBGE");

        text.setText(unidade);
        text2.setText(cidade);
        text3.setText(logradouro);
        text4.setText(bairro);
        text5.setText(tipo);
        text6.setText(UF);
        text7.setText(cep);
        text8.setText(cnpjm);
        text9.setText(cnpjp);
        text10.setText(cnes);
        text11.setText(razao);
        text12.setText(numero);
        text13.setText(ibge);
        bexc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Hospitais").child(unidade).removeValue();
                try {
                    Toast.makeText(getContext(), "Hospital Removido com Sucesso", Toast.LENGTH_LONG).show();
                }catch (Exception e){}
                }
        });
        bedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"clicou "+click,Toast.LENGTH_LONG).show();
                if(click==0) {
                    text.setEnabled(true);
                    text2.setEnabled(true);
                    text3.setEnabled(true);
                    text4.setEnabled(true);
                    text5.setEnabled(true);
                    text6.setEnabled(true);
                    text7.setEnabled(true);
                    text8.setEnabled(true);
                    text9.setEnabled(true);
                    text10.setEnabled(true);
                    text11.setEnabled(true);
                    text12.setEnabled(true);
                    text13.setEnabled(true);
                    bsalv.setVisibility(View.VISIBLE);
                    click=1;
                }
                else if(click==1) {
                    text.setEnabled(false);
                    text2.setEnabled(false);
                    text3.setEnabled(false);
                    text4.setEnabled(false);
                    text5.setEnabled(false);
                    text6.setEnabled(false);
                    text7.setEnabled(false);
                    text8.setEnabled(false);
                    text9.setEnabled(false);
                    text10.setEnabled(false);
                    text11.setEnabled(false);
                    text12.setEnabled(false);
                    text13.setEnabled(false);
                    bsalv.setVisibility(View.INVISIBLE);
                    click=0;
                }
            }
        });
        bsalv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             try {
                 Hospital h2 = new Hospital();
                 h2.NOME_FANTASIA = text.getText().toString();
                 h2.MUNICIPIO = text2.getText().toString();
                 h2.CEP = text7.getText().toString();
                 h2.LOGRADOURO = text3.getText().toString();
                 h2.BAIRRO = text4.getText().toString();
                 h2.TIPO_GESTAO = text5.getText().toString();
                 h2.NUMERO = text12.getText().toString();
                 h2.RAZAO_SOCIAL = text11.getText().toString();
                 h2.CNES = text10.getText().toString();
                 h2.IBGE = text13.getText().toString();
                 h2.UF = text6.getText().toString();
                 h2.CNPJ_PROPRIO = text9.getText().toString();
                 h2.CNPJ_MANTENEDORA = text8.getText().toString();

                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("NOME_FANTASIA").setValue(h2.NOME_FANTASIA);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("MUNICIPIO").setValue(h2.MUNICIPIO);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("CEP").setValue(h2.CEP);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("LOGRADOURO").setValue(h2.LOGRADOURO);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("BAIRRO").setValue(h2.BAIRRO);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("TIPO_GESTAO").setValue(h2.TIPO_GESTAO);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("NUMERO").setValue(h2.NUMERO);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("RAZAO_SOCIAL").setValue(h2.RAZAO_SOCIAL);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("CNES").setValue(h2.CNES);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("IBGE").setValue(h2.IBGE);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("UF").setValue(h2.UF);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("CNPJ_PROPRIO").setValue(h2.CNPJ_PROPRIO);
                 mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).child("CNPJ_MANTENEDORA").setValue(h2.CNPJ_MANTENEDORA);

                 Toast.makeText(getContext(),"Alterado com sucesso",Toast.LENGTH_LONG).show();
             }
             catch (Exception e){
                 Toast.makeText(getContext(),"Erro,Tente novamente mais tarde",Toast.LENGTH_LONG).show();
             }
             }
        });
        return v;
    }
}
