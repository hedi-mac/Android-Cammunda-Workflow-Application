package tn.scolarite.isi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import tn.scolarite.isi.service.DataService;
import tn.scolarite.isi.R;
import tn.scolarite.isi.model.DemandeVerification;

import org.json.JSONException;
import org.json.JSONObject;

public class VerificationDemande extends AppCompatActivity {

    public TextView tv_id;
    public TextView tv_name;
    public EditText edt_nom;
    public EditText edt_prenom;
    public EditText edt_motif;
    public EditText edt_niveau;
    public CheckBox chk_valider;
    public Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_demande);
        tv_id = findViewById(R.id.tv_id);
        tv_name = findViewById(R.id.tv_name);
        edt_nom = findViewById(R.id.edt_nom);
        edt_prenom = findViewById(R.id.edt_prenom);
        edt_motif = findViewById(R.id.edt_motif);
        edt_niveau = findViewById(R.id.edt_niveau);
        chk_valider = findViewById(R.id.chk_valider);
        btn_submit = findViewById(R.id.btn_submit);
        edt_nom.setEnabled(false);
        edt_prenom.setEnabled(false);
        edt_motif.setEnabled(false);
        edt_niveau.setEnabled(false);
        
        String id = getIntent().getStringExtra("id").toString();
        String name = getIntent().getStringExtra("name").toString();
        tv_id.setText(id);
        tv_name.setText(name);
        final DataService dataService = new DataService(VerificationDemande.this);
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
                String checked = "false";
                if(chk_valider.isChecked())
                    checked = "true";
                JSONObject jsonBody = null;
                try {
                    jsonBody = new JSONObject("{\n" +
                            "  \"variables\": {\n" +
                            "    \"valid\": {\n" +
                            "      \"value\": "+checked+",\n" +
                            "      \"type\": \"Boolean\",\n" +
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
                    }
                    @Override
                    public void onResponse() {

                    }
                }, requestBody, id);
                Intent intent = new Intent(VerificationDemande.this, MainActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

    }




}