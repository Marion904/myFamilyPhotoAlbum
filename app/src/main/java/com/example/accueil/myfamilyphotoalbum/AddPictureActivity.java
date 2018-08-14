package com.example.accueil.myfamilyphotoalbum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accueil.myfamilyphotoalbum.Controller.Constants;
import com.example.accueil.myfamilyphotoalbum.model.Picture;
import com.example.accueil.myfamilyphotoalbum.model.PictureFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPictureActivity extends AppCompatActivity implements View.OnClickListener{

    //Firebase references
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    TextView contentType;


    //private StorageReference mStorageRef;
    private static final int PICK_IMAGE_REQUEST=255;
    private static final int REQUEST_IMAGE_CAPTURE = 234;

    ImageView showPhoto;
    private Uri imageUri;
    private ImageButton buttonTakePicture;
    private ImageButton buttonSelectFromGallery;
    private ImageView buttonUpload, buttonRotate;
    private String mCurrentPhotoPath;
    private StorageReference mStorageRef;
    private Picture mPicture;
    private EditText mEditTextCaption;
    private int i;
    private Date now;

    private String uploadId;
    private String caption;
    private String urlPic;
    private String idUser;
    private String displayName;
    public Bitmap bitmapOrg;

    PictureFactory mPicFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
        contentType=this.findViewById(R.id.contentType);
        buttonTakePicture = this.findViewById(R.id.cameraButton);
        buttonSelectFromGallery = this.findViewById(R.id.galery);
        buttonUpload = this.findViewById(R.id.buttonUpload);
        buttonRotate = this.findViewById(R.id.rotateButton);
        showPhoto = this.findViewById(R.id.imageView);
        mEditTextCaption = this.findViewById(R.id.contentTitle);
        contentType.setText(R.string.addPictureTitle);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);

        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase= database.getReference("contents");
        uploadId = mDatabase.push().getKey();

        buttonTakePicture.setOnClickListener(this);
        buttonSelectFromGallery.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        buttonRotate.setOnClickListener(this);

        buttonRotate.setClickable(false);

        i = (int) (new Date().getTime()/1000);
        now = new Date();
        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        mPicFactory = new PictureFactory();
        mPicture = mPicFactory.createPicture();
        mPicture.setOwner(idUser);
        mPicture.setId(uploadId);
        mPicture.setDate(now);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            finish();
            startActivity(new Intent(AddPictureActivity.this,WelcomeActivity.class));

        } else {

        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        menu.findItem(R.id.addContent).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }




    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.album:
                startActivity(new Intent(AddPictureActivity.this,MainActivity.class));
                break;
            case R.id.myProfile :
                startActivity(new Intent(AddPictureActivity.this,MyProfileActivity.class));
                break;
            case R.id.logOut:
                mAuth.signOut();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openGallery() {
        Intent gallery = new Intent();

        gallery.setType("image/*");

        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /**Ensure there is a camera activity to handle the Intent*/
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            //Create the file where the photo should go

            File photoFile=null;
            try {
                photoFile = createImageFile();
                //photoFile = new File(getFilesDir(),"Pictures");
                //photoFile.mkdirs();
                //File file = new File(photoFile,"pic");


            } catch (IOException ex) {
                //Error occurred while creating the File
                throw new RuntimeException("Error generating file", ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                try{
                    imageUri = FileProvider.getUriForFile(AddPictureActivity.this,"com.example.accueil.myfamilyphotoalbum.fileprovider", photoFile);
                }catch(IllegalArgumentException ie){
                    throw new RuntimeException("Error getting the Uri",ie);
                }

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            }


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        buttonRotate.setClickable(true);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            imageUri = data.getData();

            try {
                bitmapOrg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmapOrg = getResizedBitmap(bitmapOrg,300,300);
                showPhoto.setImageBitmap(bitmapOrg);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            imageUri = data.getData();
            Bundle extras = data.getExtras();

            bitmapOrg = (Bitmap) extras.get("data");
            bitmapOrg = getResizedBitmap(bitmapOrg,300,300);
            showPhoto.setImageBitmap(bitmapOrg);


            galleryAddPic();
            return;
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String sdf = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + sdf + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        //File image = new File(Environment.getExternalStorageDirectory(),  imageFileName);
        //return photo;
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    private void uploadFile(){
/**
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Chargement en cours...");
        progressDialog.show();
**/
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference picRef = mStorageRef.child(Constants.STORAGE_PATH_UPLOADS + uploadId );
        UploadTask uploadTask = picRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AddPictureActivity.this, (CharSequence) exception,Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //progressDialog.dismiss();

                Toast.makeText(AddPictureActivity.this, R.string.uploadOk, Toast.LENGTH_LONG).show();

                //adding an upload to firebase database
                urlPic = taskSnapshot.getDownloadUrl().toString();
                mPicture.setUrl(urlPic);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                //progressDialog.setMessage("Uploaded :" + (int) progress + "%");
            }
        });


    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        float scale = Math.min(scaleHeight,scaleWidth);
        matrix.postScale(scale, scale);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public Bitmap rotateBitmap(){
        Matrix matrix = new Matrix();
        matrix.setRotate(Constants.BITMAP_ROTATE);
        bitmapOrg = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapOrg.getWidth(),bitmapOrg.getHeight(), matrix, true);
        showPhoto.setImageBitmap(bitmapOrg);
        return bitmapOrg;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
            else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }
    @Override
    public void onClick(View v) {
        if (v == buttonTakePicture) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.CAMERA)) {
                    Toast.makeText(this, R.string.cameraNeeded, Toast.LENGTH_SHORT).show();
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.CAMERA},
                            Constants.MY_REQUEST_CODE);
                }
            } else {
                dispatchTakePictureIntent();
            }
        }
        if (v == buttonSelectFromGallery) {
            openGallery();
        }

        if (v == buttonRotate) {
            rotateBitmap();
        }
        if (v == buttonUpload) {
            caption = mEditTextCaption.getText().toString().trim();
            mPicture.setCaption(caption);
            uploadFile();
            mDatabase.child(mPicture.getOwner()).child(mPicture.toString()).child(mPicture.getId()).setValue(mPicture);
            mEditTextCaption.setEnabled(false);

        }
    }
}
