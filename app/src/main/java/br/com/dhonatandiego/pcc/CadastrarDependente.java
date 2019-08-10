package br.com.dhonatandiego.pcc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class CadastrarDependente extends Fragment {
    int cont=0;
    public static final int IMAGEM_INTERNA = 12;
    private Uri filePath;
    View v;
    private DatabaseReference mDatabase;
    ImageView imagemView;
    Perfil p=new Perfil();
    Dependente d;
    Usuario usuario;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDatabase= FirebaseDatabase.getInstance().getReference();
        v=inflater.inflate(R.layout.fragment_cadastrar_dependente, container, false);
        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        ImageButton ib=v.findViewById(R.id.imageButton);
        // Initializing a String Array
        String[] estados = new String[]{
                "UF","AC", "AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA"
                ,"PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","SP"
        };

        final List<String> plantsList = new ArrayList<>(Arrays.asList(estados));

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rotacionar();
            }
        });
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position==0) {
                    // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
        v.findViewById(R.id.cadastro2_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastrarDependente();
            }
        });

        return v;
    }
    public void pegarImagem(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGEM_INTERNA);
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent){
        if(requestCode == IMAGEM_INTERNA){
            if(responseCode == RESULT_OK){

                Uri imagemSelecionada = intent.getData();
                filePath = imagemSelecionada;
                imagemView = (ImageView)v.findViewById(R.id.Cadastro_ImgPerfil);
                Bitmap imagem = null;
                try {
                    imagem = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                    imagemView.setImageBitmap(imagem);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void Rotacionar(){
        if(cont==0) {
            imagemView.setRotation(90);
            cont=1;
        }
        else if(cont==1) {
            imagemView.setRotation(180);
            cont=2;
        }
        else if(cont==2) {
            imagemView.setRotation(270);
            cont=3;
        }
        else if(cont==3) {
            imagemView.setRotation(360);
            cont=0;
        }
    }
    public void CadastrarDependente(){
        try {
            EditText Temail = v.findViewById(R.id.Cadastro_Email);
            EditText Tnome = v.findViewById(R.id.Cadastro_Nome);
            EditText Ttelfone = v.findViewById(R.id.Cadastro_Telefone);
            EditText Tendereço = v.findViewById(R.id.Cadastro_endereço);
            EditText Tbairro = v.findViewById(R.id.Cadastro_bairro);
            EditText Tnumero = v.findViewById(R.id.Cadastro_numero);
            EditText Tcidade = v.findViewById(R.id.Cadastro_cidade);
            EditText Trg= v.findViewById(R.id.Cadastro_RG);
            EditText TCPF= v.findViewById(R.id.Cadastro_CPF);
            EditText TSUS= v.findViewById(R.id.Cadastro_SUS);
            Spinner TUF= v.findViewById(R.id.spinner);

            String email = Temail.getText().toString();
            String nome = Tnome.getText().toString();
            String telefone = Ttelfone.getText().toString();
            String endereço = Tendereço.getText().toString();
            String bairro = Tbairro.getText().toString();
            String numero = Tnumero.getText().toString();
            String cidade = Tcidade.getText().toString();
            String uf= TUF.getSelectedItem().toString();
            String rg= Trg.getText().toString();
            String CPF= TCPF.getText().toString();
            String SUS= TSUS.getText().toString();

            d = new Dependente(usuario.getEmail(),nome,email,telefone,endereço,bairro,numero,cidade,uf,rg,CPF,SUS);
                          /*  mDatabase.child("Dependente").child(d.getCpf()).child("Nome").setValue(d.getNome());
                            mDatabase.child("Dependente").child(d.getNome()).child("Email").setValue(d.getEmail());
                            mDatabase.child("Dependente").child(d.getNome()).child("Senha").setValue(d.getSenha());
                            mDatabase.child("Dependente").child(d.getNome()).child("Telefone").setValue(d.getTelefone());
                            mDatabase.child("Dependente").child(d.getNome()).child("Endereço").setValue(d.getEndereço());
                            mDatabase.child("Dependente").child(d.getNome()).child("Bairro").setValue(d.getBairro());
                            mDatabase.child("Dependente").child(d.getNome()).child("Numero").setValue(d.getNumero());
                            mDatabase.child("Dependente").child(d.getNome()).child("Cidade").setValue(d.getCidade());
                            mDatabase.child("Dependente").child(d.getNome()).child("UF").setValue(d.getUf());
                            mDatabase.child("Dependente").child(d.getNome()).child("RG").setValue(d.getRg());
                            mDatabase.child("Dependente").child(d.getNome()).child("CPF").setValue(d.getCpf());
                            mDatabase.child("Dependente").child(d.getNome()).child("SUS").setValue(d.getSUS());
                            mDatabase.child("Dependente").child(d.getNome()).child("Permissão").setValue("Comum");*/


                          mDatabase.child("Dependente").child(d.CPF).setValue(d);
            Toast.makeText(getContext(),"Dependente cadastrado com sucesso",Toast.LENGTH_SHORT).show();
                            Bundle args = new Bundle();
                            args.putString("nome",d.Nome);
                            args.putString("email",d.Email);
                            args.putString("telefone",d.Telefone);
                            args.putString("bairro",d.Bairro);
                            args.putString("numero",d.Numero);
                            args.putString("uf",d.UF);
                            args.putString("cpf",d.CPF);
                            args.putString("rg",d.RG);
                            args.putString("endereço",d.Endereço);
                            args.putString("cidade",d.Cidade);
                            p.setArguments(args);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"Erro, Tente novamente mais tarde",Toast.LENGTH_SHORT).show();
        }
    }
}
