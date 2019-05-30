package org.dgonzalo.m8_uf1_recuperacio;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddComentari extends Fragment {

    private addComentarioListener mListener;
    EditText add_comentari_text, add_recomanada;
    Button add_comentari;
    public AddComentari() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_add_comentari, container, false);
        add_comentari_text = vista.findViewById(R.id.comentari_input);
        add_recomanada = vista.findViewById(R.id.recomanada_input);
        add_comentari = vista.findViewById(R.id.add_comentari);

        add_comentari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.añadirComentario(add_comentari_text.getText().toString(), add_recomanada.getText().toString());
            }
        });


        return vista;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof addComentarioListener) {
            mListener = (addComentarioListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement addComentarioListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface addComentarioListener {
        void añadirComentario(String comentari, String recomanada);
    }
}