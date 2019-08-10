package br.com.dhonatandiego.pcc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HlistHospitais extends Fragment {


    public HlistHospitais() {
        // Required empty public constructor
    }
    Hospital h;
    View v;
    LatLng currentLocationLatLong;
    InfoHospital infoHospital = new InfoHospital();
    HospitalFragment IH = new HospitalFragment();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_hlist_hospitais, container, false);
        TextView tNome= v.findViewById(R.id.NomeHospital);
        TextView tBairro= v.findViewById(R.id.HospitalBairro);
        TextView tCidade= v.findViewById(R.id.HospitalCidade);
        TextView tdistancia= v.findViewById(R.id.Hospitaldistancia);
        ImageButton setinha = v.findViewById(R.id.setinha);
        try {
            final double distancia=6371 *
                    Math.acos(
                            Math.cos(Math.toRadians(currentLocationLatLong.latitude)) *
                                    Math.cos(Math.toRadians(Double.parseDouble(h.LATITUDE))) *
                                    Math.cos(Math.toRadians(currentLocationLatLong.longitude) - Math.toRadians(Double.parseDouble(h.LONGITUDE))) +
                                    Math.sin(Math.toRadians(currentLocationLatLong.latitude)) *
                                            Math.sin(Math.toRadians(Double.parseDouble(h.LATITUDE)))
                    );

            tdistancia.setText(""+Math.ceil(distancia)+"km");

        }catch (Exception e){
            tdistancia.setText("Erro");
        }
        tNome.setText(h.NOME_FANTASIA);
        tBairro.setText(h.BAIRRO);
        tCidade.setText(h.MUNICIPIO+" "+h.UF);
        LinearLayout l =v.findViewById(R.id.HospitalClick);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoHospital();
            }
        });
        setinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoHospital();
            }
        });
        return v;
    }
    public void infoHospital(){
        Bundle args = new Bundle();
        args.putString("usuario",h.NOME_FANTASIA);
        args.putString("logradouro",h.LOGRADOURO);
        args.putString("cidade",h.MUNICIPIO);
        args.putString("bairro",h.BAIRRO);
        args.putString("tipo",h.TIPO_GESTAO);
        args.putString("UF",h.UF);
        args.putString("CEP",h.CEP);
        args.putString("CNPJM",h.CNPJ_MANTENEDORA);
        args.putString("CNPJP",h.CNPJ_PROPRIO);
        args.putString("CNES",h.CNES);
        args.putString("razao",h.RAZAO_SOCIAL);
        args.putString("numero",h.NUMERO);
        args.putString("IBGE",h.IBGE);
        infoHospital.setArguments(args);
        IH.h=h;
        MostrarFrag(IH,"fragment");
    }
    public void MostrarFrag(Fragment fragmento, String name){

        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.addToBackStack("pilha");
        transaction.replace (R.id.containerP,fragmento,name);
        transaction.commit();

    }
}
