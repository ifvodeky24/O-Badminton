package com.hero.o_badminton.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hero.o_badminton.R;
import com.hero.o_badminton.config.ServerConfig;
import com.hero.o_badminton.response.TambahGorResponse;
import com.hero.o_badminton.rest.ApiClient;
import com.hero.o_badminton.rest.ApiInterface;
import com.hero.o_badminton.util.HttpFileUpload;
import com.hero.o_badminton.util.SessionManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

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
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahGorActivity extends AppCompatActivity implements OnMapReadyCallback {
    EditText edtNamaGor, edtAlamat, edtDeskripsi, edtJumlahLapangan, edtFasilitas;
    Button btnPilihFoto, btnTambahGor;
    ImageView ivGambarGor;

    GoogleMap mMap;

    String latitude, longitude;

    ApiInterface apiInterface;

    public SessionManager sessionManager;

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
        setContentView(R.layout.activity_tambah_gor);
        setTitle("Tambah Data Gor");

        edtNamaGor = findViewById(R.id.edtNamaGor);
        edtAlamat = findViewById(R.id.edtAlamat);
        edtDeskripsi = findViewById(R.id.edtDeskripsi);
        edtJumlahLapangan = findViewById(R.id.edtJumlahLapangan);
        edtFasilitas = findViewById(R.id.edtFasilitas);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);
        btnTambahGor = findViewById(R.id.btnTambahGor);
        ivGambarGor = findViewById(R.id.ivGambarGor);

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

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        //Maps
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_lokasi);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGaleri();
            }
        });

        btnTambahGor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_data();
            }
        });
    }

    private void update_data() {
        final String foto = finalPhotoName;

        boolean isEmptys = false;

        if (foto == null) {
            isEmptys = true;
            Toast.makeText(getApplicationContext(), "Foto masih kosong", Toast.LENGTH_SHORT).show();
        }

        boolean isEmpty = false;

        final String nama_gor = edtNamaGor.getText().toString();
        final String deskripsi = edtDeskripsi.getText().toString();
        final String fasilitas = edtFasilitas.getText().toString();
        final String alamat = edtAlamat.getText().toString();
        final String jumlah_lapangan = edtJumlahLapangan.getText().toString();

        if (nama_gor.equalsIgnoreCase("")) {
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Nama Gor tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }

        if (deskripsi.equalsIgnoreCase("")) {
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Deskripsi tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }

        if (fasilitas.equalsIgnoreCase("")) {
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Fasilitas tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }

        if (alamat.equalsIgnoreCase("")) {
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Alamat tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }

        if (jumlah_lapangan.equalsIgnoreCase("")) {
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Jumlah Lapangan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }

        if (latitude == null) {
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Titik Lokasi tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }

        if (longitude == null) {
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Titik Lokasi tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }

        if (ivGambarGor.getVisibility() == View.GONE) {
            isEmpty = true;
            Toast.makeText(getApplicationContext(), "Foto Gor tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }


        if (!isEmpty) {
            apiInterface.tambahGor(sessionManager.getLoginDetail().get(SessionManager.ID), nama_gor, alamat, longitude, latitude, deskripsi, jumlah_lapangan, fasilitas, "Tersedia", foto).enqueue(new Callback<TambahGorResponse>() {
                @Override
                public void onResponse(@NonNull Call<TambahGorResponse> call, @NonNull Response<TambahGorResponse> response) {
                    System.out.println("oooog " + response);
                    if (response.isSuccessful()) {
                        new UploadFoto().execute();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TambahGorResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void openGaleri() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar dari Galeri"), 102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            if (requestCode == 102) {
                if (resultCode == Activity.RESULT_OK) {
                    finalPhotoName = "gor_" + dateFormatter.format(new Date()) + ".jpg";

                    upflag = true;

                    Uri uriPhoto = null;
                    if (data != null) {
                        uriPhoto = data.getData();
                    }
                    if (uriPhoto != null) {
                        Log.d("Pick Galeri Image", "Selected Image Uri path : " + uriPhoto.toString());
                    }

                    ivGambarGor.setVisibility(View.VISIBLE);
                    ivGambarGor.setImageURI(uriPhoto);

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
                            .into(ivGambarGor);

//                    upload_data();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    class UploadFoto extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(TambahGorActivity.this);
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
                HttpFileUpload hfu = new HttpFileUpload(ServerConfig.UPLOAD_FOTO_GOR, "ftitle", "fdescription", finalPhotoName);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(10);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0.507068, 101.447779)));

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            latitude = extras.getString("latitude");
            longitude = extras.getString("longitude");
            System.out.println("kucing" + latitude + " " + longitude);

            if (latitude != null && longitude != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude))));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)))
                        .title("Lokasi"));
            }
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent1 = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent1);
                finish();

            }
        });

        //tambahan untuk permission
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now

                            mMap.setMyLocationEnabled(true);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
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

}
