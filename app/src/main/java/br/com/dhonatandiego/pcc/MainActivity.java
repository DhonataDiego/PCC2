package br.com.dhonatandiego.pcc;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.dhonatandiego.pcc.exception.ValidationException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager FragTela;
    public FragmentTransaction transaction;
    private DatabaseReference mDatabase;
    Mapa2 mapa2 = new Mapa2();
    telaLogin tl=new telaLogin();
    Perfil p=new Perfil();
    PerfilMedico p2 =new PerfilMedico();
    Cadastro c = new Cadastro();
    Usuario u,uaux;
    PesquisarMedico pesquisarMedico =new PesquisarMedico();
    public static final int IMAGEM_INTERNA = 12;
    private Uri filePath;
    NavigationView navigationView;
    boolean logado = false;
    ImageView imagemView;
    PesquisarHospital pesquisarHospital= new PesquisarHospital();
    Dependentes d;
    int cont=0;
;
    DadosMapa dadosMapa=new DadosMapa();
    List<Medicos> ProfissionaisList = new ArrayList();

    private List<Hospital> HospitaisList = new ArrayList();
    ArrayList<String> CidadesPernambuco = new ArrayList<>();
    LatLng latLng;
    int auxHosp=0,auxMed=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CarregarCidadesGerais();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        latLng=mapa2.currentLocationLatLong;
        MostrarFrag(mapa2,"MapsFragment");
        }
    public void CarregarCidadesGerais() {
        InputStream is = getResources().openRawResource(R.raw.cidadespe);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String linha = "";
        try {
            while (((linha = bufferedReader.readLine()) != null)) {
                String[] token = linha.split(";");
                CidadesPernambuco.add(token[0]);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Erro ao ler a linha: " + linha, Toast.LENGTH_LONG);
            e.printStackTrace();
        } finally {
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void Login(View v){
        EditText Temail = findViewById(R.id.email);
        EditText Tsenha = findViewById(R.id.password12);

        String email = Temail.getText().toString();
        String senha = Tsenha.getText().toString();

        u = new Usuario(email,senha);

        if(!email.equals("") && !senha.equals("")){
            ConexaoActivity.getFirebaseAuth().signInWithEmailAndPassword(email,senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                alert("logado");
                                IdentificarUsuario();
                                logado=true;
                                AtivarCampos();
                                mapa2=new Mapa2();
                                MostrarFrag(mapa2,"fragment");
                            } else {
                               alert("falha no login");
                            }
                        }
                    });
        }
        else{
            alert("Preencha todos os Campos");
        }
        }
        public void IdentificarUsuario(){

            Query query = mDatabase.child("Usuario");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    FirebaseUser user =  ConexaoActivity.getFirebaseAuth().getCurrentUser();
                    for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                        u= Snap.getValue(Usuario.class);
                        if(u.getEmail().equals(user.getEmail())) {
                            uaux=u;
                            Bundle args = new Bundle();
                            if(u.getPermissão().equals("Funcionario")){
                                navigationView.getMenu().findItem(R.id.ItemHospital).setVisible(true);
                                navigationView.getMenu().findItem(R.id.ItemMedico).setVisible(true);
                            }
                            args.putString("nome", u.getNome());
                            args.putString("email", u.getEmail());
                            args.putString("telefone", u.getTelefone());
                            args.putString("bairro", u.getBairro());
                            args.putString("numero", u.getNumero());
                            args.putString("uf", u.getUf());
                            args.putString("cpf", u.getCpf());
                            args.putString("rg", u.getRg());
                            args.putString("endereço", u.getEndereço());
                            args.putString("cidade", u.getCidade());
                            args.putString("SUS", u.getSUS());
                            p.setArguments(args);
                            TextView t=findViewById(R.id.nav_Nome);
                            TextView t2=findViewById(R.id.nav_N_SUS);
                            t.setText(u.getNome());
                            t2.setText("N°SUS: "+u.getSUS());
                        }
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    public void Cadastrar(View v){
        boolean valido;
        try {
            EditText Temail = findViewById(R.id.Cadastro_Email);
            EditText Tsenha = findViewById(R.id.Cadastro_senha);
            EditText Tnome = findViewById(R.id.Cadastro_Nome);
            EditText Ttelfone = findViewById(R.id.Cadastro_Telefone);
            EditText Tendereço = findViewById(R.id.Cadastro_endereço);
            EditText Tbairro = findViewById(R.id.Cadastro_bairro);
            EditText Tnumero = findViewById(R.id.Cadastro_numero);
            EditText Tcidade = findViewById(R.id.Cadastro_cidade);
            EditText Trg= findViewById(R.id.Cadastro_RG);
            EditText TCPF= findViewById(R.id.Cadastro_CPF);
            EditText TSUS= findViewById(R.id.Cadastro_SUS);
            Spinner TUF= findViewById(R.id.spinner);
            EditText Tconfirme = findViewById(R.id.Cadastro_confirm);

            String email = Temail.getText().toString();
            String senha = Tsenha.getText().toString();
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
            String confirm =Tconfirme.getText().toString();

            if(senha.equals(confirm)){
           u = new Usuario(nome,email,senha,telefone,endereço,bairro,numero,cidade,uf,rg,CPF,SUS);
            valido = Util.CamposPreenchidos(u.getSenha(),u.getEmail());
            if(valido) {
                ConexaoActivity.getFirebaseAuth().createUserWithEmailAndPassword(u.getEmail(), u.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDatabase.child("Usuario").child(u.getNome()).child("Nome").setValue(u.getNome());
                            mDatabase.child("Usuario").child(u.getNome()).child("Email").setValue(u.getEmail());
                            mDatabase.child("Usuario").child(u.getNome()).child("Senha").setValue(u.getSenha());
                            mDatabase.child("Usuario").child(u.getNome()).child("Telefone").setValue(u.getTelefone());
                            mDatabase.child("Usuario").child(u.getNome()).child("Endereço").setValue(u.getEndereço());
                            mDatabase.child("Usuario").child(u.getNome()).child("Bairro").setValue(u.getBairro());
                            mDatabase.child("Usuario").child(u.getNome()).child("Numero").setValue(u.getNumero());
                            mDatabase.child("Usuario").child(u.getNome()).child("Cidade").setValue(u.getCidade());
                            mDatabase.child("Usuario").child(u.getNome()).child("UF").setValue(u.getUf());
                            mDatabase.child("Usuario").child(u.getNome()).child("RG").setValue(u.getRg());
                            mDatabase.child("Usuario").child(u.getNome()).child("CPF").setValue(u.getCpf());
                            mDatabase.child("Usuario").child(u.getNome()).child("SUS").setValue(u.getSUS());
                            mDatabase.child("Usuario").child(u.getNome()).child("Permissão").setValue("Comum");

                            Bundle args = new Bundle();
                            args.putString("nome",u.getNome());
                            args.putString("email",u.getEmail());
                            args.putString("telefone",u.getTelefone());
                            args.putString("bairro",u.getBairro());
                            args.putString("numero",u.getNumero());
                            args.putString("uf",u.getUf());
                            args.putString("cpf",u.getCpf());
                            args.putString("rg",u.getRg());
                            args.putString("endereço",u.getEndereço());
                            args.putString("cidade",u.getCidade());
                            uaux=u;
                            p.setArguments(args);
                            AtivarCampos();
                            logado=true;
                            TextView t=findViewById(R.id.nav_Nome);
                            TextView t2=findViewById(R.id.nav_N_SUS);
                            t.setText(u.getNome());
                            t2.setText("N°SUS: "+u.getSUS());
                            alert("usuario cadastrado com sucesso");
                            MostrarFrag(new Mapa(),"fragment");
                        } else {
                            alert("falha ao cadastrar,tente novamente mais tarde");
                        }
                    }
                });}
            }  else {
                alert("Senhas não Corresponde");
            }
        } catch (ValidationException e) {
            e.printStackTrace();
            valido = false;
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void CadastrarMedico(View v){
        try {
            EditText TNome = findViewById(R.id.MedNome);
            EditText TCNS = findViewById(R.id.MedCNS);
            EditText TSexo = findViewById(R.id.Medsexo);
            EditText TIBGE = findViewById(R.id.MedIBGE);
            EditText TMunicipio = findViewById(R.id.Medmunicipio);
            EditText TCBO = findViewById(R.id.MedCBO);
            EditText TdesCBO = findViewById(R.id.Meddescricao_cbo);
            EditText TCNES= findViewById(R.id.MedCNES);
            EditText TCNPJ= findViewById(R.id.MedCNPJ);
            EditText TnatJur= findViewById(R.id.Mednatureza_juridica);
            Spinner TUF= findViewById(R.id.Medspinner);
            EditText Testabelecimento= findViewById(R.id.Medestabelecimento);
            EditText TdesNatJur= findViewById(R.id.Meddescricao_natureza_juridica);
            EditText TGestao= findViewById(R.id.Medgestao);
            EditText TSUS= findViewById(R.id.MedSUS);
            EditText Tresidente = findViewById(R.id.Medresidente);
            EditText TPreceptor= findViewById(R.id.Medpreceptor);
            EditText TVinculoEst= findViewById(R.id.Medvinculo_estabelecimento);
            EditText TVinculoEmp= findViewById(R.id.Medvinculo_empregador);
            EditText TChOut= findViewById(R.id.Medch_outro);
            EditText TdetVin= findViewById(R.id.Meddetalhamento_do_vinculo);
            EditText TCHamb= findViewById(R.id.Medch_amb);
            EditText TchHosp= findViewById(R.id.Medch_hosp);

            Medicos M = new Medicos();
            M.NOME=TNome.getText().toString();
            M.CNS=TCNS.getText().toString();
            M.SEXO=TSexo.getText().toString();
            M.IBGE=TIBGE.getText().toString();
            M.UF=TUF.getSelectedItem().toString();
            M.MUNICIPIO=TMunicipio.getText().toString();
            M.CBO=TCBO.getText().toString();
            M.DESCRICAO_CBO=TdesCBO.getText().toString();
            M.CNES=TCNES.getText().toString();
            M.CNPJ=TCNPJ.getText().toString();
            M.ESTABELECIMENTO=Testabelecimento.getText().toString();
            M.NATUREZA_JURIDICA=TnatJur.getText().toString();
            M.DESCRICAO_NATUREZA_JURIDICA=TdesNatJur.getText().toString();
            M.GESTAO=TGestao.getText().toString();
            M.SUS=TSUS.getText().toString();
            M.RESIDENTE=Tresidente.getText().toString();
            M.PRECEPTOR=TPreceptor.getText().toString();
            M.VINCULO_ESTABELECIMENTO=TVinculoEst.getText().toString();
            M.VINCULO_EMPREGADOR=TVinculoEmp.getText().toString();
            M.DETALHAMENTO_DO_VINCULO=TdetVin.getText().toString();
            M.CH_OUTROS=TChOut.getText().toString();
            M.CH_AMB=TCHamb.getText().toString();
            M.CH_HOSP=TchHosp.getText().toString();
            M.ID="36454";
            mDatabase.child("MedicosT").child(M.MUNICIPIO).child(M.ID).setValue(M);


            Bundle args = new Bundle();
            args.putString("nome",TNome.getText().toString());
            args.putString("cns",TCNS.getText().toString());
            args.putString("sexo",TSexo.getText().toString());
            args.putString("ibge",TIBGE.getText().toString());
            args.putString("municipio",TMunicipio.getText().toString());
            args.putString("cbo",TCBO.getText().toString());
            args.putString("descricao_cbo",TdesCBO.getText().toString());
            args.putString("cnes",TCNES.getText().toString());
            args.putString("cnpj",TCNPJ.getText().toString());
            args.putString("natureza_juridica",TnatJur.getText().toString());
            args.putString("uf",TUF.getSelectedItem().toString());
            args.putString("estabelecimento",Testabelecimento.getText().toString());
            args.putString("descricao_natureza_juridica",TdesNatJur.getText().toString());
            args.putString("gestao",TGestao.getText().toString());
            args.putString("sus",TSUS.getText().toString());
            args.putString("preceptor",TPreceptor.getText().toString());
            args.putString("vinculo_estabelecimento",TVinculoEst.getText().toString());
            args.putString("vinculo_empregador",TVinculoEmp.getText().toString());
            args.putString("ch_outro",TChOut.getText().toString());
            args.putString("detalhamento_do_vinculo",TdetVin.getText().toString());
            args.putString("ch_amb",TCHamb.getText().toString());
            args.putString("ch_hosp",TchHosp.getText().toString());
            args.putString("residente",Tresidente.getText().toString());

            p2.setArguments(args);
            alert("Médico cadastrado com sucesso");
            MostrarFrag(p2,"fragment");
          } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void setarLoc(View v){
        EditText TLatitude= findViewById(R.id.HLatitude_Cad);
        EditText TLongitude= findViewById(R.id.HLongitude_Cad);
        if(mapa2.currentLocationLatLong!=null){
            TLatitude.setText(""+mapa2.currentLocationLatLong.latitude);
            TLongitude.setText(""+mapa2.currentLocationLatLong.longitude);
        }
        else{
            alert("Não foi possivel obter a localização");
        }
    }
    public void CadastrarHospital(View v){
            EditText TNome = findViewById(R.id.HNome_Cad);
            EditText TCidade = findViewById(R.id.Hcidade_Cad);
            EditText TCep = findViewById(R.id.HCEP_Cad);
            EditText TLogradouro = findViewById(R.id.Hlogradouro_Cad);
            EditText TBairro = findViewById(R.id.Hbairro_Cad);
            EditText Ttipo = findViewById(R.id.Htipo_Cad);
            EditText Tnumero = findViewById(R.id.HNumero_Cad);
            EditText Trazao= findViewById(R.id.HRazao_Cad);
            EditText Tcnes= findViewById(R.id.HCNES_Cad);
            EditText TIBGE= findViewById(R.id.HIBGE_Cad);
            Spinner TUF= findViewById(R.id.HUF_Cad);
            EditText TCNPJ_prop= findViewById(R.id.HCNPJPROP_Cad);
            EditText TCNPJ_mant= findViewById(R.id.HCNPJMANT_Cad);
            EditText TLatitude= findViewById(R.id.HLatitude_Cad);
            EditText TLongitude= findViewById(R.id.HLongitude_Cad);

            String nome=TNome.getText().toString();
            String cidade= TCidade.getText().toString();
            String Cep=TCep.getText().toString();
            String Logradouro=TLogradouro.getText().toString();
            String Bairro= TBairro.getText().toString();
            String tipo =Ttipo.getText().toString();
            String numero = Tnumero.getText().toString();
            String razao=Trazao.getText().toString();
            String cnes =Tcnes.getText().toString();
            String ibge=TIBGE.getText().toString();
            String uf=TUF.getSelectedItem().toString();
            String cnpjp=  TCNPJ_prop.getText().toString();
            String cnpjm=TCNPJ_mant.getText().toString();
            String latitude= TLatitude.getText().toString();
            String longitude=  TLongitude.getText().toString();

            if(TLatitude.getText().toString().trim().equals("")){
                alert("Preencha o Campo Latitude");}
            else if(TLongitude.getText().toString().trim().equals("")){
                alert("Preencha o Campo Longitude");
            }
            else {
                Hospital h2 = new Hospital();
                h2.NOME_FANTASIA = nome;
                h2.MUNICIPIO = cidade;
                h2.CEP = Cep;
                h2.LOGRADOURO = Logradouro;
                h2.BAIRRO = Bairro;
                h2.TIPO_GESTAO = tipo;
                h2.NUMERO = numero;
                h2.RAZAO_SOCIAL = razao;
                h2.CNES = cnes;
                h2.IBGE = ibge;
                h2.UF = uf;
                h2.CNPJ_PROPRIO = cnpjp;
                h2.CNPJ_MANTENEDORA = cnpjm;
                h2.LATITUDE = latitude;
                h2.LONGITUDE = longitude;
                mDatabase.child("Hospitais").child(h2.NOME_FANTASIA).setValue(h2);

                alert("Hospital cadastrado com sucesso");
                Bundle args = new Bundle();
                args.putString("usuario", TNome.getText().toString());
                args.putString("logradouro", TLogradouro.getText().toString());
                args.putString("bairro", TBairro.getText().toString());
                args.putString("cidade", TCidade.getText().toString());
                args.putString("UF", TUF.getSelectedItem().toString());
                args.putString("tipo", Ttipo.getText().toString());
                args.putString("CEP", TCep.getText().toString());
                args.putString("CNPJM", TCNPJ_mant.getText().toString());
                args.putString("CNPJP", TCNPJ_prop.getText().toString());
                args.putString("CNES", Tcnes.getText().toString());
                args.putString("razao", Trazao.getText().toString());
                args.putString("numero", Tnumero.getText().toString());
                args.putString("IBGE", TIBGE.getText().toString());
                InfoHospital infoHospital = new InfoHospital();
                infoHospital.setArguments(args);

                MostrarFrag(infoHospital, "fragment");
            }
    }

    public void AtivarCampos(){
        navigationView.getMenu().findItem(R.id.Perfil).setEnabled(true);
        navigationView.getMenu().findItem(R.id.Dependentes).setEnabled(true);
    }
    public void ChamarTelaCadastrar(View v){
        MostrarFrag(c,"fragment");
    }

    public void MostrarFrag(Fragment fragmento, String name){
        FragTela = getSupportFragmentManager();
        transaction = FragTela.beginTransaction();
        transaction.replace (R.id.containerP,fragmento,name);
        transaction.addToBackStack("pilha");
        transaction.commit();
    }
    public void alert(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }

    public void ChamarTelaLogin(View v){
        if(logado==false) {
            MostrarFrag(tl, "fragment");
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id)
        {
            case  R.id.Perfil:
                p.d=uaux;
                MostrarFrag(p,"MapsFragment");
                break;
            case R.id.Mapa:
                mapa2= new Mapa2();
                if(dadosMapa.checked==true){
                    mapa2.currentLocationLatLong=dadosMapa.latLng;
                    mapa2.distancia2=dadosMapa.Valor;
                    mapa2.checked=dadosMapa.checked;
                }
                if(mapa2.currentLocationLatLong!=null){
                    latLng=mapa2.currentLocationLatLong;}
                MostrarFrag(mapa2,"MapsFragment");
                break;
            case R.id.Historico:
                MostrarFrag(dadosMapa,"MapsFragment");
                dadosMapa.m = new Mapa();
                dadosMapa.MostrarMapa(dadosMapa.m,"fragment");
                break;
            case R.id.Dependentes:
                Dependentes d=new Dependentes();
                d.u=uaux;
                MostrarFrag(d,"MapsFragment");
                break;
            case R.id.Cad_Medico:
                MostrarFrag(new CadastrarMedico(),"MapsFragment");
                break;
            case R.id.Cd_Hospital:
                MostrarFrag(new CadastrarHospital(),"fragment");
                break;
            case R.id.Atualizar_Banco:

                MostrarFrag(new AtualizarBanco(),"fragment");
            break;
            case R.id.PesquisaEsp:
                PesquisarHospADM p=new PesquisarHospADM();
                if(mapa2.currentLocationLatLong!=null) {
                    p.latLng = mapa2.currentLocationLatLong;
                }
                MostrarFrag(p,"fragment");
                break;
            case R.id.Peq_Medico:
                if(auxMed==0) {
                    pesquisarMedico.CidadesPernambuco = CidadesPernambuco;
                    auxMed++;
                }
                MostrarFrag(pesquisarMedico,"fragment");
                break;
            case R.id.Pesq_Hospital:
                if(auxHosp==0) {
                    pesquisarHospital.CidadesPernambuco = CidadesPernambuco;
                    auxHosp++;
                }
                pesquisarHospital.hospitalCidade.latLng=mapa2.currentLocationLatLong;
                MostrarFrag(pesquisarHospital,"fragmente");
                break;
    }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void pegarImagem(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGEM_INTERNA);
    }
/*public void pesquisarDependente(){
    Query query = mDatabase.child("Usuario");
    query.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Usuario u2;
            for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                u2 = Snap.getValue(Usuario.class);
                if (u2.getCpf().equals(d.pesq.getText().toString())) {
                    Bundle args = new Bundle();
                    args.putString("nome",u2.getNome());
                    args.putString("email",u2.getEmail());
                    args.putString("telefone",u2.getTelefone());
                    args.putString("bairro",u2.getBairro());
                    args.putString("numero",u2.getNumero());
                    args.putString("uf",u2.getUf());
                    args.putString("cpf",u2.getCpf());
                    args.putString("rg",u2.getRg());
                    args.putString("endereço",u2.getEndereço());
                    args.putString("cidade",u2.getCidade());
                    Perfil p=new Perfil();
                    p.setArguments(args);
                    d.MostrarFrag(p,"fragment");
                } else {
                    alert("Não encontrado");
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}    */@Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent){
        if(requestCode == IMAGEM_INTERNA){
            if(responseCode == RESULT_OK){

                Uri imagemSelecionada = intent.getData();
                filePath = imagemSelecionada;
                imagemView = (ImageView)findViewById(R.id.Cadastro_ImgPerfil);
                Bitmap imagem = null;
                try {
                    imagem = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                    imagemView.setImageBitmap(imagem);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void LerXML(View v){
        alert("tamanho: "+HospitaisList.size());
        InputStream is=getResources().openRawResource(R.raw.medicos3);
        BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String linha="";
        try {
            while (((linha=bufferedReader.readLine())!=null)){
                String[] token = linha.split(";");

                Hospital Hospital = new Hospital();
                Hospital.IBGE=token[0];
                Hospital.UF=token[1];
                Hospital.MUNICIPIO=token[2];
                Hospital.CNES=token[3];
                Hospital.NOME_FANTASIA=token[4];
                Hospital.RAZAO_SOCIAL=token[5];
                Hospital.CNPJ_PROPRIO=token[6];
                Hospital.CNPJ_MANTENEDORA=token[7];
                Hospital.TIPO_GESTAO=token[8];
                Hospital.LOGRADOURO=token[9];
                Hospital.NUMERO=token[10];
                Hospital.BAIRRO=token[11];
                Hospital.CEP=token[12];

                if(token.length>13){
                    Hospital.LATITUDE=token[13];
                    Hospital.LONGITUDE=token[14];
                }
                else{
                    Hospital.LATITUDE="0";
                    Hospital.LONGITUDE="0";
                }
                HospitaisList.add(Hospital);
            }
        } catch (IOException e) {
            Toast.makeText(this,"Erro ao ler a linha: "+linha,Toast.LENGTH_LONG);
            e.printStackTrace();
        }
        finally {
            for(int i=0;i<HospitaisList.size();i++) {
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("NOME_FANTASIA").setValue(HospitaisList.get(i).NOME_FANTASIA);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("LONGITUDE").setValue(HospitaisList.get(i).LONGITUDE);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("LATITUDE").setValue(HospitaisList.get(i).LATITUDE);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("TIPO_GESTAO").setValue(HospitaisList.get(i).TIPO_GESTAO);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("BAIRRO").setValue(HospitaisList.get(i).BAIRRO);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("MUNICIPIO").setValue(HospitaisList.get(i).MUNICIPIO);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("UF").setValue(HospitaisList.get(i).UF);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("LOGRADOURO").setValue(HospitaisList.get(i).LOGRADOURO);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("CNES").setValue(HospitaisList.get(i).CNES);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("IBGE").setValue(HospitaisList.get(i).IBGE);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("CNPJ_PROPRIO").setValue(HospitaisList.get(i).CNPJ_MANTENEDORA);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("CNPJ_MANTENEDORA").setValue(HospitaisList.get(i).CNPJ_MANTENEDORA);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("NUMERO").setValue(HospitaisList.get(i).NUMERO);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("CEP").setValue(HospitaisList.get(i).CEP);
                mDatabase.child("Hospitais").child(HospitaisList.get(i).NOME_FANTASIA).child("RAZAO_SOCIAL").setValue(HospitaisList.get(i).RAZAO_SOCIAL);
            }
        }
    }
    public void LerXML2(View v) {
        InputStream is = getResources().openRawResource(R.raw.medicos2);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String linha = "";
        try {
            while (((linha = bufferedReader.readLine()) != null)) {
                String[] token = linha.split(";");

                Medicos profissionais = new Medicos();
                profissionais.NOME = token[0];
                profissionais.CNS = token[1];
                profissionais.SEXO = token[2];
                profissionais.IBGE = token[3];
                profissionais.UF = token[4];
                profissionais.MUNICIPIO = token[5];
                profissionais.CBO = token[6];
                profissionais.DESCRICAO_CBO = token[7];
                profissionais.CNES = token[8];
                profissionais.CNPJ = token[9];
                profissionais.ESTABELECIMENTO = token[10];
                profissionais.NATUREZA_JURIDICA = token[11];
                profissionais.DESCRICAO_NATUREZA_JURIDICA = token[12];
                profissionais.GESTAO = token[13];
                profissionais.SUS = token[14];
                profissionais.RESIDENTE = token[15];
                profissionais.PRECEPTOR = token[16];
                profissionais.VINCULO_ESTABELECIMENTO = token[17];
                profissionais.VINCULO_EMPREGADOR = token[18];
                profissionais.DETALHAMENTO_DO_VINCULO = token[19];
                profissionais.CH_OUTROS = token[20];
                profissionais.CH_AMB = token[21];
                profissionais.CH_HOSP = token[21];
                ProfissionaisList.add(profissionais);
            }
            TextView t = findViewById(R.id.TesteSi);
            t.setText(""+ProfissionaisList.size());
        } catch (IOException e) {
            Toast.makeText(this, "Erro ao ler a linha: " + linha, Toast.LENGTH_LONG);
            e.printStackTrace();
        } finally {

               for (int i=0;i<ProfissionaisList.size();i++) {
                        mDatabase.child("Medicos").child(""+i).child("ID").setValue(""+i);
                        mDatabase.child("Medicos").child(""+i).child("NOME").setValue(ProfissionaisList.get(i).NOME);
                        mDatabase.child("Medicos").child(""+i).child("CNS").setValue(ProfissionaisList.get(i).CNS);
                        mDatabase.child("Medicos").child(""+i).child("IBGE").setValue(ProfissionaisList.get(i).IBGE);
                        mDatabase.child("Medicos").child(""+i).child("UF").setValue(ProfissionaisList.get(i).UF);
                        mDatabase.child("Medicos").child(""+i).child("MUNICIPIO").setValue(ProfissionaisList.get(i).MUNICIPIO);
                        mDatabase.child("Medicos").child(""+i).child("CBO").setValue(ProfissionaisList.get(i).CBO);
                        mDatabase.child("Medicos").child(""+i).child("DESCRICAO_CBO").setValue(ProfissionaisList.get(i).DESCRICAO_CBO);
                        mDatabase.child("Medicos").child(""+i).child("CNES").setValue(ProfissionaisList.get(i).CNES);
                        mDatabase.child("Medicos").child(""+i).child("CNPJ").setValue(ProfissionaisList.get(i).CNPJ);
                        mDatabase.child("Medicos").child(""+i).child("ESTABELECIMENTO").setValue(ProfissionaisList.get(i).ESTABELECIMENTO);
                        mDatabase.child("Medicos").child(""+i).child("NATUREZA_JURIDICA").setValue(ProfissionaisList.get(i).NATUREZA_JURIDICA);
                        mDatabase.child("Medicos").child(""+i).child("DESCRICAO_NATUREZA_JURIDICA").setValue(ProfissionaisList.get(i).DESCRICAO_NATUREZA_JURIDICA);
                        mDatabase.child("Medicos").child(""+i).child("GESTAO").setValue(ProfissionaisList.get(i).GESTAO);
                        mDatabase.child("Medicos").child(""+i).child("SUS").setValue(ProfissionaisList.get(i).SUS);
                        mDatabase.child("Medicos").child(""+i).child("RESIDENTE").setValue(ProfissionaisList.get(i).RESIDENTE);
                        mDatabase.child("Medicos").child(""+i).child("PRECEPTOR").setValue(ProfissionaisList.get(i).PRECEPTOR);
                        mDatabase.child("Medicos").child(""+i).child("VINCULO_ESTABELECIMENTO").setValue(ProfissionaisList.get(i).VINCULO_ESTABELECIMENTO);
                        mDatabase.child("Medicos").child(""+i).child("VINCULO_EMPREGADOR").setValue(ProfissionaisList.get(i).VINCULO_EMPREGADOR);
                        mDatabase.child("Medicos").child(""+i).child("DETALHAMENTO_DO_VINCULO").setValue(ProfissionaisList.get(i).DETALHAMENTO_DO_VINCULO);
                        mDatabase.child("Medicos").child(""+i).child("CH_OUTROS").setValue(ProfissionaisList.get(i).CH_OUTROS);
                        mDatabase.child("Medicos").child(""+i).child("CH_AMB").setValue(ProfissionaisList.get(i).CH_AMB);
                        mDatabase.child("Medicos").child(""+i).child("CH_HOSP").setValue(ProfissionaisList.get(i).CH_HOSP);
            }
        }
    }
    public void apagarTabela(View v){
        Query query = mDatabase.child("Hospitais");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                    i++;
                }
                TextView t= findViewById(R.id.testeSi);
                t.setText(""+i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void Rotacionar(View v){
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


}
