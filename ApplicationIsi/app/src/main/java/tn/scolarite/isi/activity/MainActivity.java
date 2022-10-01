package tn.scolarite.isi.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

import tn.scolarite.isi.service.DataService;
import tn.scolarite.isi.R;

public class MainActivity extends AppCompatActivity
{

	public ListView lv;
	public TextView tv_nbr;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		final DataService dataService = new DataService(MainActivity.this);
		lv = findViewById( R.id.lv );
		tv_nbr = findViewById(R.id.tv_nbr);
		ArrayList<HashMap<String, String>> listItem;

		dataService.getTasks( new DataService.GetTasksListener()
		{

			@Override
			public void onError( String message )
			{
				Toast.makeText( MainActivity.this, "Error : "+message.toString(), Toast.LENGTH_SHORT ).show();
			}

			@Override
			public void onResponse(ArrayList<HashMap<String, String>> list)
			{
				tv_nbr.setText(list.size()+" ");
				SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list, R.layout.task_list_item,
						new String[] {"date", "id", "name"}, new int[] {R.id.tv_date, R.id.tv_id, R.id.tv_name});

				lv.setAdapter( adapter );
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> a, View v, int position, long id) {
						HashMap<String, String> map = (HashMap<String, String>) lv.getItemAtPosition(position);
						if(map.get("taskDefinitionKey").equals("verif_dmd")) {
							Intent intent = new Intent(MainActivity.this, VerificationDemande.class);
							intent.putExtra("name", map.get("name"));
							intent.putExtra("id", map.get("id"));
							startActivity(intent);
						}
						else if(map.get("taskDefinitionKey").equals("ajout_cmt_justif")||map.get("taskDefinitionKey").equals("prep_attestattion")) {
							Intent intent = new Intent(MainActivity.this, Justificatif.class);
							intent.putExtra("name", map.get("name"));
							intent.putExtra("id", map.get("id"));
							startActivity(intent);
						}
					}
				});

			}
		} );







	}

}