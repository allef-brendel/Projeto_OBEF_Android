package com.example.obef.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.obef.Gerenciamento.Gravador;
import com.example.obef.R;

public class FragmentPontos extends Fragment {

	private Gravador gravador;
	private TextView tv;
	private TextView tv2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.layout_frag_pontos, null);

		gravador = new Gravador();

		tv = view.findViewById(R.id.pontosDia);
		tv2 = view.findViewById(R.id.pontosTotais);

		tv.setText("" + gravador.lerPontos());
		tv2.setText("" + gravador.lerPontosTotais());

		return(view);
	}
}
