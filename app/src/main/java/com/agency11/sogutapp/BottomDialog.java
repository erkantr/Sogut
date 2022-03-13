package com.agency11.sogutapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
    Context context;

    public BottomDialog(Activity activity, FirebaseUser firebaseUser, FirebaseAuth auth, FirebaseFirestore firebaseFirestore,
                        TextView kullanici_adi, ImageView profile_image, LinearLayout linearLayout){
        this.firebaseUser = firebaseUser;
        this.auth = auth;
        this.firebaseFirestore = firebaseFirestore;
        this.activity = activity;
        this.kullanici_adi = kullanici_adi;
        this.profile_image = profile_image;
        this.linearLayout = linearLayout;
    }


    public void loginDialog(int page) {
        View view = activity.getLayoutInflater().inflate(R.layout.login_dialog, null, false);
        dialog = new BottomSheetDialog(activity, R.style.BottomSheetDialog);
        Size size = new Size(activity);

        TextView devam_etmek_icin = view.findViewById(R.id.devam_etmek_icin);
        ImageView image_close_square = view.findViewById(R.id.image_close_square);
        TextView text_girisyap = view.findViewById(R.id.text_girisyap);
        LinearLayout linear1 = view.findViewById(R.id.linear1);
        TextInputLayout text_input_layout = view.findViewById(R.id.text_input_layout);
        TextInputEditText mail = view.findViewById(R.id.mail);
        TextInputLayout text_input_layout1 = view.findViewById(R.id.text_input_layout1);
        TextInputEditText pass = view.findViewById(R.id.password);
        RelativeLayout giris = view.findViewById(R.id.login_button);
        TextView login_button_text = view.findViewById(R.id.login_button_text);
        ImageView login_button_image = view.findViewById(R.id.login_button_image);
        LinearLayout linear2 = view.findViewById(R.id.linear2);
        View view_login = view.findViewById(R.id.view_login);
        TextView text_yada = view.findViewById(R.id.text_yada);
        View view_login1 = view.findViewById(R.id.view_login1);
        LinearLayout linear3 = view.findViewById(R.id.linear3);
        ImageView login_button_image1 = view.findViewById(R.id.login_button_image1);
        ImageView login_button_image2 = view.findViewById(R.id.login_button_image2);
        LinearLayout linear4 = view.findViewById(R.id.linear4);
        TextView text_hesap = view.findViewById(R.id.text_hesap);
        TextView kayit_ol = view.findViewById(R.id.register_text);
        TextView forgot_password = view.findViewById(R.id.forgot_password);

        size.setWidth(devam_etmek_icin,190);
        size.setMargin(devam_etmek_icin,17,42,0,0);
        size.setSize(devam_etmek_icin,10);
        size.setMargin(image_close_square,0,24,24,0);
        size.setMargin(text_girisyap,17,0,0,0);
        size.setSize(text_girisyap,33);
        size.setMargin(linear1,0,-20,0,0);
        size.setPadding(linear1,16,16,16,16);
        size.setMargin(text_input_layout,0,20,0,0);
        size.setSize(mail,14);
        size.setMargin(text_input_layout1,0,20,0,0);
        size.setSize(pass,14);
        size.setMargin(giris,16,42,16,0);
        size.setSize(login_button_text,13);
        size.setWidth(login_button_image,18);
        size.setHeight(login_button_image,18);
        size.setMargin(login_button_image,8,0,0,0);
        size.setMargin(linear2,0,50,0,0);
        size.setWidth(view_login,0);
        size.setHeight(view_login,1);
        size.setSize(text_yada,10);
        size.setWidth(view_login1,0);
        size.setHeight(view_login1,1);
        size.setMargin(linear3,0,50,0,0);
        size.setMargin(login_button_image1,54,0,0,0);
        size.setMargin(login_button_image2,54,0,0,0);
        size.setMargin(linear4,0,50,0,0);
        size.setSize(text_hesap,12);
        size.setMargin(kayit_ol,8,0,0,0);
        size.setSize(kayit_ol,12);
        size.setMargin(forgot_password,64,0,0,0);
        size.setSize(forgot_password,12);

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
                Toast.makeText(activity, "Tüm alanlar zorunludur", Toast.LENGTH_SHORT).show();
            } else {

                auth.signInWithEmailAndPassword(txt_email, txt_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                    if(page ==1){
                                        activity.startActivity(new Intent(activity, MainActivity.class));
                                    }

                                    if (profile_image !=null){
                                        profile_image.setImageResource(R.drawable.profile);
                                    }
                                    if (kullanici_adi !=null){
                                        kullanici_adi.setVisibility(View.VISIBLE);
                                    }
                                    if (linearLayout !=null){
                                        linearLayout.setBackgroundResource(R.drawable.arkaplan);
                                    }
                                    dialog.cancel();

                                } else {
                                    Toast.makeText(activity, "Kimlik doğrulama başarısız!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                if (firebaseUser != null) {
                    DocumentReference reference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
                    reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            User user = documentSnapshot.toObject(User.class);

                            if (kullanici_adi !=null){
                                kullanici_adi.setText("Merhaba,\n" + user.getName());
                            }
                            Toast.makeText(activity, "Giriş yapıldı", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        dialog.setContentView(view);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public void resetPasswordDialog() {
        View view = activity.getLayoutInflater().inflate(R.layout.password_reset_dialog, null, false);
        dialog = new BottomSheetDialog(activity, R.style.BottomSheetDialog);
        Size size = new Size(activity);


        LinearLayout linear_remember = view.findViewById(R.id.linear_remember);
        TextView text_remember = view.findViewById(R.id.text_remember);
        ImageView image_remember = view.findViewById(R.id.image_remember);
        TextView text_remember2 = view.findViewById(R.id.text_remember2);
        LinearLayout linear_remember1 = view.findViewById(R.id.linear_remember1);
        TextInputLayout textinput_remember = view.findViewById(R.id.textinput_remember);
        TextInputEditText mail = view.findViewById(R.id.mail);
        RelativeLayout send = view.findViewById(R.id.send_button);
        TextView send_button_text = view.findViewById(R.id.send_button_text);
        ImageView send_button_image = view.findViewById(R.id.send_button_image);
        LinearLayout linear_remember2 = view.findViewById(R.id.linear_remember2);
        TextView text_remember3 = view.findViewById(R.id.text_remember3);
        TextView login_text_remember = view.findViewById(R.id.login_text_remember);

        size.setWidth(linear_remember,190);
        size.setWidth(text_remember,190);
        size.setMargin(text_remember,17,42,0,0);
        size.setSize(text_remember,10);
        size.setMargin(image_remember,0,24,24,0);
        size.setMargin(text_remember2,17,0,0,0);
        size.setSize(text_remember2,33);
        size.setPadding(linear_remember1,16,16,16,16);
        size.setMargin(linear_remember1,0,-20,0,0);
        size.setMargin(textinput_remember,0,20,0,0);
        size.setSize(mail,14);
        size.setMargin(send,16,42,16,0);
        size.setSize(send_button_text,13);
        size.setWidth(send_button_image,18);
        size.setHeight(send_button_image,18);
        size.setMargin(send_button_image,8,0,0,0);
        size.setMargin(linear_remember2,16,50,0,0);
        size.setSize(text_remember3,12);
        size.setMargin(login_text_remember,8,0,0,0);
        size.setSize(login_text_remember,12);

        send.setOnClickListener(view1 -> {
            String txt_email = mail.getText().toString();

            if (TextUtils.isEmpty(txt_email)) {
                Toast.makeText(activity, "Tüm alanlar zorunludur", Toast.LENGTH_SHORT).show();
            } else {
                auth.sendPasswordResetEmail(txt_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(activity, "Sıfırlama bağlantısı mail adresinize gönderildi", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            loginDialog(0);
                        } else {
                            Toast.makeText(activity, "Sıfırlama bağlantısı gönderilemedi, lütfen mail adresinizi doğru girin", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog.setContentView(view);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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

        size.setWidth(linear_bottom_change,190);
        size.setWidth(text_devam_change,190);
        size.setMargin(text_devam_change,17,42,0,0);
        size.setSize(text_devam_change,10);
        size.setMargin(image_bottom_change,0,24,24,0);
        size.setSize(text_sifre_degistir,33);
        size.setMargin(text_sifre_degistir,17,0,0,0);
        size.setPadding(linear_bottom_change1,16,16,16,16);
        size.setMargin(linear_bottom_change1,0,-20,0,0);
        size.setMargin(text_input_change,0,20,0,0);
        size.setSize(mail,14);
        size.setMargin(text_input_change1,0,20,0,0);
        size.setSize(password3,14);
        size.setMargin(text_input_change2,0,20,0,0);
        size.setSize(password,14);
        size.setMargin(text_input_change3,0,20,0,0);
        size.setSize(password2,14);
        size.setMargin(send,16,42,16,0);
        size.setSize(send_button_text,13);
        size.setWidth(send_button_image,18);
        size.setHeight(send_button_image,18);
        size.setMargin(send_button_image,8,0,0,0);
        size.setMargin(linear_bottom_change2,16,50,0,0);
        size.setSize(text_hesap_varmi,12);
        size.setMargin(login_text,8,0,0,0);
        size.setSize(login_text,12);



        send.setOnClickListener(view1 -> {
            String mail_text = mail.getText().toString();
            String password_text = password.getText().toString();
            String password2_text = password2.getText().toString();
            String password3_text = password3.getText().toString();

            if (TextUtils.isEmpty(password3_text) || TextUtils.isEmpty(mail_text) || TextUtils.isEmpty(password_text) || TextUtils.isEmpty(password_text)) {
                Toast.makeText(activity, "Tüm alanlar zorunludur", Toast.LENGTH_SHORT).show();

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
                                                    System.out.println("Kullanıcı yeniden giriş yaptı");
                                                    if (!finalNewPassword.equals("") && finalNewPassword.length() >= 6) {
                                                        firebaseUser.updatePassword(finalNewPassword)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            System.out.println("Şifre yenileme başarılı");
                                                                            successfulDialog();
                                                                            dialog.cancel();
                                                                        } else {
                                                                            Toast.makeText(activity, "Bu şifre ile kayıt olamazsınız", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    } else {
                                                        Toast.makeText(activity, "şifre en az 6 karakterden oluşmalıdır", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(activity, "Lütfen şifrenizi doğru girin", Toast.LENGTH_SHORT).show();
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
                                    System.out.println("Kullanıcı yeniden giriş yaptı");
                                    if (!newPass.equals("") && newPass.length() >= 6) {
                                        firebaseUser.updatePassword(newPass)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            System.out.println("Şifre yenileme başarılı");
                                                            dialog.cancel();
                                                            successfulDialog();
                                                        } else {
                                                            Toast.makeText(activity, "Bu şifre ile kayıt olamazsınız", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(activity, "şifre en az 6 karakterden oluşmalıdır", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(activity, "Lütfen şifrenizi doğru girin", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(activity, "Şifreler birbirleri ile uyuşmuyor!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setContentView(view);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public void successfulDialog() {
        View view = activity.getLayoutInflater().inflate(R.layout.successful_dialog, null, false);
        dialog = new BottomSheetDialog(activity, R.style.BottomSheetDialog);
        Size size = new Size(activity);



        ImageView image_success = view.findViewById(R.id.image_success);
        TextView text_success = view.findViewById(R.id.text_success);
        ImageView image_success2 = view.findViewById(R.id.image_success2);
        RelativeLayout login = view.findViewById(R.id.login_button);
        TextView login_button_text = view.findViewById(R.id.login_button_text);
        ImageView login_button_image = view.findViewById(R.id.login_button_image);

        size.setMargin(image_success,0,24,24,0);
        size.setMargin(text_success,0,24,0,0);
        size.setSize(text_success,23);
        size.setMargin(image_success2,0,24,0,0);
        size.setMargin(login,16,86,16,0);
        size.setSize(login_button_text,13);
        size.setWidth(login_button_image,18);
        size.setHeight(login_button_image,18);
        size.setMargin(login_button_image,8,0,0,0);

        login.setOnClickListener(view1 -> {
            loginDialog(0);
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public void registerDialog() {
        View view = activity.getLayoutInflater().inflate(R.layout.register_dialog, null, false);
        Dialog dialog = new BottomSheetDialog(activity, R.style.BottomSheetDialog);
        TextInputEditText mail = view.findViewById(R.id.mail);
        TextInputEditText kullanici_adi1 = view.findViewById(R.id.kullanici_adi_edit);
        TextInputEditText pass = view.findViewById(R.id.password);
        RelativeLayout register = view.findViewById(R.id.register_button);

        register.setOnClickListener(view1 -> {
            String txt_email = mail.getText().toString();
            String txt_password = pass.getText().toString();
            String txt_kullanici = kullanici_adi1.getText().toString();
            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)
                    || TextUtils.isEmpty(txt_kullanici)) {
                Toast.makeText(activity, "Tüm alanlar zorunludur", Toast.LENGTH_SHORT).show();
            } else if (!txt_email.endsWith("@gmail.com")) {
                Toast.makeText(activity, "Lütfen geçerli bir e-posta adresi girin", Toast.LENGTH_SHORT).show();
            } else if (txt_password.length() < 6) {
                Toast.makeText(activity, "şifre en az 6 karakterden oluşmalıdır", Toast.LENGTH_SHORT).show();
            } else {
                register(txt_email, txt_kullanici, txt_password);
                Toast.makeText(activity, "Hesabınız Oluşturuldu", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        dialog.setContentView(view);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void register(final String email, String name, String password) {

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
                                    linearLayout.setBackgroundResource(R.drawable.arkaplan);
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(activity, "Bu e-posta veya şifre ile kayıt olamazsınız", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
