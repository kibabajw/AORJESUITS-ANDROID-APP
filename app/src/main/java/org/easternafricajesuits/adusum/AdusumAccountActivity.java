package org.easternafricajesuits.adusum;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.MainActivity;
import org.easternafricajesuits.adusum.catalogue.CatalogueActivity;
import org.easternafricajesuits.adusum.constitution.ConstitutionActivity;
import org.easternafricajesuits.adusum.model.AdusumModel;
import org.easternafricajesuits.adusum.model.CatalogueModel;
import org.easternafricajesuits.adusum.model.ImageClass;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.adusum.databases.AdusumAccountSQLHelper;
import org.easternafricajesuits.adusum.databases.AdusumCursorAdapter;
import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.model.AdusumDocuments;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdusumAccountActivity extends AppCompatActivity {

    private static final int PERMISSIONS_FILES_REQUEST_CODE = 11;

    public static final String MY_USER_ID = "MY_USER_ID";
    private RecyclerView recyclerView;
    private AdusumDocuments adusumDocuments;

    private static final int BROTHER_LOADER = 0;

    AdusumCursorAdapter mCursorAdapter;

    FloatingActionButton fabChangeAccountPicture;

    public static final int PICK_PHOTO_REQUEST = 2;
    public static final int MEDIA_TYPE_IMAGE = 4;

    protected Uri mMediaUri;
    private Bitmap bitmap;

    private String mediaPath;
    private String postPath;

    private String userOnlineID = "";

    private TextView tvUsername;
    private TextView tvFullname;
    private TextView tvDateofbirth;
    private TextView tvDateofentry;
    private TextView tvNameofprovincial;
    private TextView tvCurrentcommunity;
    private Toolbar tbAccount;

    private ImageView adusumprofilepicture;

    private TextView textViewCatalogue;
    private TextView textViewConstitution;
    private DownloadManager downloadManager;

    private AdusumAccountSQLHelper mAdusumAccountSQLHelper;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adusum_account);

        mAdusumAccountSQLHelper = new AdusumAccountSQLHelper(this);

        tbAccount = (Toolbar) findViewById(R.id.toolbarAccount);
        setSupportActionBar(tbAccount);

        ActionBar actionBarAccount = getSupportActionBar();
        ((ActionBar) actionBarAccount).setDisplayHomeAsUpEnabled(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        textViewCatalogue = findViewById(R.id.textViewCatalogue);
        textViewConstitution = findViewById(R.id.textViewConstitution);

        textViewConstitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdusumAccountActivity.this, ConstitutionActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.adusumRecycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);

        fetchCatalogue();
        fetchDocuments();

        // initialise views
        tvUsername = (TextView) findViewById(R.id.adusum_brother_info_list_item_info_data);
        tvFullname = (TextView) findViewById(R.id.adusum_list_item_fullname);
        tvDateofbirth = (TextView) findViewById(R.id.adusum_list_item_date_of_birth);
        tvDateofentry = (TextView) findViewById(R.id.adusum_list_item_date_of_entry);
        tvNameofprovincial = (TextView) findViewById(R.id.tVadusumaccountNameofprovincial);
        tvCurrentcommunity = (TextView) findViewById(R.id.tVadusumaccountCurrentcommunity);
        tbAccount = (Toolbar) findViewById(R.id.toolbarAccount);
        adusumprofilepicture = (ImageView) findViewById(R.id.adusumprofilepicture);
        progressDialog = new ProgressDialog(AdusumAccountActivity.this);

         userOnlineID = fetchmyidLocally();

         fetchMyOnlineInfo(userOnlineID);

        fabChangeAccountPicture = (FloatingActionButton) findViewById(R.id.fabChangeaccountpicture);
        fabChangeAccountPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check for permisions first
                askforpermission();
            }
        });
    }

    private void askforpermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(AdusumAccountActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                ActivityCompat.requestPermissions(AdusumAccountActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_FILES_REQUEST_CODE);
            }
        } else {
            openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_FILES_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        new AlertDialog.Builder(this).setTitle("Open gallery")
                                .setMessage("The application requires permission to open the gallery, click Ok to allow")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(AdusumAccountActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_FILES_REQUEST_CODE);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                }
            }
        }
    }



    private void fetchCatalogue() {
        Call<CatalogueModel> catalogueModelCall = RetrofitClient.getInstance().getApi().adusumGetCatalogue();
        catalogueModelCall.enqueue(new Callback<CatalogueModel>() {
            @Override
            public void onResponse(Call<CatalogueModel> call, Response<CatalogueModel> response) {
                if (response.code() == 200) {
                    textViewCatalogue.setText(response.body().getCatalogue_name());
                    textViewCatalogue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(AdusumAccountActivity.this, CatalogueActivity.class));
//                            downloadCatalogue(AllConstants.IMAGE_URL + response.body().getCatalogue_link());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CatalogueModel> call, Throwable t) {
                if (String.valueOf(t.toString().startsWith("java.net.SocketTimeoutException")).equals("true")) {
                    Toast.makeText(AdusumAccountActivity.this, "You are offline", Toast.LENGTH_SHORT).show();
                } else if (String.valueOf(t.toString().startsWith("java.net.ProtocolException")).equals("true")) {
                    Toast.makeText(AdusumAccountActivity.this, "Request timedout", Toast.LENGTH_SHORT).show();
                } else if(String.valueOf(t.toString().startsWith("java.net.ConnectException")).equals("true")) {
                    Toast.makeText(AdusumAccountActivity.this, "No connection", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AdusumAccountActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void downloadCatalogue(String catalogueLocation) {
        downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(catalogueLocation);
        Toast.makeText(AdusumAccountActivity.this, "Downloading catalogue", Toast.LENGTH_SHORT).show();
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);
    }


    private String fetchmyidLocally() {
        String userId = "";
        SQLiteDatabase db = mAdusumAccountSQLHelper.getReadableDatabase();

        String[] projection = {
                AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID
        };

        Cursor cursor = db.query(
                AdusumDatabaseContract.BrotherEntry.ADUSUMACCOUNT_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
                int myIdColumnIndex = cursor.getColumnIndex(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID);

                String id = "";

                while (cursor.moveToNext()) {
                    id = cursor.getString(myIdColumnIndex);
                }
                userId = id;
        } finally {
            cursor.close();
        }

        return userId;
    }

    private void fetchMyOnlineInfo(String userOnlineID) {
        Call<AdusumModel> adusumModelCall = RetrofitClient.getInstance().getApi().adusumGetMyInfo(userOnlineID);
        adusumModelCall.enqueue(new Callback<AdusumModel>() {
            @Override
            public void onResponse(Call<AdusumModel> call, Response<AdusumModel> response) {
                if (response.code() == 200) {
                    tbAccount.setTitle(response.body().getFullname());

                    tvUsername.setText(response.body().getUsername());
                    tvFullname.setText(response.body().getFullname());
                    tvDateofbirth.setText(response.body().getDate_of_birth());
                    tvDateofentry.setText(response.body().getDate_of_entry());
                    tvNameofprovincial.setText(response.body().getName_of_provincial());
                    tvCurrentcommunity.setText(response.body().getCurrent_community());

                    Glide.with(AdusumAccountActivity.this)
                            .load(AllConstants.IMAGE_URL + response.body().getProfile_picture())
                            .placeholder(R.drawable.avatar)
                            .into(adusumprofilepicture);
                }
            }

            @Override
            public void onFailure(Call<AdusumModel> call, Throwable t) {
                if (String.valueOf(t.toString().startsWith("java.net.SocketTimeoutException")).equals("true")) {
                    Toast.makeText(AdusumAccountActivity.this, "You are offline", Toast.LENGTH_SHORT).show();
                } else if (String.valueOf(t.toString().startsWith("java.net.ProtocolException")).equals("true")) {
                    Toast.makeText(AdusumAccountActivity.this, "Request timedout", Toast.LENGTH_SHORT).show();
                } else if(String.valueOf(t.toString().startsWith("java.net.ConnectException")).equals("true")) {
                    Toast.makeText(AdusumAccountActivity.this, "No connection", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AdusumAccountActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchDocuments() {
        Call<AdusumDocuments> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDocuments();
        call.enqueue(new Callback<AdusumDocuments>() {
            @Override
            public void onResponse(Call<AdusumDocuments> call, Response<AdusumDocuments> response) {
                if (response.code() == 200) {
                    List<AdusumDocuments> items = (List<AdusumDocuments>) response.body().getItems();
                    recyclerView.setAdapter(new AdusumAdapter(getApplicationContext(), items));
                    recyclerView.smoothScrollToPosition(0);
                }
            }

            @Override
            public void onFailure(Call<AdusumDocuments> call, Throwable t) {
//                Toast.makeText(AdusumAccountActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adusum_account_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adusum_account_menu_item_settings:
                Intent intentAdusumSettings = new Intent(AdusumAccountActivity.this, AdusumSettingsActivity.class);
                intentAdusumSettings.putExtra(MY_USER_ID, userOnlineID);
                startActivity(intentAdusumSettings);
                return true;
            case R.id.adusum_account_menu_item_logout:
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                if (logout(userOnlineID)) {
                    progressDialog.dismiss();
                    startActivity(new Intent(AdusumAccountActivity.this, MainActivity.class));
                    finish();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openGallery() {
//        Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        Intent choosePhotoIntent = new Intent(Intent.ACTION_PICK);
        choosePhotoIntent.setType("image/*");
        startActivityForResult(choosePhotoIntent, PICK_PHOTO_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO_REQUEST) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            adusumprofilepicture.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

            cursor.close();
            postPath = mediaPath;

            if (postPath != null) {
                File file = new File(postPath);

                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part parts = MultipartBody.Part.createFormData("photo", file.getName(), requestBody);

                RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), userOnlineID);

                Call<ImageClass> imageClassCall = RetrofitClient.getInstance().getApi().uploadImageraw(userid, parts);
                imageClassCall.enqueue(new Callback<ImageClass>() {
                    @Override
                    public void onResponse(Call<ImageClass> call, Response<ImageClass> response) {
                        ImageClass aClass = response.body();
                        Toast.makeText(AdusumAccountActivity.this, aClass.getResponse(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ImageClass> call, Throwable t) {
                        Toast.makeText(AdusumAccountActivity.this, "Could not upload your photo", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(this, "Could not open photo", Toast.LENGTH_LONG).show();
            }
        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean logout(String logoutId) {
        long idtouse = (long) Math.floor(Double.parseDouble(logoutId));
        ContentValues values = new ContentValues();
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN, "0");

        int rowsAffected = getContentResolver().update(
                ContentUris.withAppendedId(AdusumDatabaseContract.BrotherEntry.CONTENT_URI, idtouse), values, null, null);

        if (rowsAffected == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}