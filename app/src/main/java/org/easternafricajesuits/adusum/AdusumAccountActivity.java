package org.easternafricajesuits.adusum;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
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
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.FatherGeneralActivity;
import org.easternafricajesuits.FatherProvincialActivity;
import org.easternafricajesuits.MainActivity;
import org.easternafricajesuits.OtherDocumentsActivity;
import org.easternafricajesuits.adusum.catalogue.CatalogueActivity;
import org.easternafricajesuits.adusum.constitution.ConstitutionActivity;
import org.easternafricajesuits.adusum.model.AdusumModel;
import org.easternafricajesuits.adusum.model.CatalogueModel;
import org.easternafricajesuits.adusum.model.ImageClass;
import org.easternafricajesuits.adusum.shukran.ShukranActivity;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.adusum.databases.AdusumAccountSQLHelper;
import org.easternafricajesuits.adusum.databases.AdusumCursorAdapter;
import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.model.AdusumDocuments;
import org.easternafricajesuits.databinding.ActivityAdusumAccountBinding;

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

    private ActivityAdusumAccountBinding binding;
    private static final int PERMISSIONS_FILES_REQUEST_CODE = 11;

    public static final String MY_USER_ID = "MY_USER_ID";

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
    private TextView textViewShukran;
    private TextView textViewConstitution;
    private TextView textViewNecrology;
    private DownloadManager downloadManager;

    private AdusumAccountSQLHelper mAdusumAccountSQLHelper;

    private ProgressDialog progressDialog;

    String [] documenttypes = {"Father Provincial", "Father General", "Others"};
    private ListView listViewDocumentTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdusumAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAdusumAccountSQLHelper = new AdusumAccountSQLHelper(this);

        tbAccount = (Toolbar) findViewById(R.id.toolbarAccount);
        setSupportActionBar(tbAccount);

        ActionBar actionBarAccount = getSupportActionBar();
        ((ActionBar) actionBarAccount).setDisplayHomeAsUpEnabled(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // initialise List for document types
        listViewDocumentTypes = binding.adusumListDocumentTypes;
        ArrayAdapter<String> arrayAdapterDocumentTypes = new ArrayAdapter<String>(this, R.layout.listdocumenttypesadusum, R.id.tv_documenttype,
                documenttypes);
        listViewDocumentTypes.setAdapter(arrayAdapterDocumentTypes);

        listViewDocumentTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(AdusumAccountActivity.this, FatherProvincialActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(AdusumAccountActivity.this, FatherGeneralActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(AdusumAccountActivity.this, OtherDocumentsActivity.class));
                        break;
                }
            }
        });

//        textViewCatalogue = findViewById(R.id.textViewCatalogue);
//        textViewConstitution = findViewById(R.id.textViewConstitution);

        textViewCatalogue = binding.textViewCatalogue;
        textViewShukran = binding.textViewShukran;
        textViewConstitution = binding.textViewConstitution;
        textViewNecrology = binding.textViewNecrology;

        textViewShukran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdusumAccountActivity.this, ShukranActivity.class));
            }
        });

        textViewConstitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdusumAccountActivity.this, ConstitutionActivity.class);
                startActivity(intent);
            }
        });

        textViewNecrology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdusumAccountActivity.this, NecrologyActivity.class));
            }
        });

        fetchCatalogue();

        // initialise views
        tbAccount = (Toolbar) findViewById(R.id.toolbarAccount);
        adusumprofilepicture = (ImageView) findViewById(R.id.adusumprofilepicture);
        progressDialog = new ProgressDialog(AdusumAccountActivity.this);

         userOnlineID = fetchmyidLocally();

         fetchMyOnlineInfo(userOnlineID);
    }

    private void fetchCatalogue() {
        Call<CatalogueModel> catalogueModelCall = RetrofitClient.getInstance().getApi().adusumGetCatalogue();
        catalogueModelCall.enqueue(new Callback<CatalogueModel>() {
            @Override
            public void onResponse(Call<CatalogueModel> call, Response<CatalogueModel> response) {
                if (response.code() == 200) {
                    textViewCatalogue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(AdusumAccountActivity.this, CatalogueActivity.class));
                                }
                            }, 500);

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
                    tbAccount.setTitle(response.body().getFullname().toUpperCase());

                    Glide.with(AdusumAccountActivity.this)
                            .load(AllConstants.IMAGE_URL + response.body().getProfile_picture())
                            .centerInside()
                            .placeholder(R.drawable.logo)
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
            case R.id.adusum_account_menu_item_delete_account:
                startActivity(new Intent(AdusumAccountActivity.this, DeleteAccountActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
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