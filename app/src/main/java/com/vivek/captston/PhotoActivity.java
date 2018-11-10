package com.vivek.captston;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener
{
     ImageView uploadimgview;
     private static final int SELECT_PICTURE = 100;
     Uri filepath;
     private FirebaseAuth mAuth;
     private ProgressDialog pd;
     private DatabaseReference rdatabase;
     private String ruserid;
     private FirebaseStorage rstorage;
     private StorageReference rstorageReference;
     private DatabaseReference msubref;


     @Override
     protected void onCreate(Bundle savedInstanceState)
     {
	  super.onCreate(savedInstanceState);
	  setTitle("Select Image");
	  setContentView(R.layout.activity_photo);
	  findViewById(R.id.button_chooseimg).setOnClickListener(this);
	  findViewById(R.id.button_uploadimg).setOnClickListener(this);
	  uploadimgview = (ImageView)findViewById(R.id.uploadimgview);
	  pd = new ProgressDialog(this);
	  mAuth = FirebaseAuth.getInstance();
	  rstorage = FirebaseStorage.getInstance();
	  ruserid = mAuth.getCurrentUser().getUid().toString();
	  rdatabase = FirebaseDatabase.getInstance().getReference("user").child(ruserid);
	  msubref = FirebaseDatabase.getInstance().getReference("seeker");
	  rstorageReference = rstorage.getReference().child("Images").child("RecruiterImages");


     }


     public void chooseImage()
     {

	  Intent intent = new Intent();
	  intent.setType("image/*");
	  intent.setAction(Intent.ACTION_GET_CONTENT);
	  startActivityForResult(Intent.createChooser(intent , "select picture") , SELECT_PICTURE);

     }


     public void uploadImage()
     {
	  if(filepath != null)
	  {
	       final ProgressDialog pd = new ProgressDialog(this);
	       pd.setTitle("Uploading....");
	       pd.show();

	       StorageReference ref = rstorageReference.child(mAuth.getCurrentUser().getUid().toString());
	       ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
	       {
		    @Override
		    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
		    {


			 Handler handler = new Handler();
			 handler.postDelayed(new Runnable()
			 {
			      @Override
			      public void run()
			      {
				   pd.dismiss();
			      }
			 } , 500);
			 String urlToImage = taskSnapshot.getDownloadUrl().toString();
			 rdatabase.child("urlToImage").setValue(urlToImage);

			 Picasso.get().load(urlToImage).transform(new CropCircleTransformation()).into(uploadimgview);

			 Toast.makeText(getApplicationContext() , "Uploaded" , Toast.LENGTH_SHORT).show();
		    }
	       }).addOnFailureListener(new OnFailureListener()
	       {
		    @Override
		    public void onFailure(@NonNull Exception e)
		    {
			 pd.dismiss();
			 Toast.makeText(getApplication() , "Uploading failed" + e.getMessage() , Toast.LENGTH_SHORT).show();
		    }
	       }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
	       {
		    @Override
		    public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
		    {
			 double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
			 pd.setMessage("Uploaded" + (int)progress + "%");
		    }
	       });


	  }
	  else
	  {
	       Toast.makeText(getApplication() , "No file selected" , Toast.LENGTH_SHORT).show();
	  }


     }

     public void onActivityResult(int requestcode , int resultcode , Intent data)
     {
	  if(resultcode == RESULT_OK)
	  {
	       if(requestcode == SELECT_PICTURE)
	       {
		    filepath = data.getData();

		    if(filepath != null)
		    {
			 Picasso.get().load(filepath).transform(new CropCircleTransformation()).into(uploadimgview);
		    }
	       }
	  }
     }

     @Override
     public void onClick(View v)
     {
	  int i = v.getId();
	  switch(i)
	  {

	       case R.id.button_chooseimg:
		    chooseImage();
		    break;
	       case R.id.button_uploadimg:
		    uploadImage();
		    break;
	       default:
		    break;
	  }
     }
}
