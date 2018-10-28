package com.vivek.captston;

//import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
	import android.os.Bundle;
	import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
	import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
	import android.widget.TextView;
import android.widget.Toast;

	import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
	import com.squareup.picasso.Picasso;

	import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class Recruiter extends AppCompatActivity {
    private DrawerLayout m;
    private ActionBarDrawerToggle mt;
    NavigationView navigation;
    private FirebaseAuth mAuth;
    private DatabaseReference rdatabase;
    private String ruserid;
    private ProgressDialog pd;


    CardView electrician1, plumber1, bricklayer1, labour1, painter1, carpainter1;
    CircleImageView headerpic1 , profile;
    TextView header_name,header_mail_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Home");
        setContentView(R.layout.activity_recruiter);
        m = (DrawerLayout) findViewById(R.id.drawer);
        navigation = (NavigationView) findViewById(R.id.navigationView);
        View hview=navigation.getHeaderView(0);
        headerpic1 = (CircleImageView)hview.findViewById(R.id.headerpic);
        electrician1 = (CardView) findViewById(R.id.electrician);
        plumber1 = (CardView) findViewById(R.id.plumber);
        bricklayer1 = (CardView) findViewById(R.id.bricklayer);
        labour1 = (CardView) findViewById(R.id.labour);
        painter1 = (CardView) findViewById(R.id.painter);
        header_name=(TextView)hview.findViewById(R.id.header_name);
        header_mail_id=(TextView)hview.findViewById(R.id.header_mail_id);
        carpainter1 = (CardView) findViewById(R.id.carpainter);
        profile = (CircleImageView)findViewById(R.id.profile_image) ;
        mAuth=FirebaseAuth.getInstance();
        ruserid=mAuth.getCurrentUser().getUid().toString();
        rdatabase= FirebaseDatabase.getInstance().getReference("user").child(ruserid);




       pd=new ProgressDialog(this);


        mt = new ActionBarDrawerToggle(this, m, R.string.open, R.string.close);

        m.addDrawerListener(mt);
        mt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int k = item.getItemId();
                switch (k) {
                    case R.id.addEmployee_Recruiter:
                        Toast.makeText(getApplicationContext(), "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.showEmployee_Recruiter:
                        Toast.makeText(getApplicationContext(), "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Contact_Recruiter:
                        Toast.makeText(getApplicationContext(), "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Help_Recruiter:
                        Toast.makeText(getApplicationContext(), "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout_Recruiter:
                        logout();
                        break;
                    case R.id.profile_recruiter:
                        Intent intent=new Intent(Recruiter.this,RecruiterProfile.class);
			 ActivityOptionsCompat actop = ActivityOptionsCompat.makeSceneTransitionAnimation(Recruiter.this , headerpic1 , ViewCompat.getTransitionName(headerpic1));
                        startActivity((intent) , actop.toBundle());

                        default:
                        return false;
                }
                return true;
            }
        });

        rdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                header_name.setText(dataSnapshot.child("Name").getValue().toString());
                header_mail_id.setText(dataSnapshot.child("Email").getValue().toString());

                if(dataSnapshot.hasChild("urlToImage")){
                    Picasso.get().load(dataSnapshot.child("urlToImage").getValue().toString()).transform(new CropCircleTransformation()).into(headerpic1);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mt.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void dothis(View v)
    {
        SharedPreferences sharedPreferences= getSharedPreferences("Categories", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String categorie;
        String img_var;
        Intent intent;
        switch(v.getId())
        {

            case R.id.electrician:
                Toast.makeText(getApplicationContext(),"Electrician",Toast.LENGTH_SHORT).show();
                categorie="Electrician";
                img_var="1";
                editor.putString("categorie",categorie);
                editor.putString("imgvar",img_var);
                editor.commit();

                intent=new Intent(getApplicationContext(),GetLocation.class);
                startActivity(intent);

                break;
            case R.id.carpainter:
                Toast.makeText(getApplicationContext(),"Carpainter",Toast.LENGTH_SHORT).show();
                categorie="Carpainter";
                img_var="2";
                editor.putString("categorie",categorie);
                editor.putString("imgvar",img_var);
                editor.commit();
                intent=new Intent(this,GetLocation.class);
                startActivity(intent);
                break;
            case R.id.plumber:
                Toast.makeText(getApplicationContext(),"Plumber",Toast.LENGTH_SHORT).show();
                categorie="Plumber";
                img_var="3";
                editor.putString("categorie",categorie);
                editor.putString("imgvar",img_var);
                editor.commit();
                intent=new Intent(getApplicationContext(),GetLocation.class);
                startActivity(intent);
                break;
            case R.id.bricklayer:
                Toast.makeText(getApplicationContext(),"BrickLayer",Toast.LENGTH_SHORT).show();
                categorie="Bricklayer";
                img_var="4";
                editor.putString("categorie",categorie);
                editor.putString("imgvar",img_var);
                editor.commit();
                intent=new Intent(getApplicationContext(),GetLocation.class);
                startActivity(intent);
                break;
            case R.id.painter:
                Toast.makeText(getApplicationContext(),"Painter",Toast.LENGTH_SHORT).show();
                categorie="Painter";
                img_var="5";
                editor.putString("categorie",categorie);
                editor.putString("imgvar",img_var);
                editor.commit();
                intent=new Intent(getApplicationContext(),GetLocation.class);
                startActivity(intent);
                break;
            case R.id.labour:
                Toast.makeText(getApplicationContext(),"Labour",Toast.LENGTH_SHORT).show();
                categorie="Labour";
                img_var="6";
                editor.putString("categorie",categorie);
                editor.putString("imgvar",img_var);
                editor.commit();
                intent=new Intent(getApplicationContext(),GetLocation.class);
                startActivity(intent);
                break;
            case R.id.headerpic:
            //Toast.makeText(getApplication(),"Image clicked",Toast.LENGTH_SHORT).show();
           // Toast.makeText(getApplicationContext(),"click"+item.getTitle(),Toast.LENGTH_SHORT).show();
            Intent h=new Intent(Recruiter.this,PhotoActivity.class);
            startActivity(h);
            break;
            default:
                break;
        }
    }

    public void logout() {
        pd.setTitle("Logging out");
        pd.show();
        mAuth.signOut();
        finish();
        startActivity(new Intent(Recruiter.this,Login.class));
    }










}
