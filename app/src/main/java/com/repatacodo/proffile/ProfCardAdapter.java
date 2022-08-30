package com.repatacodo.proffile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class ProfCardAdapter extends RecyclerView.Adapter<ProfCardAdapter.ViewHolder>{

    //the data the adapter will work with
    byte[][] picture;
    String[] teacherType;
    String[] name;
    String[] nickname;
    String[] subject;
    boolean[] recitation;
    private Listener listener;

    interface Listener{
        void onClick(int position);
    }

    public void setListener (Listener listener){
        this.listener = listener;
    }


    public ProfCardAdapter (byte[][] picture,
            String[] teacherType,
            String[] name,
            String[] nickname,
            String[] subject,
            boolean[] recitation){

        this.picture = picture;
        this.teacherType = teacherType;
        this.name = name;
        this.nickname = nickname;
        this.subject = subject;
        this.recitation = recitation;

    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    @NonNull
    @Override
    public ProfCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Code to instantiate the ViewHolder
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.prof_listitem, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.profImage);
        if (picture[position] != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(picture[position], 0, picture[position].length);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, /*imageView.getWidth() - TODO: optimal is to use these code, but idk how without throwing an error cuz the view isn't done adjutsing yet, therefore returns zero which causes an error*/2000, /*imageView.getHeight()*/2000, false));
        }

        TextView txt_teacherType = (TextView) cardView.findViewById(R.id.txt_value_teacher_type);
        txt_teacherType.setText(teacherType[position]);

        TextView txt_fullname = (TextView) cardView.findViewById(R.id.txt_value_fullname);
        txt_fullname.setText(name[position]);

        TextView txt_nickname = (TextView) cardView.findViewById(R.id.txt_value_nickname);
        txt_nickname.setText(nickname[position]);

        TextView txt_recitation = (TextView) cardView.findViewById(R.id.txt_value_recitation);
        if (recitation[position]){
            txt_recitation.setText("Required");
        } else {
            txt_recitation.setText("Not Required");
        }

        TextView txt_subject = (TextView) cardView.findViewById(R.id.txt_value_subject);
        txt_subject.setText(subject[position]);

        //set Click functionality for recycler view items
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onClick(holder.getAdapterPosition());
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //viewHolder variable
        CardView cardView;

        //Define the view to be used for each data item
        public ViewHolder(CardView CV){
            super(CV);
            this.cardView = CV;
        }
    }
}