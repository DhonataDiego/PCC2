package br.com.dhonatandiego.pcc;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConexaoActivity {
    private static FirebaseAuth mAuth;
    private static  FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser fbUser;

    private ConexaoActivity(){}
public static FirebaseAuth getFirebaseAuth(){
        if(mAuth ==null) {
            inicializarFirebase();
        }
        return mAuth;
}
  public static void inicializarFirebase(){
       mAuth  = FirebaseAuth.getInstance();
        authStateListener  = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    fbUser =user;
                }
            }
        };
        mAuth.addAuthStateListener(authStateListener);
  }
  public static FirebaseUser getfirebaseUser (){
        return fbUser;
    }
 public void logout(){
        mAuth.signOut();
 }
}
