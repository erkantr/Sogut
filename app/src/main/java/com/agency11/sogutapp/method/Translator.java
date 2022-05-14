package com.agency11.sogutapp.method;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.Locale;

public class Translator {

    public Translator(){

    }

    public void translateDetails(TextView textView,String text,ShimmerFrameLayout shimmerFrameLayout){
        String targetLanguage = Locale.getDefault().getLanguage();
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.TURKISH)
                        .setTargetLanguage(targetLanguage)
                        .build();
        final com.google.mlkit.nl.translate.Translator translator =
                Translation.getClient(options);


        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Model couldn’t be downloaded or other internal error.
                        // ...
                    }
                });

        translator.translate(text).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s.length() > 47) {
                     textView.setText(s.substring(0, 47) + "...");
                } else {
                     textView.setText(s + "...");
                }

                /*
                if (shimmerFrameLayout != null){
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }

                 */


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (text.length() > 47) {
                    textView.setText(text.substring(0, 47) + "...");
                } else {
                    textView.setText(text + "...");
                }

                /*
                if (shimmerFrameLayout != null){
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }

                 */


            }
        });
    }

    public void translate(TextView textView, String text, ShimmerFrameLayout shimmerFrameLayout){
        String targetLanguage = Locale.getDefault().getLanguage();
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.TURKISH)
                        .setTargetLanguage(targetLanguage)
                        .build();
        final com.google.mlkit.nl.translate.Translator translator =
                Translation.getClient(options);



        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Model couldn’t be downloaded or other internal error.
                        // ...
                    }
                });

        translator.translate(text).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                //String ceviri = translator.translate(detay).getResult();

                /*
                if (shimmerFrameLayout != null){
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }

                 */


                textView.setText(s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                textView.setText(text);

                /*
                if (shimmerFrameLayout != null){
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }

                 */


            }
        });
    }

}
