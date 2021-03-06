package com.agency11.sogutapp.method;

import static android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.agency11.sogutapp.R;
import com.agency11.sogutapp.activity.MainActivity;
import com.agency11.sogutapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BottomDialog {

    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    Activity activity;
    TextView kullanici_adi;
    ImageView profile_image;
    LinearLayout linearLayout;
    Dialog dialog;

    public BottomDialog(Activity activity, FirebaseUser firebaseUser, FirebaseAuth auth, FirebaseFirestore firebaseFirestore,
                        TextView kullanici_adi, ImageView profile_image, LinearLayout linearLayout) {
        this.firebaseUser = firebaseUser;
        this.auth = auth;
        this.firebaseFirestore = firebaseFirestore;
        this.activity = activity;
        this.kullanici_adi = kullanici_adi;
        this.profile_image = profile_image;
        this.linearLayout = linearLayout;
        SharedPreferences sharedPreferences2 = activity.getSharedPreferences("lang1", Context.MODE_PRIVATE);
        String targetLanguage = sharedPreferences2.getString("language","");
        LocaleHelper.setLocale(activity,targetLanguage);
    }


    public void loginDialog(int page) {
        //View view = activity.getLayoutInflater().inflate(R.layout.login_dialog, null, false);
        //dialog = new BottomSheetDialog(activity, R.style.BottomSheetDialog);
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.login_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);
        Size size = new Size(activity);

        RelativeLayout login_layout = window.findViewById(R.id.login_layout);
        TextView devam_etmek_icin = window.findViewById(R.id.devam_etmek_icin);
        ImageView image_close_square = window.findViewById(R.id.image_close_square);
        TextView text_girisyap = window.findViewById(R.id.text_girisyap);
        LinearLayout linear1 = window.findViewById(R.id.linear1);
        TextInputLayout text_input_layout = window.findViewById(R.id.text_input_layout);
        TextInputEditText mail = window.findViewById(R.id.mail);
        TextInputLayout text_input_layout1 = window.findViewById(R.id.text_input_layout1);
        TextInputEditText pass = window.findViewById(R.id.password);
        RelativeLayout giris = window.findViewById(R.id.login_button);
        TextView login_button_text = window.findViewById(R.id.login_button_text);
        ImageView login_button_image = window.findViewById(R.id.login_button_image);
        LinearLayout linear2 = window.findViewById(R.id.linear2);
        View view_login = window.findViewById(R.id.view_login);
        TextView text_yada = window.findViewById(R.id.text_yada);
        View view_login1 = window.findViewById(R.id.view_login1);
        LinearLayout linear3 = window.findViewById(R.id.linear3);
        ImageView login_button_image1 = window.findViewById(R.id.login_button_image1);
        ImageView login_button_image2 = window.findViewById(R.id.login_button_image2);
        LinearLayout linear4 = window.findViewById(R.id.linear4);
        TextView text_hesap = window.findViewById(R.id.text_hesap);
        TextView kayit_ol = window.findViewById(R.id.register_text);
        TextView forgot_password = window.findViewById(R.id.forgot_password);

        size.setWidth(devam_etmek_icin, 190);
        size.setMargin(devam_etmek_icin, 17, 42, 0, 0);
        size.setSize(devam_etmek_icin, 10);
        size.setMargin(image_close_square, 0, 24, 24, 0);
        size.setMargin(text_girisyap, 17, 0, 0, 0);
        size.setSize(text_girisyap, 33);
        size.setMargin(linear1, 0, -20, 0, 0);
        size.setPadding(linear1, 16, 16, 16, 16);
        size.setMargin(text_input_layout, 0, 20, 0, 0);
        size.setSize(mail, 14);
        size.setMargin(text_input_layout1, 0, 20, 0, 0);
        size.setSize(pass, 14);
        size.setMargin(giris, 16, 42, 16, 0);
        size.setSize(login_button_text, 13);
        size.setWidth(login_button_image, 18);
        size.setHeight(login_button_image, 18);
        size.setMargin(login_button_image, 8, 0, 0, 0);
        size.setMargin(linear2, 0, 50, 0, 0);
        size.setWidth(view_login, 0);
        size.setHeight(view_login, 1);
        size.setSize(text_yada, 10);
        size.setWidth(view_login1, 0);
        size.setHeight(view_login1, 1);
        size.setMargin(linear3, 0, 50, 0, 0);
        size.setMargin(login_button_image1, 54, 0, 0, 0);
        size.setMargin(login_button_image2, 54, 0, 0, 0);
        size.setMargin(linear4, 0, 50, 0, 0);
        size.setSize(text_hesap, 12);
        size.setMargin(kayit_ol, 8, 0, 0, 0);
        size.setSize(kayit_ol, 12);
        size.setMargin(forgot_password, 64, 0, 0, 0);
        size.setSize(forgot_password, 12);
        size.setHeight(login_layout,584);

        image_close_square.setOnClickListener(view -> {
            dialog.cancel();
        });

        forgot_password.setOnClickListener(view1 -> {
            dialog.cancel();
            resetPasswordDialog();
        });

        kayit_ol.setOnClickListener(view1 -> {
            dialog.cancel();
            registerDialog();
        });

        giris.setOnClickListener(view1 -> {
            String txt_email = mail.getText().toString();
            String txt_password = pass.getText().toString();
            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                Toast.makeText(activity, "T??m alanlar zorunludur", Toast.LENGTH_SHORT).show();
            } else {

                auth.signInWithEmailAndPassword(txt_email, txt_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                    //if (page == 1) {
                                        activity.startActivity(new Intent(activity, MainActivity.class));
                                        activity.overridePendingTransition(0, 0);
                                        activity.finish();
                                   // }

                                    if (page == 2){
                                        SharedPreferences sharedPreferences = activity.getSharedPreferences("profile", Context.MODE_PRIVATE);
                                        String imageUriString = sharedPreferences.getString("profile_image", null);
                                        if (imageUriString !=null){
                                            Uri imageUri = Uri.parse(imageUriString);
                                            try {
                                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUri);
                                                profile_image.setImageBitmap(bitmap);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            profile_image.setImageResource(R.drawable.profile);
                                        }
                                        kullanici_adi.setVisibility(View.VISIBLE);
                                        //linearLayout.setBackgroundResource(R.drawable.background1);
                                    }

                                    dialog.cancel();

                                } else {
                                    Toast.makeText(activity, "Kimlik do??rulama ba??ar??s??z!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    DocumentReference reference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
                    reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            User user = documentSnapshot.toObject(User.class);

                            if (page == 2) {
                                kullanici_adi.setText("Merhaba,\n" + user.getName());
                            }
                            Toast.makeText(activity, "Giri?? yap??ld??", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        //dialog.setContentView(window);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
        dialog.getWindow().clearFlags(FLAG_DIM_BEHIND);
    }

    public void resetPasswordDialog() {
       // View view = activity.getLayoutInflater().inflate(R.layout.password_reset_dialog, null, false);
       // dialog = new BottomSheetDialog(activity, R.style.BottomSheetDialog);
        Size size = new Size(activity);
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.password_reset_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);


        RelativeLayout reset_layout = window.findViewById(R.id.reset_layout);
        LinearLayout linear_remember = window.findViewById(R.id.linear_remember);
        TextView text_remember = window.findViewById(R.id.text_remember);
        ImageView image_remember = window.findViewById(R.id.image_remember);
        TextView text_remember2 = window.findViewById(R.id.text_remember2);
        LinearLayout linear_remember1 = window.findViewById(R.id.linear_remember1);
        TextInputLayout textinput_remember = window.findViewById(R.id.textinput_remember);
        TextInputEditText mail = window.findViewById(R.id.mail);
        RelativeLayout send = window.findViewById(R.id.send_button);
        TextView send_button_text = window.findViewById(R.id.send_button_text);
        ImageView send_button_image = window.findViewById(R.id.send_button_image);
        LinearLayout linear_remember2 = window.findViewById(R.id.linear_remember2);
        TextView text_remember3 = window.findViewById(R.id.text_remember3);
        TextView login_text_remember = window.findViewById(R.id.login_text_remember);

        size.setWidth(linear_remember, 190);
        size.setWidth(text_remember, 190);
        size.setMargin(text_remember, 17, 42, 0, 0);
        size.setSize(text_remember, 10);
        size.setMargin(image_remember, 0, 24, 24, 0);
        size.setMargin(text_remember2, 17, 0, 0, 0);
        size.setSize(text_remember2, 33);
        size.setPadding(linear_remember1, 16, 16, 16, 16);
        size.setMargin(linear_remember1, 0, -20, 0, 0);
        size.setMargin(textinput_remember, 0, 20, 0, 0);
        size.setSize(mail, 14);
        size.setMargin(send, 16, 42, 16, 0);
        size.setSize(send_button_text, 13);
        size.setWidth(send_button_image, 18);
        size.setHeight(send_button_image, 18);
        size.setMargin(send_button_image, 8, 0, 0, 0);
        size.setMargin(linear_remember2, 16, 50, 0, 0);
        size.setSize(text_remember3, 12);
        size.setMargin(login_text_remember, 8, 0, 0, 0);
        size.setSize(login_text_remember, 12);
        size.setHeight(reset_layout,641);

        login_text_remember.setOnClickListener(view1 -> {
            dialog.cancel();
            loginDialog(0);
        });

        image_remember.setOnClickListener(view1 -> {
            dialog.cancel();
        });

        send.setOnClickListener(view1 -> {
            String txt_email = mail.getText().toString();

            if (TextUtils.isEmpty(txt_email)) {
                Toast.makeText(activity, "T??m alanlar zorunludur", Toast.LENGTH_SHORT).show();
            } else {
                auth.sendPasswordResetEmail(txt_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "S??f??rlama ba??lant??s?? mail adresinize g??nderildi", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            loginDialog(0);
                        } else {
                            Toast.makeText(activity, "S??f??rlama ba??lant??s?? g??nderilemedi, l??tfen mail adresinizi do??ru girin", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
       // dialog.setContentView(window);
        dialog.show();
        dialog.getWindow().clearFlags(FLAG_DIM_BEHIND);
    }

    public void passwordChangeDialog() {
        View view = activity.getLayoutInflater().inflate(R.layout.password_change_dialog, null, false);
        dialog = new BottomSheetDialog(activity, R.style.BottomSheetDialog);
        Size size = new Size(activity);

        LinearLayout linear_bottom_change = view.findViewById(R.id.linear_bottom_change);
        TextView text_devam_change = view.findViewById(R.id.text_devam_change);
        ImageView image_bottom_change = view.findViewById(R.id.image_bottom_change);
        TextView text_sifre_degistir = view.findViewById(R.id.text_sifre_degistir);
        LinearLayout linear_bottom_change1 = view.findViewById(R.id.linear_bottom_change1);
        TextInputLayout text_input_change = view.findViewById(R.id.text_input_change);
        TextInputEditText mail = view.findViewById(R.id.mail);
        TextInputLayout text_input_change1 = view.findViewById(R.id.text_input_change1);
        TextInputEditText password3 = view.findViewById(R.id.password3);
        TextInputLayout text_input_change2 = view.findViewById(R.id.text_input_change2);
        TextInputEditText password = view.findViewById(R.id.password);
        TextInputLayout text_input_change3 = view.findViewById(R.id.text_input_change3);
        TextInputEditText password2 = view.findViewById(R.id.password2);
        RelativeLayout send = view.findViewById(R.id.send_button);
        TextView send_button_text = view.findViewById(R.id.send_button_text);
        ImageView send_button_image = view.findViewById(R.id.send_button_image);
        LinearLayout linear_bottom_change2 = view.findViewById(R.id.linear_bottom_change2);
        TextView text_hesap_varmi = view.findViewById(R.id.text_hesap_varmi);
        TextView login_text = view.findViewById(R.id.login_text);

        size.setWidth(linear_bottom_change, 190);
        size.setWidth(text_devam_change, 190);
        size.setMargin(text_devam_change, 17, 42, 0, 0);
        size.setSize(text_devam_change, 10);
        size.setMargin(image_bottom_change, 0, 24, 24, 0);
        size.setSize(text_sifre_degistir, 33);
        size.setMargin(text_sifre_degistir, 17, 0, 0, 0);
        size.setPadding(linear_bottom_change1, 16, 16, 16, 16);
        size.setMargin(linear_bottom_change1, 0, -20, 0, 0);
        size.setMargin(text_input_change, 0, 20, 0, 0);
        size.setSize(mail, 14);
        size.setMargin(text_input_change1, 0, 20, 0, 0);
        size.setSize(password3, 14);
        size.setMargin(text_input_change2, 0, 20, 0, 0);
        size.setSize(password, 14);
        size.setMargin(text_input_change3, 0, 20, 0, 0);
        size.setSize(password2, 14);
        size.setMargin(send, 16, 42, 16, 0);
        size.setSize(send_button_text, 13);
        size.setWidth(send_button_image, 18);
        size.setHeight(send_button_image, 18);
        size.setMargin(send_button_image, 8, 0, 0, 0);
        size.setMargin(linear_bottom_change2, 16, 50, 0, 0);
        size.setSize(text_hesap_varmi, 12);
        size.setMargin(login_text, 8, 0, 0, 0);
        size.setSize(login_text, 12);

        image_bottom_change.setOnClickListener(view1 -> {
            dialog.cancel();
        });

        send.setOnClickListener(view1 -> {
            String mail_text = mail.getText().toString();
            String password_text = password.getText().toString();
            String password2_text = password2.getText().toString();
            String password3_text = password3.getText().toString();

            if (TextUtils.isEmpty(password3_text) || TextUtils.isEmpty(mail_text) || TextUtils.isEmpty(password_text) || TextUtils.isEmpty(password_text)) {
                Toast.makeText(activity, "T??m alanlar zorunludur", Toast.LENGTH_SHORT).show();

            } else {
                if (password_text.equals(password2_text)) {
                    String newPass = password_text;
                    if (firebaseUser != null) {
                        DocumentReference reference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
                        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                User user = documentSnapshot.toObject(User.class);
                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(user.getEmail(), user.getPassword());

                                String finalNewPassword = newPass;

                                firebaseUser.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    System.out.println("Kullan??c?? yeniden giri?? yapt??");
                                                    if (!finalNewPassword.equals("") && finalNewPassword.length() >= 6) {
                                                        firebaseUser.updatePassword(finalNewPassword)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            System.out.println("??ifre yenileme ba??ar??l??");
                                                                            successfulDialog();
                                                                            dialog.cancel();
                                                                        } else {
                                                                            Toast.makeText(activity, "Bu ??ifre ile kay??t olamazs??n??z", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    } else {
                                                        Toast.makeText(activity, "??ifre en az 6 karakterden olu??mal??d??r", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(activity, "L??tfen ??ifrenizi do??ru girin", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
                    } else {
                        auth.signInWithEmailAndPassword(mail_text, password3_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    System.out.println("Kullan??c?? yeniden giri?? yapt??");
                                    if (!newPass.equals("") && newPass.length() >= 6) {
                                        firebaseUser.updatePassword(newPass)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            System.out.println("??ifre yenileme ba??ar??l??");
                                                            dialog.cancel();
                                                            successfulDialog();
                                                        } else {
                                                            Toast.makeText(activity, "Bu ??ifre ile kay??t olamazs??n??z", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(activity, "??ifre en az 6 karakterden olu??mal??d??r", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(activity, "L??tfen ??ifrenizi do??ru girin", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(activity, "??ifreler birbirleri ile uyu??muyor!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setContentView(view);
        dialog.show();
        dialog.getWindow().clearFlags(FLAG_DIM_BEHIND);
    }

    public void successfulDialog() {
        //View view = activity.getLayoutInflater().inflate(R.layout.successful_dialog, null, false);
       // dialog = new BottomSheetDialog(activity, R.style.BottomSheetDialog);
        Size size = new Size(activity);
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.successful_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);

        ImageView image_success = window.findViewById(R.id.image_success);
        TextView text_success = window.findViewById(R.id.text_success);
        ImageView image_success2 = window.findViewById(R.id.image_success2);
        RelativeLayout login = window.findViewById(R.id.login_button);
        TextView login_button_text = window.findViewById(R.id.login_button_text);
        ImageView login_button_image = window.findViewById(R.id.login_button_image);
        RelativeLayout successful_layout = window.findViewById(R.id.successful_layout);

        size.setMargin(image_success, 0, 24, 24, 0);
        size.setMargin(text_success, 0, 24, 0, 0);
        size.setSize(text_success, 23);
        size.setMargin(image_success2, 0, 24, 0, 0);
        size.setMargin(login, 16, 86, 16, 0);
        size.setSize(login_button_text, 13);
        size.setWidth(login_button_image, 18);
        size.setHeight(login_button_image, 18);
        size.setMargin(login_button_image, 8, 0, 0, 0);
        size.setHeight(successful_layout,584);

        image_success.setOnClickListener(view1 -> {
            dialog.cancel();
        });

        login.setOnClickListener(view1 -> {
            loginDialog(0);
            dialog.dismiss();
        });

        //dialog.setContentView(window);
        dialog.show();
        dialog.getWindow().clearFlags(FLAG_DIM_BEHIND);
    }

    public void registerDialog() {
        //View view = activity.getLayoutInflater().inflate(R.layout.register_dialog, null, false);
        //Dialog dialog = new BottomSheetDialog(activity, R.style.BottomSheetDialog);
        Size size = new Size(activity);
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.register_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);

        TextInputEditText mail = window.findViewById(R.id.mail);
        TextInputEditText kullanici_adi1 = window.findViewById(R.id.kullanici_adi_edit);
        TextInputEditText pass = window.findViewById(R.id.password);
        RelativeLayout register = window.findViewById(R.id.register_button);
        RelativeLayout register_layout = window.findViewById(R.id.register_layout);

        LinearLayout linear_register = window.findViewById(R.id.linear_register);
        TextView text_register = window.findViewById(R.id.text_register);
        ImageView image_register = window.findViewById(R.id.image_register);
        TextView text_register1 = window.findViewById(R.id.text_register1);
        LinearLayout linear_register1 = window.findViewById(R.id.linear_register1);
        TextInputLayout textinput_register = window.findViewById(R.id.textinput_register);
        TextInputLayout textinput_register1 = window.findViewById(R.id.textinput_register1);
        TextInputLayout textinput_register2 = window.findViewById(R.id.textinput_register2);
        TextView register_button_text = window.findViewById(R.id.register_button_text);
        ImageView register_button_image = window.findViewById(R.id.register_button_image);
        LinearLayout linear_register2 = window.findViewById(R.id.linear_register2);
        View view_register = window.findViewById(R.id.view_register);
        TextView text_register2 = window.findViewById(R.id.text_register2);
        View view_register1 = window.findViewById(R.id.view_register1);
        LinearLayout linear_register3 = window.findViewById(R.id.linear_register3);
        ImageView image_register_google = window.findViewById(R.id.image_register_google);
        ImageView image_register_linkedin = window.findViewById(R.id.image_register_linkedin);
        LinearLayout linear_register4 = window.findViewById(R.id.linear_register4);
        TextView text_register3 = window.findViewById(R.id.text_register3);
        TextView text_register4 = window.findViewById(R.id.text_register4);

        size.setWidth(linear_register,190);
        size.setWidth(text_register,190);
        size.setMargin(text_register,17,42,0,0);
        size.setSize(text_register,10);
        size.setMargin(image_register,0,24,24,0);
        size.setMargin(text_register1,17,0,0,0);
        size.setSize(text_register1,33);
        size.setPadding(linear_register1,16,16,16,16);
        size.setMargin(linear_register1,0,-20,0,0);
        size.setMargin(textinput_register,0,20,0,0);
        size.setSize(kullanici_adi1,14);
        size.setMargin(textinput_register1,0,20,0,0);
        size.setSize(mail,14);
        size.setMargin(textinput_register2,0,20,0,0);
        size.setSize(pass,14);
        size.setMargin(register,16,42,16,0);
        size.setSize(register_button_text,13);
        size.setWidth(register_button_image,18);
        size.setHeight(register_button_image,18);
        size.setMargin(register_button_image,8,0,0,0);
        size.setMargin(linear_register2,0,50,0,0);
        size.setWidth(view_register,0);
        size.setHeight(view_register,1);
        size.setSize(text_register2,10);
        size.setWidth(view_register1,0);
        size.setHeight(view_register1,1);
        size.setMargin(linear_register3,0,50,0,0);
        size.setMargin(image_register_google,54,0,0,0);
        size.setMargin(image_register_linkedin,54,0,0,0);
        size.setMargin(linear_register4,16,50,0,0);
        size.setSize(text_register3,12);
        size.setMargin(text_register4,8,0,0,0);
        size.setSize(text_register4,12);
        size.setHeight(register_layout,641);

        text_register4.setOnClickListener(view1 -> {
            dialog.cancel();
            loginDialog(0);
        });

        image_register.setOnClickListener(view1 -> {
            dialog.cancel();
        });

        register.setOnClickListener(view1 -> {
            String txt_email = mail.getText().toString();
            String txt_password = pass.getText().toString();
            String txt_kullanici = kullanici_adi1.getText().toString();
            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)
                    || TextUtils.isEmpty(txt_kullanici)) {
                Toast.makeText(activity, "T??m alanlar zorunludur", Toast.LENGTH_SHORT).show();
            } else if (!txt_email.endsWith("@gmail.com")) {
                Toast.makeText(activity, "L??tfen ge??erli bir e-posta adresi girin", Toast.LENGTH_SHORT).show();
            } else if (txt_password.length() < 6) {
                Toast.makeText(activity, "??ifre en az 6 karakterden olu??mal??d??r", Toast.LENGTH_SHORT).show();
            } else {
                register(txt_email, txt_kullanici, txt_password,dialog);
                //Toast.makeText(activity, "Hesab??n??z Olu??turuldu", Toast.LENGTH_SHORT).show();
               // dialog.cancel();
            }
        });

        //dialog.setContentView(view);
        dialog.show();
        dialog.getWindow().clearFlags(FLAG_DIM_BEHIND);
    }

    public void languageDialog() {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.language_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);

        Size size = new Size(activity);

        TextView dil_sec = window.findViewById(R.id.dil_sec);
        ImageView close = window.findViewById(R.id.close);
        LinearLayout linear_activity = window.findViewById(R.id.linear_activity);
        View view_activity = window.findViewById(R.id.view_activity);
        LinearLayout linear_activity2 = window.findViewById(R.id.linear_activity2);
        ImageView tr = window.findViewById(R.id.tr);
        ImageView uk = window.findViewById(R.id.uk);
        ImageView ua = window.findViewById(R.id.ua);
        LinearLayout linear_activity3 = window.findViewById(R.id.linear_activity3);
        ImageView ar = window.findViewById(R.id.ar);
        ImageView pak = window.findViewById(R.id.pak);
        ImageView ru = window.findViewById(R.id.ru);
        CardView main_card = window.findViewById(R.id.main_card);

        SharedPreferences sharedPreferences = activity.getSharedPreferences("lang1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        tr.setOnClickListener(view -> {
            LocaleHelper.setLocale(activity,"tr");
            editor.putString("language", "tr");
            editor.apply();
            activity.startActivity(new Intent(activity,MainActivity.class));
        });
        uk.setOnClickListener(view -> {
            LocaleHelper.setLocale(activity,"en");
            editor.putString("language", "en");
            editor.apply();
            activity.startActivity(new Intent(activity,MainActivity.class));
        });

        ru.setOnClickListener(view -> {
            LocaleHelper.setLocale(activity,"ru");
            editor.putString("language", "ru");
            editor.apply();
            activity.startActivity(new Intent(activity,MainActivity.class));
        });

        /*
        size.setWidth(dil_sec,47);
        size.setHeight(dil_sec,21);
        size.setMargin(dil_sec,16,18,0,0);
        size.setSize(dil_sec,14);
        size.setMargin(close,240,16,0,0);
        size.setMargin(linear_activity,0,17,0,0);
        size.setHeight(view_activity,1);
        size.setWidth(view_activity,0);
        size.setMargin(linear_activity2,0,21,0,0);
        size.setWidth(tr,63);
        size.setHeight(tr,63);
        size.setWidth(uk,63);
        size.setHeight(uk,63);
        size.setMargin(uk,53,0,0,0);
        size.setWidth(ua,63);
        size.setHeight(ua,63);
        size.setMargin(linear_activity3,24,24,0,0);
        size.setWidth(ar,63);
        size.setHeight(ar,63);
        size.setWidth(pak,63);
        size.setHeight(pak,63);
        size.setWidth(ru,63);
        size.setHeight(ru,63);
        size.setWidth(main_card,343);
        size.setHeight(main_card,323);

        */


        close.setOnClickListener(view -> {
            dialog.dismiss();
        });
        //main_card.setTranslationX(800);
        float v=0;
        //main_card.setAlpha(v);
       // main_card.animate().translationX(1).setDuration(800).setStartDelay(300).start();
        //Path path = new Path();
        //path.arcTo(0f, 0f, 0f, -300f, 270f, 300f, true);
        //ObjectAnimator animator = ObjectAnimator.ofFloat(main_card, View.X, View.Y, path);
        //animator.setDuration(2000);
        //animator.start();
        dialog.show();
        dialog.getWindow().setWindowAnimations(R.style.AnimationPopup);
        //dialog.getWindow().getAttributes().windowAnimations = FLAG_DIM_BEHIND;
        dialog.getWindow().clearFlags(FLAG_DIM_BEHIND);
    }

    public void hoursDialog() {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.hours_dialog);
        Size size = new Size(activity);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);


        CardView main_card = window.findViewById(R.id.main_card);
        TextView text_hours = window.findViewById(R.id.text_hours);
        ImageView close = window.findViewById(R.id.close);
        LinearLayout linear_activity = window.findViewById(R.id.linear_activity);
        View view_activity = window.findViewById(R.id.view_activity);

        /*
        size.setWidth(main_card,343);
        size.setHeight(main_card,323);
        size.setMargin(text_hours,16,18,0,0);
        size.setSize(text_hours,14);
        size.setMargin(close,158,16,0,0);
        size.setMargin(linear_activity,0,17,0,0);
        size.setWidth(view_activity,0);
        size.setHeight(view_activity,1);

         */


        close.setOnClickListener(view -> {
            dialog.dismiss();
        });

        float v=0;

        dialog.show();
        dialog.getWindow().setWindowAnimations(R.style.AnimationPopup);
        dialog.getWindow().clearFlags(FLAG_DIM_BEHIND);
    }

    private void register(final String email, String name, String password,Dialog dialog1) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = auth.getCurrentUser();

                            DocumentReference reference = firebaseFirestore.collection("users").document(firebaseUser.getUid());

                            Map<String, String> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("name", name);
                            hashMap.put("password", password);

                            reference.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    profile_image.setImageResource(R.drawable.profile);

                                    kullanici_adi.setText("Merhaba,\n" + name);
                                    kullanici_adi.setVisibility(View.VISIBLE);
                                    linearLayout.setBackgroundResource(R.drawable.background1);
                                    dialog1.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(activity, "Bu e-posta veya ??ifre ile kay??t olamazs??n??z", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
