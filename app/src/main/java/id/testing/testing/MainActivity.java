package id.testing.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyInterface {

    private MyAdapter myAdapter;
    private ArrayList<QuestionModel> list;

    private RecyclerView rv;
    private Button btnTambah;

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

        rv.setLayoutManager(new LinearLayoutManager((this)));
        rv.setAdapter(myAdapter);

        initiateFirstList();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
        QuestionModel questionModel = new QuestionModel(id,true,fromID,type,"",false);
        list.add(questionModel);
        myAdapter.updateList(list);
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
}