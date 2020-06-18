package id.testing.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Batch;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyInterface, View.OnClickListener{

    private MyAdapter myAdapter;
    private ArrayList<QuestionModel> list;

    private RecyclerView rv;

    private Button btnTambah;
    private Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
    }

    private void setView(){
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list,this);

        rv = findViewById(R.id.rv);
        btnTambah = findViewById(R.id.btnTambah);
        btnShow = findViewById(R.id.btnShow);

        rv.setLayoutManager(new LinearLayoutManager((this)));
        rv.setAdapter(myAdapter);

        initiateFirstList();

        btnTambah.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnShow.setOnClickListener(this);
    }

    private void initiateFirstList(){
        String id = FirebaseFirestore.getInstance().collection("Question").document().getId();
        QuestionModel questionModel = new QuestionModel(id,true,"",0,"",
                false);
        list.add(questionModel);
        myAdapter.updateList(list);
    }

    private void tambahList(String fromID, int type){
        String id = FirebaseFirestore.getInstance().collection("Question").document().getId();
        QuestionModel questionModel = new QuestionModel(id,false,fromID,type,"",false);
        list.add(questionModel);
        myAdapter.updateList(list);
    }

    private void postDatabase(){
        CollectionReference cRef = FirebaseFirestore.getInstance().collection("Question");
        WriteBatch batch = FirebaseFirestore.getInstance().batch();

        for (int i = 0 ; i < list.size() ; i++){
            QuestionModel questionModel = list.get(i);
            DocumentReference docRef = cRef.document(questionModel.getId());
            batch.set(docRef,questionModel);
        }

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("batch_error","Data updated");
                }
                else{
                    Log.d("batch_error",task.getException().getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void onClick(int from, String fromId, String text, int position) {
        QuestionModel questionModel = list.get(position);
        questionModel.setQuestion(text);
        questionModel.setClicked(true);
        list.set(position,questionModel);

        Boolean isFound = false;

        for (int i = 0; i < list.size(); i++){
            QuestionModel qMods = list.get(i);
            if (qMods.getFromID().equals(fromId) && qMods.getType() == from){
                isFound = true;
            }
        }

        if (!isFound){
            tambahList(fromId,from);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTambah :
                postDatabase();
            break;
            case R.id.btnShow :
                startActivity(new Intent(MainActivity.this,ShowDataActivity.class));
                break;
        }
    }
}