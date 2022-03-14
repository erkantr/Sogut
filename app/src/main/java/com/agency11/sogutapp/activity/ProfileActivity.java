package com.agency11.sogutapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.agency11.sogutapp.BottomDialog;
import com.agency11.sogutapp.R;
import com.agency11.sogutapp.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView kullanici_adi = findViewById(R.id.kullanici_adi);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        ImageView logout = findViewById(R.id.logout);
        ImageView langugageDialog = findViewById(R.id.more2);
        BottomDialog bottomDialog = new BottomDialog(this,null,null,null
        ,null,null,null);

        //linearLayout.setBackgroundResource(R.drawable.background1);
        DocumentReference reference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                kullanici_adi.setText(user.getName());
            }
        });

        langugageDialog.setOnClickListener(view -> {
            bottomDialog.languageDialog();
        });

        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ProfileActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });
    }
}