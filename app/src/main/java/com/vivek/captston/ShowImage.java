package com.vivek.captston;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ShowImage extends AppCompatActivity
{
     android.support.v7.widget.Toolbar toolbar;
     DatabaseReference mDatabase;
     ImageView iv;

     @Override
     protected void onCreate(Bundle savedInstanceState)
     {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_show_image);

	  toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_showimage);
	  iv = (ImageView) findViewById(R.id.show_image);
	  mDatabase = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

	  setTitle("Profile Photo");
	  setSupportActionBar(toolbar);
	  toolbar.setTitleTextColor(Color.WHITE);
	  toolbar.setBackgroundColor(Color.BLACK);

	  getSupportActionBar().setHomeButtonEnabled(true);
	  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	  mDatabase.addValueEventListener(new ValueEventListener()
	  {
	       @Override
	       public void onDataChange(DataSnapshot dataSnapshot)
	       {
		    if(dataSnapshot.hasChild("urlToImage"))
		    {
			 Picasso.get().load(dataSnapshot.child("urlToImage").getValue().toString()).transform(new CropCircleTransformation()).into(iv);
		    }
	       }

	       @Override
	       public void onCancelled(DatabaseError databaseError)
	       {

	       }
	  });
     }
}
