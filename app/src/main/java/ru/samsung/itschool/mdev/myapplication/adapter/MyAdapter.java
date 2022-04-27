package ru.samsung.itschool.mdev.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;

import java.util.ArrayList;

import ru.samsung.itschool.mdev.myapplication.R;
import ru.samsung.itschool.mdev.myapplication.model.Anekdot;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewRow> {

    private ArrayList<Anekdot> anekdots;

    public MyAdapter(ArrayList<Anekdot> anekdots) {
        this.anekdots = anekdots;
    }

    @NonNull
    @Override
    public ViewRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new ViewRow(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewRow holder, int position) {

        holder.tv.setText(html2text(anekdots.get(position).getElementPureHtml()));
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    @Override
    public int getItemCount() {
        return anekdots != null ? anekdots.size() : 0;
    }

    public class ViewRow extends RecyclerView.ViewHolder {

        private TextView tv;

        public ViewRow(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textView);
        }
    }
}
