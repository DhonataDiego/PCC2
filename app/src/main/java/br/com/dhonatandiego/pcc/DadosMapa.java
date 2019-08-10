package br.com.dhonatandiego.pcc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


public class DadosMapa extends Fragment {


    public DadosMapa() {
    }
    public double Valor ;
    public LatLng latLng;
    public boolean checked =false;
    TextView tv;
    View v;
    Mapa m=new Mapa();
    Switch s;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_dados_mapa, container, false);
        tv= v.findViewById(R.id.valor);
        SeekBar seekBar = v.findViewById(R.id.seekBar);
        s= v.findViewById(R.id.switch1);

        m.valor=1000*Double.parseDouble(tv.getText().toString().replace("km","").trim());
        MostrarMapa(m,"fragment");

       v.findViewById(R.id.SalvarConfig).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(m.latLng1!=null || s.isChecked()==false){
                   latLng =m.latLng1;
                   Valor=1000*Double.parseDouble(tv.getText().toString().replace("km","").trim());
                   checked=s.isChecked();
                   Toast.makeText(getContext(),"Dados de Pesquisa Salvos!",Toast.LENGTH_LONG).show();
               }
               else{
                   Toast.makeText(getContext(),"Marque o Local no Mapa",Toast.LENGTH_LONG).show();
               }
           }
       });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText(""+progress+" km");
                m.valor=1000*Double.parseDouble(tv.getText().toString().replace("km","").trim());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return v;
    }
    public void MostrarMapa(Fragment fragment,String a){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentMapa, fragment, a);
        transaction.commit();

    }
}
