package com.exodia.numeric.paycheque;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Random;

import static com.exodia.numeric.paycheque.R.id.account;
import static com.exodia.numeric.paycheque.R.id.imageView;
import static com.exodia.numeric.paycheque.R.id.phone;

public class QRGeneratorActivity extends AppCompatActivity {
    private EditText tv_user,tv_amount,tv_reciever;
    private String my_acc_no,my_name,phone;
    private  ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generator);

        imageView = (ImageView) findViewById(R.id.imageview);
        tv_user = (EditText) findViewById(R.id.tv_name);
        tv_amount = (EditText) findViewById(R.id.tv_amount);
        tv_reciever = (EditText) findViewById(R.id.tv_rec);

        my_acc_no = getIntent().getStringExtra("acc");
        my_name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");

    }
public void create(View v){
    //hide keyboard
    View view = this.getCurrentFocus();
    if (view != null) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    //create bitmap
    try {


        Bitmap bitmap = encodeAsBitmap(my_acc_no+231+","+my_acc_no+","+my_name+","+tv_amount.getText().toString()+","+tv_user.getText().toString()+tv_reciever.getText().toString()+","+phone);
        imageView.setImageBitmap(bitmap);
    } catch (WriterException e) {
        e.printStackTrace();
    }
}
    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        int WHITE = 0xFFFFFFFF;
        int BLACK = 0xFF000000;
        int WIDTH = 400;
        int HEIGHT = 400;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }
}
