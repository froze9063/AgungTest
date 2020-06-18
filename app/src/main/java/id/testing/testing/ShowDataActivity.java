package id.testing.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity implements MyInterface {

    private RecyclerView rvData;
    private ShowDataAdapter showDataAdapter;
    private ArrayList<QuestionModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        rvData = findViewById(R.id.rvData);

        list = new ArrayList<>();
        rvData.setLayoutManager(new LinearLayoutManager(this));

        showDataAdapter = new ShowDataAdapter(this,list,this);
        rvData.setAdapter(showDataAdapter);

        loadData(0,"",true);
    }

    private void loadData(int type,String fromid, Boolean isFirst){
        FirebaseFirestore.getInstance().collection("Question")
                .whereEqualTo("type",type)
                .whereEqualTo("first",isFirst)
                .whereEqualTo("fromID",fromid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot qSnap = task.getResult();

                            if (qSnap != null){
                                List<DocumentSnapshot> mydocss= qSnap.getDocuments();

                                for (DocumentSnapshot item : mydocss ){
                                     String id = item.getString("id");
                                     Boolean first = item.getBoolean("first");
                                     String fromID = item.getString("fromID");
                                     int type = Integer.parseInt(String.valueOf(((long) item.get("type"))));
                                     String question = item.getString("question");
                                     Boolean isClicked = item.getBoolean("isClicked");

                                     QuestionModel questionModel = new QuestionModel(id,first,fromID,type,question,isClicked);

                                    list.add(questionModel);
                                }

                                showDataAdapter.updateList(list);
                            }

                        }
                        else{
                            Log.d("load_data","gagal "+task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(int from, String fromId, String text, int position) {
        loadData(from,fromId,false);
    }
}