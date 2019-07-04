package com.example.obef.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.obef.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentRanking extends Fragment {

	private TextView tv;
	DatabaseReference firebase;
	private ListView listView;
	private ListView listView2;
	private ListView listView3;


	private String[] itens = {"","","","",""};
	private String[] itens2 = {"","","","",""};
	private String[] itens3 = {"","","","",""};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.layout_frag_ranking, null);

		listView = view.findViewById(R.id.listViewNomes);
		listView2 = view.findViewById(R.id.listViewScore);
		listView3 = view.findViewById(R.id.listViewNumeros);

		firebase= FirebaseDatabase.getInstance().getReference();

		//Query que ordena os acertos de todos os alunos cadastrados do maior para o menor.
		Query query=firebase.child("Acertos").orderByChild("pontos");

		query.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				final List<Integer> pontos=new ArrayList<Integer>();
				final List<String> ids=new ArrayList<String>();
				for(DataSnapshot d:dataSnapshot.getChildren()){
					ids.add(d.getKey());
					pontos.add(d.child("pontos").getValue(Integer.class));
				}
                /*
                Segundo Listener que pega o id da escola dos alunos e seu nome, assim como também
                pega o nome do aluno para então mostrar o rank por pontos
                */
				firebase.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						int cont=0;
						for(int x=pontos.size()-1;x>=0;x--){
							String idEscola=""+dataSnapshot.child("Alunos").child(ids.get(x)).child("idEscola").getValue(Integer.class).toString();
							String nomeAluno=dataSnapshot.child("Alunos").child(ids.get(x)).child("nome").getValue(String.class);
							String nomeEscola =dataSnapshot.child("Escolas").child(idEscola).child("nome").getValue(String.class);
							if(cont<5){
								itens[cont]=nomeAluno;
								itens2[cont]=""+pontos.get(x);
								itens3[cont] = (""+(++cont));
							}
						}

						ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.my_text_size, android.R.id.text1, itens);
						listView.setAdapter(adapter);

						ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),R.layout.my_text_size, android.R.id.text1, itens2);
						listView2.setAdapter(adapter2);

						ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(),R.layout.my_text_size, android.R.id.text1, itens3);
						listView3.setAdapter(adapter3);
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {

					}
				});
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});

		tv = view.findViewById(R.id.textViewRamking);
		
		return(view);
	}
}
