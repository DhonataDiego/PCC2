package br.com.dhonatandiego.pcc;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Mapa2 extends SupportMapFragment implements OnMapReadyCallback, LocationListener {

    public GoogleMap mMap;
    public LatLng currentLocationLatLong;
    private DatabaseReference mDatabase;
    private ArrayList<Hospital> Hospitais = new ArrayList<>();
    ArrayList<Hospital> HospitaisGerais = new ArrayList<>();
    ArrayList<Hospital> AuxHospital;
    HospitalCidade hospitalCidade = new HospitalCidade();
    HospitalFragment IH;
    Marker marker2;
    FragmentTransaction transaction;
    boolean checked=false;
    Hospital h;
    String Permissão;
    int aux;
    public double distancia2=100000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(checked==false){
            startGettingLocations();
        }
        else{
            CarregarHospitais();
        }

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker2=marker;
                IH= new HospitalFragment();
                AuxHospital = new ArrayList<>();
                transaction= getFragmentManager().beginTransaction();
                final ArrayList<String>t2 = new ArrayList<>();
                Query q= mDatabase.child("Hospitais").child(marker.getTitle());
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                            String a=Snap.getValue(String.class);
                            t2.add(a);
                        }
                        final ArrayList<Hospital> h2= new ArrayList<>();
                        Query q2= mDatabase.child("Hospitais").orderByChild("MUNICIPIO").equalTo(t2.get(9));
                        q2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                                    h2.add(Snap.getValue(Hospital.class));
                                }
                                ArrayList<Hospital>AuxHospital=new ArrayList<>();
                                for(int i=0;i<h2.size();i++){
                                    if(t2.get(6).equals(h2.get(i).LATITUDE) && t2.get(8).equals(h2.get(i).LONGITUDE)){
                                        AuxHospital.add(h2.get(i));
                                    }
                                }
                                try {
                                    if (AuxHospital.size() == 1) {
                                        IH.h = AuxHospital.get(0);
                                        transaction.addToBackStack("pilha");
                                        transaction.replace(R.id.containerP, IH, "Fragment");
                                        transaction.commit();
                                    } else {
                                        hospitalCidade.Hospitais = AuxHospital;
                                        hospitalCidade.latLng = marker2.getPosition();
                                        transaction.addToBackStack("pilha");
                                        transaction.replace(R.id.containerP, hospitalCidade, "Fragment");
                                        transaction.commit();
                                    }
                                }catch (Exception e){}
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                /*final ArrayList<Medicos> Teste = new ArrayList<>();
                Query query = mDatabase.child("MedicosT").child(t2.get(9));
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                            Teste.add(Snap.getValue(Medicos.class));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                for(int i=0;i<Hospitais.size();i++){
                    if(Hospitais.get(i).NOME_FANTASIA.equals(marker.getTitle())){
                        h = Hospitais.get(i);}
                }
                for(int i=0;i<Hospitais.size();i++){
                        if(Hospitais.get(i).LATITUDE.equals(h.LATITUDE) && Hospitais.get(i).LONGITUDE.equals(h.LONGITUDE)){
                            AuxHospital.add(Hospitais.get(i));
                        }
                }*/
                /*for(int i=0;i<h2.size();i++){
                    if(t2.get(6).equals(h2.get(i).LATITUDE) && t2.get(8).equals(h.LONGITUDE)){
                        AuxHospital.add(Hospitais.get(i));
                    }
                }*/


            }
        });

    }
   /* public void CarregarHospitaisGerais(){
        Query query = mDatabase.child("Hospitais");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                    HospitaisGerais.add(Snap.getValue(Hospital.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
    @Override
    public void onLocationChanged(Location location) {

       try {
           currentLocationLatLong = new LatLng(location.getLatitude(), location.getLongitude());
           if(aux==0) {
               CarregarHospitais();
               aux++;
           }
       }
       catch (Exception e){
           if(getView()!=null)
                getView().invalidate();
       }
   }

    public void CarregarHospitais(){
        Query query = mDatabase.child("Hospitais");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                    HospitaisGerais.add(Snap.getValue(Hospital.class));
                }
                CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(currentLocationLatLong).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mMap.addCircle(new CircleOptions()
                        .center(currentLocationLatLong)
                        .radius(distancia2)
                        .strokeColor(Color.RED)
                        .fillColor(Color.argb(15,118,17,22)));

                for(int i=0;i<HospitaisGerais.size();i++) {
                    Hospital p = HospitaisGerais.get(i);
                    try{
                    final double distancia = 6371 *
                            Math.acos(
                                    Math.cos(Math.toRadians(currentLocationLatLong.latitude)) *
                                            Math.cos(Math.toRadians(Double.parseDouble(p.LATITUDE))) *
                                            Math.cos(Math.toRadians(currentLocationLatLong.longitude) - Math.toRadians(Double.parseDouble(p.LONGITUDE))) +
                                            Math.sin(Math.toRadians(currentLocationLatLong.latitude)) *
                                                    Math.sin(Math.toRadians(Double.parseDouble(p.LATITUDE)))
                            );

                    if (Double.parseDouble(p.LATITUDE) != 0 && Double.parseDouble(p.LONGITUDE) != 0 && distancia < distancia2 / 1000) {
                        Hospitais.add(p);
                        adicionarMarcadores(p);
                    }}catch (Exception e){}
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void adicionarMarcadores(Hospital H){

        LatLng latLng = new LatLng(Double.parseDouble(H.LATITUDE),Double.parseDouble(H.LONGITUDE));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(""+H.NOME_FANTASIA);
        markerOptions .icon(BitmapDescriptorFactory.fromResource(R.mipmap.medical));
        mMap.addMarker(markerOptions);

    }


    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("GPS desativado!");
        alertDialog.setMessage("Ativar GPS?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    private void startGettingLocations() {

        LocationManager lm = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);

        boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean canGetLocation = true;
        int ALL_PERMISSIONS_RESULT = 101;
        long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;// Distance in meters
        long MIN_TIME_BW_UPDATES = 1000 * 10;// Time in milliseconds

        ArrayList<String> permissions = new ArrayList<>();
        ArrayList<String> permissionsToRequest;

        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        //Check if GPS and Network are on, if not asks the user to turn on
        if (!isGPS && !isNetwork) {
            showSettingsAlert();
        } else {
            // check permissions

            // check permissions for later versions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    canGetLocation = false;
                }
            }
        }


        //Checks if FINE LOCATION and COARSE Location were granted
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(getContext(), "Permissão negada", Toast.LENGTH_SHORT).show();
            return;
        }

        //Starts requesting location updates
        if (canGetLocation) {
            if (isGPS) {
                lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            } else if (isNetwork) {
                // from Network Provider

                lm.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            }
        } else {
            Toast.makeText(getContext(), "Não é possível obter a localização", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


