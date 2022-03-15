package com.agency11.sogutapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agency11.sogutapp.BottomDialog;
import com.agency11.sogutapp.R;
import com.agency11.sogutapp.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    Bitmap imageToStore;
    Bitmap imageBitmap;
    ImageView profile_image;
    SharedPreferences sharedPreferences;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView kullanici_adi = findViewById(R.id.kullanici_adi);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        String imageUriString = sharedPreferences.getString("profile_image", null);

        ImageView logout = findViewById(R.id.logout);
        ImageView langugageDialog = findViewById(R.id.more2);
        LinearLayout change_pass_layout = findViewById(R.id.change_pass_layout);
        LinearLayout change_lang_layout = findViewById(R.id.change_lang_layout);
        profile_image = findViewById(R.id.profile_image);
        auth = FirebaseAuth.getInstance();
        BottomDialog bottomDialog = new BottomDialog(this,null,auth,null
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

        /*
        if (imageUriString !=null){
            Uri imageUri = Uri.parse(imageUriString);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            profile_image.setImageResource(R.drawable.profile);
        }


         */



        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objectIntent = new Intent();
                objectIntent.setType("image/*");

                objectIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);
            }
        });

        change_pass_layout.setOnClickListener(view -> {
            bottomDialog.passwordChangeDialog();
        });

        change_lang_layout.setOnClickListener(view -> {
            bottomDialog.languageDialog();
        });

        /*
        langugageDialog.setOnClickListener(view -> {
            bottomDialog.languageDialog();
        });

         */

        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ProfileActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

                SaveImage(imageToStore);

                /*
                sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profile_image",imageFilePath.toString());
                editor.apply();

                 */

                profile_image.setImageBitmap(imageToStore);
                imageBitmap = ((BitmapDrawable) profile_image.getDrawable()).getBitmap();
            }
        } catch (Exception e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/images");
        myDir.mkdirs();
        String fname = "image"+".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}