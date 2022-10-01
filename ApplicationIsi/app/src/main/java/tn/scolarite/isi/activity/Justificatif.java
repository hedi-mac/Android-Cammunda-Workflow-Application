package tn.scolarite.isi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import tn.scolarite.isi.service.DataService;
import tn.scolarite.isi.R;
import tn.scolarite.isi.model.DemandeVerification;

import org.json.JSONException;
import org.json.JSONObject;

public class Justificatif extends AppCompatActivity {

    public TextView tv_id;
    public TextView tv_name;
    public EditText edt_nom;
    public EditText edt_prenom;
    public EditText edt_motif;
    public EditText edt_niveau;
    public EditText edt_justificatif;
    public Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justificatif);
        tv_id = findViewById(R.id.tv_id_just);
        tv_name = findViewById(R.id.tv_name_just);
        edt_nom = findViewById(R.id.edt_nom_just);
        edt_prenom = findViewById(R.id.edt_prenom_just);
        edt_motif = findViewById(R.id.edt_motif_just);
        edt_niveau = findViewById(R.id.edt_niveau_just);
        edt_justificatif = findViewById(R.id.edt_justificatif);
        btn_submit = findViewById(R.id.btn_submit_just);
        String id = getIntent().getStringExtra("id").toString();
        String name = getIntent().getStringExtra("name").toString();
        tv_id.setText(id);
        tv_name.setText(name);
        edt_nom.setEnabled(false);
        edt_prenom.setEnabled(false);
        edt_motif.setEnabled(false);
        edt_niveau.setEnabled(false);

        final DataService dataService = new DataService(Justificatif.this);
        dataService.getDemandeVerificationFormData(new DataService.GetDemandeVerificationFormDataListener() {
            @Override
            public void onError(String message) {
            }
            @Override
            public void onResponse(DemandeVerification demandeVerification) {
                edt_nom.setText(demandeVerification.getNom());
                edt_prenom.setText(demandeVerification.getPrenom());
                edt_niveau.setText(demandeVerification.getNiveau());
                edt_motif.setText(demandeVerification.getMotif());
            }
        }, id);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonBody = null;
                try {
                    jsonBody = new JSONObject("{\n" +
                            "  \"variables\": {\n" +
                            "    \"justificatif\": {\n" +
                            "      \"value\": \""+edt_justificatif.getText()+"\",\n" +
                            "      \"type\": \"String\",\n" +
                            "      \"valueInfo\": {}\n" +
                            "    }\n" +
                            "  }\n" +
                            "}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String requestBody = jsonBody.toString();

                dataService.postForm(new DataService.SubmitListener() {
                    @Override
                    public void onError(String message) {
                        tv_id.setText(message);
                    }
                    @Override
                    public void onResponse() {

                    }
                }, requestBody, id);
                AlertDialog.Builder adb = new AlertDialog.Builder(Justificatif.this);
                adb.setTitle("Success");
                adb.setMessage("Commentaire ajouter avec succes . . .");
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Justificatif.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                adb.show();
            }
        });

    }
}