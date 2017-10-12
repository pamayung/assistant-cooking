package com.labs.wildhan.assistantcooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TulisKomenActivity extends AppCompatActivity {
    Button buttonChoose;
    Button buttonUpload;
    Toolbar toolbar;
    ImageView imageView;
    EditText txtNama;
    EditText txtKomen;
    Bitmap bitmap;
    int success;
    int PICK_IMAGE_REQUEST = 1;

    private static final String TAG = MainActivity.class.getSimpleName();

    private String UPLOAD_URL = "http://pamayung.com/Upload";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String KEY_IMAGE = "gambar_komentar";
    private String KEY_NAME = "nama_komentar";
    private String KEY_KOMEN = "komentar";
//    int i =6;
//    String j = Integer.toString(i);

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tulis_komen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Input Tanggapan");

        buttonChoose = (Button)findViewById(R.id.btn_gambar);
        buttonUpload = (Button) findViewById(R.id.btn_kirim);
        imageView = (ImageView) findViewById(R.id.iv_gbr);
        txtNama = (EditText)findViewById(R.id.edt_nama_anda);
        txtKomen = (EditText)findViewById(R.id.edt_komen);

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageByte = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadImage(){
        if (txtNama.getText().toString().length()==0){
            txtNama.setError("Masukan Nama Anda");
        }else if (txtKomen.getText().toString().length()==0){
            txtKomen.setError("Isi Komentar Anda");
        }else{

            final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Response: " + response.toString());

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                success = jsonObject.getInt(TAG_SUCCESS);

                                if (success == 1) {
                                    Log.d("v Add", jsonObject.toString());

                                    Toast.makeText(TulisKomenActivity.this, jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                    kosong();
                                    Intent intent = new Intent(TulisKomenActivity.this, KomentarActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(TulisKomenActivity.this, jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            loading.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();

                            Toast.makeText(TulisKomenActivity.this, "Anda belum memasukan gambar", Toast.LENGTH_LONG).show();
                            Log.d(TAG, error.getMessage().toString());
                        }
                    }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<String, String>();

                    params.put(KEY_IMAGE, getStringImage(bitmap));
                    params.put(KEY_NAME, txtNama.getText().toString().trim());
                    params.put(KEY_KOMEN, txtKomen.getText().toString().trim());
                    Log.d(TAG, ""+params);
                    return params;
                }
            };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
            AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
        }
    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try {
                //meengambil data dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //menampilkan gambar yang dipilih
                imageView.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void kosong(){
        imageView.setImageResource(0);
        txtNama.setText(null);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
