package br.com.dhonatandiego.pcc;


import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Perfil extends Fragment {

    View v;
    ImageButton b1,b2,b3;
    Boolean chave = false;
    String chave2="usuario";
    private DatabaseReference mDatabase;
    EditText Temail,Tnome,Ttelfone,Tendereço,Tbairro,Tnumero,Tcidade,Trg,TCPF,Tuf,TSUS;
    Usuario d;
    int click=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_perfil2, container, false);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        b1 = v.findViewById(R.id.Editar_Paciente);
        b2 = v.findViewById(R.id.Deletar_Paciente);
        b3 = v.findViewById(R.id.btnSalvar);
        if(chave==true){
            b1.setVisibility(View.VISIBLE);
        }
        if(chave2.equals("dependente")){
            b2.setVisibility(View.VISIBLE);
        }
        Temail = v.findViewById(R.id.Pemail);
        Tnome = v.findViewById(R.id.PNome);
        Ttelfone = v.findViewById(R.id.Ptelefone);
        Tendereço = v.findViewById(R.id.Pendereço);
        Tbairro = v.findViewById(R.id.Pbairro);
        Tnumero = v.findViewById(R.id.Pnumero);
        Tcidade = v.findViewById(R.id.Pcidade);
        Trg= v.findViewById(R.id.Prg);
        TCPF= v.findViewById(R.id.Pcpf);
        Tuf= v.findViewById(R.id.PUF);
        TSUS= v.findViewById(R.id.PSUS);

        final String email = getArguments().getString("email");
        final String nome = getArguments().getString("nome");
        final String telefone = getArguments().getString("telefone");
        final String endereço = getArguments().getString("endereço");
        final String bairro = getArguments().getString("bairro");
        final String numero = getArguments().getString("numero");
        final String cidade = getArguments().getString("cidade");
        final String uf= getArguments().getString("uf");
        final String rg= getArguments().getString("rg");
        final String CPF= getArguments().getString("cpf");
        final String SUS= getArguments().getString("SUS");

        Temail.setText(email);
        Tnome.setText(nome);
        Ttelfone.setText(telefone);
        Tendereço.setText(endereço);
        Tbairro.setText(bairro);
        Tnumero.setText(numero);
        Tcidade.setText(cidade);
        Tuf.setText(uf);
        Trg.setText(rg);
        TCPF.setText(CPF);
        TSUS.setText(SUS);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click==0) {
                    Temail.setEnabled(true);
                    Tnome.setEnabled(true);
                    Ttelfone.setEnabled(true);
                    Tendereço.setEnabled(true);
                    Tbairro.setEnabled(true);
                    Tnumero.setEnabled(true);
                    Tcidade.setEnabled(true);
                    Trg.setEnabled(true);
                    TCPF.setEnabled(true);
                    Tuf.setEnabled(true);
                    TSUS.setEnabled(true);
                    b3.setVisibility(View.VISIBLE);
                    click++;
                }
                else if(click==1){
                    Temail.setEnabled(false);
                    Tnome.setEnabled(false);
                    Ttelfone.setEnabled(false);
                    Tendereço.setEnabled(false);
                    Tbairro.setEnabled(false);
                    Tnumero.setEnabled(false);
                    Tcidade.setEnabled(false);
                    Trg.setEnabled(false);
                    TCPF.setEnabled(false);
                    Tuf.setEnabled(false);
                    TSUS.setEnabled(false);
                    b3.setVisibility(View.INVISIBLE);
                    click=0;
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(chave2.equals("dependente")){
                    try {
                        mDatabase.child("Dependente").child(CPF).removeValue();
                        Toast.makeText(getContext(),"Removido com Sucesso",Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        Toast.makeText(getContext(),"Erro Tente novamente mais tarde",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(chave2.equals("dependente")) {
                    try {
                        mDatabase.child("Dependente").child(CPF).child("Nome").setValue(Tnome.getText().toString());
                        mDatabase.child("Dependente").child(CPF).child("Email").setValue(Temail.getText().toString());
                        mDatabase.child("Dependente").child(CPF).child("Telefone").setValue(Ttelfone.getText().toString());
                        mDatabase.child("Dependente").child(CPF).child("Endereço").setValue(Tendereço.getText().toString());
                        mDatabase.child("Dependente").child(CPF).child("Bairro").setValue(Tbairro.getText().toString());
                        mDatabase.child("Dependente").child(CPF).child("Numero").setValue(Tnumero.getText().toString());
                        mDatabase.child("Dependente").child(CPF).child("Cidade").setValue(Tcidade.getText().toString());
                        mDatabase.child("Dependente").child(CPF).child("UF").setValue(Tuf.getText().toString());
                        mDatabase.child("Dependente").child(CPF).child("RG").setValue(Trg.getText().toString());
                        mDatabase.child("Dependente").child(CPF).child("CPF").setValue(TCPF.getText().toString());
                        mDatabase.child("Dependente").child(CPF).child("SUS").setValue(TSUS.getText().toString());
                        Temail.setEnabled(false);
                        Tnome.setEnabled(false);
                        Ttelfone.setEnabled(false);
                        Tendereço.setEnabled(false);
                        Tbairro.setEnabled(false);
                        Tnumero.setEnabled(false);
                        Tcidade.setEnabled(false);
                        Trg.setEnabled(false);
                        TCPF.setEnabled(false);
                        Tuf.setEnabled(false);
                        TSUS.setEnabled(false);
                        b3.setVisibility(View.INVISIBLE);
                        click=0;
                        Toast.makeText(getContext(),"Dependente alterado com Sucesso",Toast.LENGTH_LONG).show();
                    }catch (Exception e ){
                        Toast.makeText(getContext(),"Erro Tente novamente mais tarde",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                  try {
                        mDatabase.child("Usuario").child(d.getNome()).child("Nome").setValue(Tnome.getText().toString());
                        mDatabase.child("Usuario").child(d.getNome()).child("Email").setValue(Temail.getText().toString());
                        mDatabase.child("Usuario").child(d.getNome()).child("Telefone").setValue(Ttelfone.getText().toString());
                        mDatabase.child("Usuario").child(d.getNome()).child("Endereço").setValue(Tendereço.getText().toString());
                        mDatabase.child("Usuario").child(d.getNome()).child("Bairro").setValue(Tbairro.getText().toString());
                        mDatabase.child("Usuario").child(d.getNome()).child("Numero").setValue(Tnumero.getText().toString());
                        mDatabase.child("Usuario").child(d.getNome()).child("Cidade").setValue(Tcidade.getText().toString());
                        mDatabase.child("Usuario").child(d.getNome()).child("UF").setValue(Tuf.getText().toString());
                        mDatabase.child("Usuario").child(d.getNome()).child("RG").setValue(Trg.getText().toString());
                        mDatabase.child("Usuario").child(d.getNome()).child("CPF").setValue(TCPF.getText().toString());
                        mDatabase.child("Usuario").child(d.getNome()).child("SUS").setValue(TSUS.getText().toString());
                        mDatabase.child("Usuario").child(d.getNome()).child("Permissão").setValue("Comum");
                        Temail.setEnabled(false);
                        Tnome.setEnabled(false);
                        Ttelfone.setEnabled(false);
                        Tendereço.setEnabled(false);
                        Tbairro.setEnabled(false);
                        Tnumero.setEnabled(false);
                        Tcidade.setEnabled(false);
                        Trg.setEnabled(false);
                        TCPF.setEnabled(false);
                        Tuf.setEnabled(false);
                        TSUS.setEnabled(false);
                        b3.setVisibility(View.INVISIBLE);
                        click=0;
                        Toast.makeText(getContext(),"Perfil alterado com Sucesso",Toast.LENGTH_LONG).show();
                    }catch (Exception e ){
                        Toast.makeText(getContext(),"Erro Tente novamente mais tarde",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return v;
    }
}
