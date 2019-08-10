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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class info_Dependentes extends Fragment {

    public info_Dependentes() {

    }
    Usuario u;
    int contador=0;
    View v;
    int i;
    HlistDependentes hld;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contador=0;
        CarregarFrags();
        v=inflater.inflate(R.layout.fragment_info__dependentes, container, false);

        return v;
    }
    public void CarregarFrags(){
       /* if(u.Dependentes!=null) {
            for (i = 0; i < u.Dependentes.size(); i++) {
                hld = new HlistDependentes();
                hld.u = u.Dependentes.get(i);
                MostrarFrag(hld, "Fragment");
            }
        }*/
    }
    public void MostrarFrag(Fragment fragmento, String name){

        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        LinearLayout newlayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        newlayout.setLayoutParams(layoutParams);
        newlayout.setId(i+1);
        LinearLayout parentlayout = v.findViewById(R.id.DepContainer);
        parentlayout.addView(newlayout);

        transaction.replace (newlayout.getId(),fragmento,name);
        transaction.commit();
    }

}
