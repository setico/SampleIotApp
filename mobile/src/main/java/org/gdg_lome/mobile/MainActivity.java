package org.gdg_lome.mobile;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private static final String LAMPE_1 = "lampe1";
    private boolean state = false;
    @BindView(R2.id.btn_activer) Button btnActiver;
    @BindView(R2.id.toolbar) Toolbar toolbar;
    @BindView(R2.id.icon) ImageView icon;
    @BindString(R2.string.state_off) String stateOff;
    @BindString(R2.string.state_on) String stateOn;
    @BindDrawable(R2.drawable.ic_wb_sunny_white_48dp)
    Drawable lampeOn;
    @BindDrawable(R2.drawable.ic_brightness_1_white_48dp)
    Drawable lampeOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        firebaseDatabase.getReference().child(LAMPE_1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                state = dataSnapshot.getValue(Boolean.class);
                if(state) {
                    btnActiver.setText(stateOff);
                    icon.setImageDrawable(lampeOn);
                }
                else{
                    btnActiver.setText(stateOn);
                    icon.setImageDrawable(lampeOff);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnActiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase.getReference().child(LAMPE_1).setValue(!state);
            }
        });

    }
}
