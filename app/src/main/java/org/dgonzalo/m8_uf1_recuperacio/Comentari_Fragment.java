package org.dgonzalo.m8_uf1_recuperacio;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class Comentari_Fragment extends Fragment {

    private ComentariListener mListener;

    Button add_comentari_button;
    RecyclerView recyclerView;
    miAdapter adapter;
    ArrayList<Comentari> comentaris;


    public Comentari_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_comentari_, container, false);
        comentaris = new ArrayList<Comentari>();
        recyclerView = vista.findViewById(R.id.recycler_view);
        adapter = new miAdapter(comentaris);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        add_comentari_button = vista.findViewById(R.id.add_comentario_button);
        add_comentari_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.add_comentari_view();
            }
        });

        return vista;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComentariListener) {
            mListener = (ComentariListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RegisterListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface ComentariListener {
        void add_comentari_view();

    }

    public void addComentari(Comentari comentari){
        comentaris.add(comentari);
        adapter.notifyDataSetChanged();
    }


    class miAdapter extends RecyclerView.Adapter<miAdapter.miViewHolder>{

        ArrayList<Comentari> comentaris;
        public miAdapter(ArrayList<Comentari> comentaris){
            this.comentaris = comentaris;
        }

        @NonNull
        @Override
        public miViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mi_view_holder,viewGroup,false);
            return new miViewHolder(vista);
        }

        @Override
        public void onBindViewHolder(@NonNull miViewHolder miViewHolder, int i) {
            miViewHolder.comentari.setText(comentaris.get(i).getComentari());
            miViewHolder.recomanada.setText(comentaris.get(i).getRecomenada());
        }

        @Override
        public int getItemCount() {
            return comentaris.size();
        }

        class miViewHolder extends RecyclerView.ViewHolder{
            TextView comentari, recomanada;

            public miViewHolder(@NonNull View itemView) {
                super(itemView);
                recomanada = itemView.findViewById(R.id.recomendada_dato);
                comentari = itemView.findViewById(R.id.comentario_dato);

            }
        }
    }
}