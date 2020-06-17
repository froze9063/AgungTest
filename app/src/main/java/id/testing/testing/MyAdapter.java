package id.testing.testing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterHolder>{

    private Context context;
    private ArrayList<QuestionModel> list;
    private MyInterface myInterface;

    public MyAdapter(Context context, ArrayList<QuestionModel> list, MyInterface myInterface){
        this.context = context;
        this.list = list;
        this.myInterface = myInterface;
    }

    @NonNull
    @Override
    public MyAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_adapter,parent,false);
        return new MyAdapterHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyAdapterHolder holder, final int position) {

        holder.txtQuestion.setText("Pertanyaan "+(position + 1));

        final QuestionModel qModel = list.get(position);
        final String modelID = qModel.getId();
        final String text = holder.etQuestion.getText().toString().trim();

        holder.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myInterface.onClick(1,modelID,text,position);
            }
        });

        holder.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myInterface.onClick(2,modelID,text,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(ArrayList<QuestionModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    class MyAdapterHolder extends RecyclerView.ViewHolder {

        TextView txtQuestion;
        EditText etQuestion;
        Button btnNo;
        Button btnYes;

        public MyAdapterHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            etQuestion = itemView.findViewById(R.id.etQuestion);
            btnNo = itemView.findViewById(R.id.btnNo);
            btnYes = itemView.findViewById(R.id.btnYes);
        }
    }
}
