package com.hero.o_badminton.config;

public class ServerConfig {
//    public static final String ENDPOINT = "http://192.168.43.133";  //wifi hp
    public static final String ENDPOINT = "http://192.168.0.100";  //wifi kos
    //    public static final String ENDPOINT = "http://192.168.1.14";  //wifi galaxy bangau
//    public static final String ENDPOINT = "http://192.168.100.248";  //wifi rumah
//    private static final String ENDPOINT = "http://192.168.100.21";
    public static final String SERVER = ENDPOINT + "/o-badminton/api/v1/";
    public static final String GOR_IMAGE = ENDPOINT + "/o-badminton/web/files/gor/";
    public static final String PENGGUNA_IMAGE = ENDPOINT + "/o-badminton/web/files/pengguna/";
    public static final String PENGELOLA_IMAGE = ENDPOINT + "/o-badminton/web/files/pengelola/";

    public static final String UPLOAD_FOTO_PENGGUNA = ENDPOINT + "/o-badminton/api/upload/upload-foto-pengguna.php";
    public static final String UPLOAD_FOTO_PENGELOLA = ENDPOINT + "/o-badminton/api/upload/upload-foto-pengelola.php";
    public static final String UPLOAD_FOTO_GOR = ENDPOINT + "/o-badminton/api/upload/upload-foto-gor.php";
}
