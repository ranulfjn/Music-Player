package com.isi.songify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class customAdapter extends ArrayAdapter {
    Context context;
    ArrayList<CustomArrayList>listArray;


    public customAdapter(@NonNull Context context, ArrayList<CustomArrayList>listArray) {
        super(context, R.layout.customadp);

        this.context=context;
        this.listArray=listArray;
    }

    @Override
    public int getCount() {
        return listArray.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.customadp,parent,false);
        TextView name=rowView.findViewById(R.id.tv1);
        name.setText(listArray.get(position).getSongTitle());
        TextView des=rowView.findViewById(R.id.tv2);
        des.setText(listArray.get(position).getArtist());
        ImageView image=rowView.findViewById(R.id.image);
        image.setImageBitmap(listArray.get(position).getImage());
        return rowView;
    }

}
