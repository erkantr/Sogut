package com.agency11.sogutapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agency11.sogutapp.R;
import com.agency11.sogutapp.Size;

import org.w3c.dom.Text;

public class OnboardingActivity extends AppCompatActivity {

    int click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ImageView image = findViewById(R.id.image);
        TextView title = findViewById(R.id.title);
        TextView center = findViewById(R.id.center);
        TextView geri1 = findViewById(R.id.geri1);
        RelativeLayout ilerle_button = findViewById(R.id.ilerle_button);
        TextView ilerle_button_text = findViewById(R.id.ilerle_button_text);
        ImageView ilerle_button_image = findViewById(R.id.ilerle_button_image);
        RelativeLayout background = findViewById(R.id.background);

        click = 0;

        Size size = new Size(this);

        size.setPadding(image,0,78,0,0);
        size.setMargin(title, 0,33, 0, 0);
        size.setSize(title,24);
        size.setPadding(title,16,16,16,16);

        size.setSize(center,14);
        size.setPadding(center,16,12,16,0);
        size.setSize(geri1,13);
        size.setPadding(geri1, 16,0,0,49);

        size.setMargin(ilerle_button,0,0,16,42);
        size.setSize(ilerle_button_text,13);
        size.setMargin(ilerle_button_image,8,0,0,0);
        size.setWidth(ilerle_button_image,18);
        size.setHeight(ilerle_button_image,18);

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        image.setImageResource(R.drawable.ic_image1);
        geri1.setTextColor(getResources().getColor(R.color.gray500));
        geri1.setText("Geç");
        title.setText("Merhaba");
        title.setTextColor(getResources().getColor(R.color.black));
        center.setText("Şehir tanıtım uygulamasına hoşgeldin!\n" +
                "\n" +
                "Burada şehir ve şehirin önde gelen kişileri hakkında öndemli bilgilere ulaşabilirsin. Haydi başlayalım!");
        center.setTextColor(getResources().getColor(R.color.gray800));
        ilerle_button.setBackgroundResource(R.drawable.button2);

        geri1.setOnClickListener(view -> {

            switch (click){
                case 0:
                    startActivity(new Intent(OnboardingActivity.this,MainActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    break;

                case 1:
                    window.setStatusBarColor(this.getResources().getColor(R.color.white));
                    background.setBackgroundColor(getResources().getColor(R.color.white));
                    image.setImageResource(R.drawable.ic_image1);
                    geri1.setTextColor(getResources().getColor(R.color.gray500));
                    geri1.setText("Geç");
                    title.setText("Merhaba");
                    title.setTextColor(getResources().getColor(R.color.black));
                    center.setText("Şehir tanıtım uygulamasına hoşgeldin!\n" +
                            "\n" +
                            "Burada şehir ve şehirin önde gelen kişileri hakkında öndemli bilgilere ulaşabilirsin. Haydi başlayalım!");
                    center.setTextColor(getResources().getColor(R.color.gray800));
                    ilerle_button.setBackgroundResource(R.drawable.button2);
                    click =0;
                    break;
                case 2:
                    window.setStatusBarColor(this.getResources().getColor(R.color.skin_color));
                    background.setBackgroundColor(getResources().getColor(R.color.skin_color));
                    geri1.setTextColor(getResources().getColor(R.color.gray500));
                    image.setImageResource(R.drawable.ic__image2);
                    title.setText("Tarihini öğren!");
                    geri1.setText("Geri");
                    title.setTextColor(getResources().getColor(R.color.black));
                    center.setText("Şehirde bulunan tarihi kişilikleri ziyaret et ve tarihe yüz tutmuş bilgileri öğren!");
                    center.setTextColor(getResources().getColor(R.color.gray800));
                    ilerle_button.setBackgroundResource(R.drawable.button2);
                    click =1;
                    break;
            }
        });

        ilerle_button.setOnClickListener(view -> {
            switch (click) {

                case 0:
                    window.setStatusBarColor(this.getResources().getColor(R.color.skin_color));
                    background.setBackgroundColor(getResources().getColor(R.color.skin_color));
                    geri1.setTextColor(getResources().getColor(R.color.gray500));
                    image.setImageResource(R.drawable.ic__image2);
                    title.setText("Tarihini öğren!");
                    geri1.setText("Geri");
                    title.setTextColor(getResources().getColor(R.color.black));
                    center.setText("Şehirde bulunan tarihi kişilikleri ziyaret et ve tarihe yüz tutmuş bilgileri öğren!");
                    center.setTextColor(getResources().getColor(R.color.gray800));
                    ilerle_button.setBackgroundResource(R.drawable.button2);
                    click = 1;
                    break;

                case 1:
                    window.setStatusBarColor(this.getResources().getColor(R.color.light_blue));
                    background.setBackgroundColor(getResources().getColor(R.color.light_blue));
                    geri1.setTextColor(getResources().getColor(R.color.white));
                    image.setImageResource(R.drawable.ic__image3);
                    title.setText("Önemli yerleri ziyaret et!");
                    geri1.setText("Geri");
                    title.setTextColor(getResources().getColor(R.color.white));
                    center.setText("Şehirde bulunan önemli yerleri ziyaret et ve tarihini daha iyi öğren!\n" +
                            "\n" +
                            "Bulunan türbelerin lokasyonlarını öğren, sana uygun en kısa yoldan buraya ulaş.");
                    center.setTextColor(getResources().getColor(R.color.white));
                    ilerle_button.setBackgroundResource(R.drawable.button_blue);
                    click =2;
                    break;
                case 2:
                    startActivity(new Intent(OnboardingActivity.this,MainActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    break;
            }
        });

    }
}