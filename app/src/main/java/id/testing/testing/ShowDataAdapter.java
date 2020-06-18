package id.testing.testing;

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

public class ShowDataAdapter extends RecyclerView.Adapter<ShowDataAdapter.ShowDataAdapterHolder>{

    private Context context;
    private ArrayList<QuestionModel> list;
    private MyInterface myInterface;

    public ShowDataAdapter(Context context, ArrayList<QuestionModel> list, MyInterface myInterface){
        this.context = context;
        this.list = list;
        this.myInterface = myInterface;
    }

    @NonNull
    @Override
    public ShowDataAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data,parent,false);
        return new ShowDataAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDataAdapterHolder holder, final int position) {
        final QuestionModel qModel = list.get(position);
        holder.txtQuestion.setText(qModel.getQuestion());

        holder.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myInterface.onClick(1,qModel.getId(),"",position);
            }
        });

        holder.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myInterface.onClick(2,qModel.getId(),"",position);
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

    class ShowDataAdapterHolder extends RecyclerView.ViewHolder {

        TextView txtQuestion;
        Button btnNo;
        Button btnYes;

        public ShowDataAdapterHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            btnNo = itemView.findViewById(R.id.btnNo);
            btnYes = itemView.findViewById(R.id.btnYes);
        }
    }
}
