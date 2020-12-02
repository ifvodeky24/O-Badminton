package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hero.o_badminton.R;
import com.hero.o_badminton.config.ServerConfig;
import com.hero.o_badminton.response.PengelolaDetailResponse;
import com.hero.o_badminton.response.PengelolaResponse;
import com.hero.o_badminton.response.PenggunaDetailResponse;
import com.hero.o_badminton.response.PenggunaResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.HttpFileUpload;
import com.hero.o_badminton.util.SessionManager;

import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahAkunActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edt_username, edt_nama_lengkap, edt_alamat, edt_email, edt_nomor_handphone, edt_nomor_rekening;
    Button btn_ubah_akun;
    ImageView iv_foto_profil;
    Spinner spinner1;

    LinearLayout ll_no_rekening, ll_nama_bank;

    SessionManager sessionManager;
    ApiInterface apiInterface;

    String id_pengelola, id_pengguna, username, nama_lengkap, alamat, email, nomor_handphone, foto_profil, nama_bank, nomor_rekening;

    public static final String IMAGE_DIRECTORY = "OBadminton";
    public String finalPhotoName, fname;
    private ProgressDialog pDialog;
    private Uri imageCaptureUri;
    private File destFile, file;
    private Boolean upflag = false;
    private Bitmap bmp;
    private File sourceFile;
    private SimpleDateFormat dateFormatter;
    public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    //max allowed size for uploading is 100 KB
    private int max_allowed_size;
    //    private ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_akun);
        this.setTitle("Ubah Akun");

        //create Galeri Folder
        dateFormatter = new SimpleDateFormat(
                DATE_FORMAT, Locale.US);

        file = new File(Environment.getExternalStorageDirectory()
                + "/" + IMAGE_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }

        //max allowed file size for uploading
        max_allowed_size = 150;

        sessionManager = new SessionManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        edt_username = findViewById(R.id.edt_username);
        edt_nama_lengkap = findViewById(R.id.edt_nama_lengkap);
        edt_alamat = findViewById(R.id.edt_alamat);
        edt_email = findViewById(R.id.edt_email);
        edt_nomor_handphone = findViewById(R.id.edt_nomor_handphone);
        edt_nomor_rekening = findViewById(R.id.edt_nomor_rekening);
        iv_foto_profil = findViewById(R.id.iv_foto_profil);
        btn_ubah_akun = findViewById(R.id.btn_ubah_akun);
        ll_no_rekening = findViewById(R.id.ll_no_rekening);
        ll_nama_bank = findViewById(R.id.ll_nama_bank);
        spinner1 = findViewById(R.id.spinner1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);


        if (sessionManager.isLoggedin()){
            if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGGUNA)){
                id_pengguna=sessionManager.getLoginDetail().get(SessionManager.ID);

                apiInterface.penggunaDetail(id_pengguna).enqueue(new Callback<PenggunaDetailResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PenggunaDetailResponse> call, @NonNull Response<PenggunaDetailResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                if (response.body().getMaster().size()>0){
                                    username = response.body().getMaster().get(0).getUsername();
                                    nama_lengkap = response.body().getMaster().get(0).getNamaLengkap();
                                    alamat = response.body().getMaster().get(0).getAlamat();
                                    email = response.body().getMaster().get(0).getEmail();
                                    nomor_handphone = response.body().getMaster().get(0).getNoHp();
                                    foto_profil = response.body().getMaster().get(0).getFoto();
                                    nomor_rekening = response.body().getMaster().get(0).getNoRekening();

                                    edt_username.setText(username);
                                    edt_nama_lengkap.setText(nama_lengkap);
                                    edt_alamat.setText(alamat);
                                    edt_email.setText(email);
                                    edt_nomor_handphone.setText(nomor_handphone);
                                    edt_nomor_rekening.setText(nomor_rekening);
                                }else {
                                    Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PenggunaDetailResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                });

                btn_ubah_akun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateProfilPengguna();
                    }
                });

                iv_foto_profil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGaleriPengguna();
                    }
                });


            }else if (Objects.requireNonNull(sessionManager.getLoginDetail().get(SessionManager.LEVEL_LOGIN)).equalsIgnoreCase(SessionManager.LEVEL_PENGELOLA)){
                id_pengelola=sessionManager.getLoginDetail().get(SessionManager.ID);

                ll_no_rekening.setVisibility(View.GONE);
                ll_nama_bank.setVisibility(View.GONE);

                apiInterface.pengelolaDetail(sessionManager.getLoginDetail().get(SessionManager.ID)).enqueue(new Callback<PengelolaDetailResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PengelolaDetailResponse> call, @NonNull Response<PengelolaDetailResponse> response) {
                        System.out.println("ooooo"+response);
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                if (response.body().getMaster().size()>0){
                                    username = response.body().getMaster().get(0).getUsername();
                                    nama_lengkap = response.body().getMaster().get(0).getNamaLengkap();
                                    alamat = response.body().getMaster().get(0).getAlamat();
                                    email = response.body().getMaster().get(0).getEmail();
                                    nomor_handphone = response.body().getMaster().get(0).getNoHp();
                                    foto_profil = response.body().getMaster().get(0).getFoto();

                                    edt_username.setText(username);
                                    edt_nama_lengkap.setText(nama_lengkap);
                                    edt_alamat.setText(alamat);
                                    edt_email.setText(email);
                                    edt_nomor_handphone.setText(nomor_handphone);
                                }else {
                                    Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PengelolaDetailResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                });

                btn_ubah_akun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateProfilPengelola();
                    }
                });

                iv_foto_profil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGaleriPengelola();
                    }
                });
            }
        }
    }

    private void openGaleriPengelola() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar dari Galeri"), 102);

    }

    private void openGaleriPengguna() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar dari Galeri"), 103);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        try {
            if (requestCode == 102) {
                if (resultCode == Activity.RESULT_OK) {
                    finalPhotoName = "pengelola_" + dateFormatter.format(new Date()) + ".jpg";

                    upflag = true;

                    Uri uriPhoto = null;
                    if (data != null) {
                        uriPhoto = data.getData();
                    }
                    if (uriPhoto != null) {
                        Log.d("Pick Galeri Image", "Selected Image Uri path : " + uriPhoto.toString());
                    }

                    iv_foto_profil.setVisibility(View.VISIBLE);
                    iv_foto_profil.setImageURI(uriPhoto);

                    sourceFile = new File(getPathFromGooglePhotosUri(uriPhoto));
                    fname = finalPhotoName;

                    destFile = new File(file, fname);

                    //size in KB
                    final int[] file_size = {Integer.parseInt(String.valueOf(sourceFile.length() / 1024))};

                    System.out.println("file size :" + file_size[0]);

                    Log.d("Source File Path :", sourceFile.toString());

                    try {
                        copyFile(sourceFile, destFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Glide.with(getApplicationContext())
                            .load("file:///storage/emulated/0/" + IMAGE_DIRECTORY + "/" + fname)
                            .into(iv_foto_profil);

                    upload_foto_pengelola();
                }
            }else if (requestCode == 103){
                if (resultCode == Activity.RESULT_OK) {
                    finalPhotoName = "pengguna_" + dateFormatter.format(new Date()) + ".jpg";

                    upflag = true;

                    Uri uriPhoto = null;
                    if (data != null) {
                        uriPhoto = data.getData();
                    }
                    if (uriPhoto != null) {
                        Log.d("Pick Galeri Image", "Selected Image Uri path : " + uriPhoto.toString());
                    }

                    iv_foto_profil.setVisibility(View.VISIBLE);
                    iv_foto_profil.setImageURI(uriPhoto);

                    sourceFile = new File(getPathFromGooglePhotosUri(uriPhoto));
                    fname = finalPhotoName;

                    destFile = new File(file, fname);

                    //size in KB
                    final int[] file_size = {Integer.parseInt(String.valueOf(sourceFile.length() / 1024))};

                    System.out.println("file size :" + file_size[0]);

                    Log.d("Source File Path :", sourceFile.toString());

                    try {
                        copyFile(sourceFile, destFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Glide.with(getApplicationContext())
                            .load("file:///storage/emulated/0/" + IMAGE_DIRECTORY + "/" + fname)
                            .into(iv_foto_profil);

                    upload_foto_pengguna();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upload_foto_pengguna() {
        final String foto = finalPhotoName;

        boolean isEmpty = false;

        if (foto == null){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Foto masih kosong", Toast.LENGTH_SHORT).show();
        }

        System.out.println("datanyaa ini "+foto+" kemudian "+id_pengguna);

        apiInterface.uploadFotoPengguna(id_pengguna, foto).enqueue(new Callback<PenggunaResponse>() {
            @Override
            public void onResponse(@NonNull Call<PenggunaResponse> call, @NonNull Response<PenggunaResponse> response) {
                if (response.isSuccessful()){
                    System.out.println("datanyaa "+foto);

                    new UploadFotoPengguna().execute();

                    if (response.body() != null) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast
                            .LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PenggunaResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast
                        .LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void upload_foto_pengelola() {
        final String foto = finalPhotoName;

        boolean isEmpty = false;

        if (foto == null){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Foto masih kosong", Toast.LENGTH_SHORT).show();
        }

        System.out.println("hasilll "+id_pengelola+" dan "+foto);

        apiInterface.uploadFotoPengelola(id_pengelola, foto).enqueue(new Callback<PengelolaResponse>() {
            @Override
            public void onResponse(@NonNull Call<PengelolaResponse> call, @NonNull Response<PengelolaResponse> response) {
                if (response.isSuccessful()){
                    System.out.println("data "+foto);

                    new UploadFotoPengelola().execute();

                    if (response.body() != null) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast
                            .LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PengelolaResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast
                        .LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    class UploadFotoPengguna extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(UbahAkunActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Mohon menunggu, sedang mengupload gambar..");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                // Set your file path here
                FileInputStream fstrm = new FileInputStream(destFile);
                // Set your server page url (and the file title/description)
                HttpFileUpload hfu = new HttpFileUpload(ServerConfig.UPLOAD_FOTO_PENGGUNA, "ftitle", "fdescription", finalPhotoName);
                upflag = hfu.Send_Now(fstrm);

            } catch (FileNotFoundException e) {
                // Error: File not found
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (upflag) {
                // selesaikan activity
                finish();
//                restartThisActivity();
            } else {
                Toast.makeText(getApplicationContext(), "Sayangnya gambar tidak bisa diupload..", Toast.LENGTH_LONG).show();
            }
        }

        private void restartThisActivity() {
            startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    class UploadFotoPengelola extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(UbahAkunActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Mohon menunggu, sedang mengupload gambar..");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                // Set your file path here
                FileInputStream fstrm = new FileInputStream(destFile);
                // Set your server page url (and the file title/description)
                HttpFileUpload hfu = new HttpFileUpload(ServerConfig.UPLOAD_FOTO_PENGELOLA, "ftitle", "fdescription", finalPhotoName);
                upflag = hfu.Send_Now(fstrm);

            } catch (FileNotFoundException e) {
                // Error: File not found
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (upflag) {
                // selesaikan activity
                finish();
//                restartThisActivity();
            } else {
                Toast.makeText(getApplicationContext(), "Sayangnya gambar tidak bisa diupload..", Toast.LENGTH_LONG).show();
            }
        }

        private void restartThisActivity() {
            startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void updateProfilPengelola() {
        String username = edt_username.getText().toString();
        String nama_lengkap = edt_nama_lengkap.getText().toString();
        String alamat = edt_alamat.getText().toString();
        String email = edt_email.getText().toString();
        String nomor_hp = edt_nomor_handphone.getText().toString();

        boolean isEmpty = false;

        if (username.equalsIgnoreCase("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Username masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (nama_lengkap.equalsIgnoreCase("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Nama Lengkap masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (alamat.equalsIgnoreCase("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Alamat masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (email.equalsIgnoreCase("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Email masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (nomor_hp.equalsIgnoreCase("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Nomor Handphone masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (!isEmpty){
            apiInterface.ubahProfilPengelola(id_pengelola, username, nama_lengkap, alamat, email, nomor_hp).enqueue(new Callback<PengelolaResponse>() {
                @Override
                public void onResponse(@NonNull Call<PengelolaResponse> call, @NonNull Response<PengelolaResponse> response) {
                    System.out.println("responnya adalah"+response);
                    if (response.isSuccessful()){
                        if (response.body() != null) {
                            if (response.body().getCode() == 1){
                                Intent intent = new Intent(UbahAkunActivity.this, MainActivity.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                sessionManager.logout();
                                finish();
                                startActivity(intent);

                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast
                                        .LENGTH_SHORT).show();

                                Toast.makeText(getApplicationContext(), "Silahkan login kembali", Toast.LENGTH_SHORT).show();


                            }else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast
                                        .LENGTH_SHORT).show();
                            }
                        }

                    }else {
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast
                                .LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PengelolaResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Gagal Koneksi Server", Toast
                            .LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateProfilPengguna() {
        String username = edt_username.getText().toString();
        String nama_lengkap = edt_nama_lengkap.getText().toString();
        String alamat = edt_alamat.getText().toString();
        String email = edt_email.getText().toString();
        String nomor_hp = edt_nomor_handphone.getText().toString();
        String no_rekening = edt_nomor_rekening.getText().toString();

        boolean isEmpty = false;

        if (username.equalsIgnoreCase("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Username masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (nama_lengkap.equalsIgnoreCase("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Nama Lengkap masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (alamat.equalsIgnoreCase("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Alamat masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (email.equalsIgnoreCase("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Email masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (nomor_hp.equalsIgnoreCase("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Nomor Handphone masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (no_rekening.equalsIgnoreCase("")){
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Nomor rekening masih kosong", Toast.LENGTH_SHORT).show();
        }

        if (!isEmpty){
            apiInterface.ubahProfilPengguna(id_pengguna, username, nama_lengkap, alamat, email, nomor_hp, no_rekening, nama_bank).enqueue(new Callback<PenggunaResponse>() {
                @Override
                public void onResponse(@NonNull Call<PenggunaResponse> call, @NonNull Response<PenggunaResponse> response) {
                    System.out.println("responnya adalah"+response);
                    if (response.isSuccessful()){
                        if (response.body() != null) {
                            if (response.body().getCode() == 1){
                                Intent intent = new Intent(UbahAkunActivity.this, MainActivity.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                sessionManager.logout();
                                finish();
                                startActivity(intent);

                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast
                                        .LENGTH_SHORT).show();

                                Toast.makeText(getApplicationContext(), "Silahkan login kembali", Toast.LENGTH_SHORT).show();


                            }else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast
                                        .LENGTH_SHORT).show();
                            }
                        }

                    }else {
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast
                                .LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PenggunaResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Gagal Koneksi Server", Toast
                            .LENGTH_SHORT).show();
                }
            });
        }
    }

    private Bitmap decodeFile(File f, String final_photo_name) {
        Bitmap b = null;

        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int IMAGE_MAX_SIZE = 1024;
        int scale = 1;
        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
            scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
                    (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try {
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("TAG", "Width :" + b.getWidth() + " Height :" + b.getHeight());

        fname = final_photo_name;
        destFile = new File(file, fname);

        return b;
    }

    public String getPathFromGooglePhotosUri(Uri uriPhoto) {
        if (uriPhoto == null)
            return null;

        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uriPhoto, "r");
            FileDescriptor fd = pfd.getFileDescriptor();
            input = new FileInputStream(fd);

            String tempFilename = getTempFilename(this);
            output = new FileOutputStream(tempFilename);

            int read;
            byte[] bytes = new byte[4096];
            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
            return tempFilename;
        } catch (IOException ignored) {
            // Nothing we can do
        } finally {
            closeSilently(input);
            closeSilently(output);
        }
        return null;
    }

    public static void closeSilently(Closeable c) {
        if (c == null)
            return;
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }

    private static String getTempFilename(Context context) throws IOException {
        File outputDir = context.getCacheDir();
        File outputFile = File.createTempFile("image", "tmp", outputDir);
        return outputFile.getAbsolutePath();
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        nama_bank = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
