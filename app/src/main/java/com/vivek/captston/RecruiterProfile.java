package com.vivek.captston;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class RecruiterProfile extends AppCompatActivity
{
     FirebaseAuth mAuth;
     DatabaseReference mDatabase, msubref;
     TextView name, mail, aadhaar, mobile, state, city, address;
     CircleImageView profile;

     @Override
     protected void onCreate(Bundle savedInstanceState)
     {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_recruiterprofile);

	  name = (TextView)findViewById(R.id.name_recruiter);
	  mail = (TextView)findViewById(R.id.mail_recruiter);
	  address = (TextView)findViewById(R.id.recruiter_address);
	  aadhaar = (TextView)findViewById(R.id.recruiter_aadhaar);
	  city = (TextView)findViewById(R.id.recruiter_city);
	  mobile = (TextView)findViewById(R.id.recruiter_mobile);
	  state = (TextView)findViewById(R.id.recruiter_state);
	  profile = (CircleImageView)findViewById(R.id.profile_image);

	  mDatabase = FirebaseDatabase.getInstance().getReference();
	  mAuth = FirebaseAuth.getInstance();

	  Toast.makeText(this , "Test In Profile" , Toast.LENGTH_SHORT).show();
	  retrieve();

	  profile.setOnClickListener(new View.OnClickListener()
	  {
	       @Override
	       public void onClick(View v)
	       {
		    ActivityOptionsCompat actop = ActivityOptionsCompat.makeSceneTransitionAnimation(RecruiterProfile.this , profile , ViewCompat.getTransitionName(profile));
		    startActivity(new Intent(RecruiterProfile.this , ShowImage.class));
	       }
	  });
     }

     public void retrieve()
     {
	  FirebaseUser user = mAuth.getCurrentUser();
	  msubref = mDatabase.child("user").child(user.getUid());
	  msubref.addListenerForSingleValueEvent(new ValueEventListener()
	  {
	       @Override
	       public void onDataChange(@NonNull DataSnapshot dataSnapshot)
	       {
		    name.setText(dataSnapshot.child("Name").getValue(String.class));
		    mail.setText(dataSnapshot.child("Email").getValue(String.class));
		    address.setText(dataSnapshot.child("Street number").getValue(String.class));
		    aadhaar.setText(dataSnapshot.child("Aadhar number").getValue(String.class));
		    city.setText(dataSnapshot.child("city").getValue(String.class));
		    mobile.setText(dataSnapshot.child("Contact number").getValue(String.class));
		    state.setText(dataSnapshot.child("State").getValue(String.class));
		    if(dataSnapshot.hasChild("urlToImage"))
		    {
			 Picasso.get().load(dataSnapshot.child("urlToImage").getValue().toString()).transform(new CropCircleTransformation()).into(profile);
		    }
	       }

	       @Override
	       public void onCancelled(@NonNull DatabaseError databaseError)
	       {

	       }
	  });
     }

     @Override
     public void onBackPressed()
     {
	  super.onBackPressed();
	  //finish();
     }

     public void edit_recruiter(View v)
     {
	  startActivity(new Intent(RecruiterProfile.this , RecruiterEdit.class));
     }
}
