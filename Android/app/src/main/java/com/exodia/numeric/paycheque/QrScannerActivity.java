package com.exodia.numeric.paycheque;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.exodia.numeric.paycheque.model.Cheque;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import static android.R.attr.bitmap;
import static com.exodia.numeric.paycheque.R.id.imageView;

public class QrScannerActivity extends AppCompatActivity {
    private TextView tv_issuedby, tv_amount, tv_name, tv_account, tv_phone;
    private LinearLayout ll_done, ll_progress, ll_notfound, ll_form;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 2;
    private static final int REQUEST_IMAGE_CAPTURE= 1;
    private Cheque cheque;
    private Bitmap bitmap;
    String encodedImage;
    //qr code scanner object
    private IntentIntegrator qrScan;
    ImageView iv_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        //instinstiate things
        tv_issuedby = (TextView) findViewById(R.id.tv_issued_by);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_account = (TextView) findViewById(R.id.tv_accno);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        ll_done = (LinearLayout) findViewById(R.id.ll_done);
        ll_progress = (LinearLayout) findViewById(R.id.ll_progress);
        ll_notfound = (LinearLayout) findViewById(R.id.ll_not);
        ll_form = (LinearLayout) findViewById(R.id.ll_form);
        iv_icon = (ImageView) findViewById(R.id.icon);
        //set visibility to zero
        ll_done.setAlpha(0.0f);
        ll_progress.setAlpha(0.0f);
        ll_notfound.setAlpha(0.0f);

        ll_form.animate().alpha(0.0f).setDuration(0);


        //intializing scan object
       // qrScan = new IntentIntegrator(this);
       // qrScan.setOrientationLocked(true);
        //initiating the qr code scan
        //qrScan.initiateScan();
        takePicture();


    }
    public void takePicture(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_USE_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /*IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                QrScannerActivity.this.finish();
            } else {
                //if qr contains data

                //converting the data to json
                //formate of data is account,name,amount,issuedto
                String s[] = result.getContents().toString().split(",");

                if(s.length==4){
                    qrScan.setOrientationLocked(false);

                    cheque = new Cheque(s[0],s[1],s[2],s[3]);
                    ll_done.animate().alpha(1.0f).setDuration(1000);
                   ll_form.animate().alpha(1.0f).setDuration(1000);
                    tv_issuedby.setText(cheque.getName());
                    tv_amount.setText(cheque.getAmount());
                }
                else{
                ll_notfound.animate().alpha(1.0f).setDuration(1000);

                }
                //JSONObject obj = new JSONObject(result.getContents());

                 //   Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                //setting values to textviewst
                // textView.setText(result.getContents());
                //if control comes here
                //that means the encoded format not matches
                //in this case you can display whatever data is available on the qrcode
                //to a toast

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }*/

         if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            iv_icon.setImageBitmap(imageBitmap);

             //compress bitmap
             Bitmap bm = imageBitmap;
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
             byte[] b = baos.toByteArray();

             encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
             //to convert simple bitmap to zixng supported BinaryBitmap
             //and return String result
             String resultString = BitmapTOZixngBinaryBitmapConvert(imageBitmap);

             if(!resultString.equals("not found")){
                 String s[] = resultString.toString().split(",");

                 if(s.length==4){

                     cheque = new Cheque(s[0],s[1],s[2],"","","",s[3]);
                     ll_done.animate().alpha(1.0f).setDuration(1000);
                     ll_form.animate().alpha(1.0f).setDuration(1000);
                     tv_issuedby.setText(cheque.getName());
                 }
                 else{
                     ll_notfound.animate().alpha(1.0f).setDuration(1000);

                 }
             }else{
                 ll_notfound.animate().alpha(1.0f).setDuration(1000);
             }
        }else{
             this.finish();
         }

    }

    @Override
    public void onBackPressed() {
         super.onBackPressed();
        //ll_progress.animate().alpha(0.0f).setDuration(500);
        //ll_form.animate().alpha(0.0f).setDuration(500);

      //  ll_done.animate().alpha(1.0f).setDuration(2000);

    }

    public void proceed(View v){
      if(!tv_amount.getText().toString().equals("")&&!tv_name.getText().toString().equals("")&&!tv_account.getText().toString().equals("")&&!tv_phone.getText().toString().equals("")){
          //ll_form.setVisibility(View.INVISIBLE);
          ll_done.animate().alpha(0.0f).setDuration(500);
          ll_form.animate().alpha(0.0f).setDuration(2000);
          ll_progress.animate().alpha(1.0f).setDuration(2000);

          String user = tv_name.getText().toString();
          String phone = tv_phone.getText().toString();
          String amount = tv_amount.getText().toString();
        //proceed to asysnc task for online submission..
              ReportLoader r = new ReportLoader(this,cheque.getAccount_no(),tv_account.getText().toString(),amount);
              r.execute();


      }else{
          Toast.makeText(this, "fill details", Toast.LENGTH_SHORT).show();
      }
    }

    public class ReportLoader extends AsyncTask<Void,Void,String> {

        Context context;
        String acc_from;String acc_to;String amount;
        ReportLoader(Context context,String acc_from,String acc_to,String amount)
        {
            this.context=context;
            this.acc_from = acc_from;
            this.acc_to = acc_to;
            this.amount = amount;
        }
        @Override
        protected String doInBackground(Void... params) {


            try{

                URL url =new URL("https://exodia-incredible100rav.cs50.io/android/paycheque/paycheque.php");
                HttpURLConnection httpurlconnection= (HttpURLConnection) url.openConnection();
                httpurlconnection.setRequestMethod("POST");
                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);

                OutputStream outputstream = httpurlconnection.getOutputStream();
                BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));

                String post_data = URLEncoder.encode("acc_from", "UTF-8") + "=" + URLEncoder.encode(acc_from, "UTF-8") + "&"
                        + URLEncoder.encode("acc_to", "UTF-8") + "=" + URLEncoder.encode(acc_to, "UTF-8")+ "&"
                        + URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(amount, "UTF-8")+ "&"
                        + URLEncoder.encode("cheque_id", "UTF-8") + "=" + URLEncoder.encode(cheque.getCheque_id(), "UTF-8") + "&"
                        + URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(encodedImage, "UTF-8");

                bufferedwriter.write(post_data);
                bufferedwriter.flush();
                bufferedwriter.close();

                InputStream inputstream= httpurlconnection.getInputStream();
                BufferedReader bufferedreader= new BufferedReader(new InputStreamReader(inputstream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedreader.readLine())!=null){
                    result+=line;
                }
                bufferedreader.close();
                inputstream.close();
                httpurlconnection.disconnect();

                return result;

            }catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }


        @Override
        protected void onPreExecute() {
           // Toast.makeText(QrScannerActivity.this, ""+encodedImage, Toast.LENGTH_SHORT).show();

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result ) {

            if(result ==null){
                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
            }
            else if(result.equalsIgnoreCase("")){
                Toast.makeText(context, "Error Occured. Try Again", Toast.LENGTH_SHORT).show();
            }
            else if(result.equalsIgnoreCase("0")){
                Toast.makeText(context, "account not found.", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("1")){
               sendSMSMessage();
                Log.e("h","Trying to Send Message");
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("-1")){
                Toast.makeText(context, "Cheque account is not valid", Toast.LENGTH_SHORT).show();
            }else if(result.equals("-2")){
                Toast.makeText(context, "Cheque Bounce", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(QrScannerActivity.this, ""+result, Toast.LENGTH_SHORT).show();
            QrScannerActivity.this.finish();
        }
    }

    public String BitmapTOZixngBinaryBitmapConvert(Bitmap bMap){
        String contents = "";

        int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
//copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));


        try {
            Reader reader = new MultiFormatReader();
            Result result = null;
            result = reader.decode(bitmap);
            contents = result.getText();
            //Toast.makeText(this, ""+contents, Toast.LENGTH_SHORT).show();
            return contents;
        } catch (NotFoundException e) {
            // e.printStackTrace();
            Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show();
            return "not found";
        } catch (ChecksumException e) {
            //e.printStackTrace();
            Toast.makeText(this, "checksum Exception", Toast.LENGTH_SHORT).show();
        } catch (FormatException e) {
            // e.printStackTrace();
            Toast.makeText(this,"format Excepttion" ,Toast.LENGTH_SHORT).show();
        }
        return "not found";
    }
    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);

            }
        }else{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(cheque.getSenderPhone(), null," Your Cheque \nXXXX "+cheque.getCheque_id()+" \nhas been proceed by \n"+ tv_name.getText().toString()+" \naccount no: "+tv_account.getText().toString() +"\nAmount :"+tv_amount.getText().toString()+"\nRegards \nBank.", null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(cheque.getSenderPhone(), null, "Your Cheque \nXXXX "+cheque.getCheque_id()+" \nhas been withdrawn by\n"+ tv_name.getText().toString()+" \naccount no: "+cheque.getAccount_no()+"\nRegards \nBank.", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            case MY_PERMISSIONS_REQUEST_USE_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }

                } else {

                    Toast.makeText(getApplicationContext(),
                            "Camera not Granted.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }

    }
}

