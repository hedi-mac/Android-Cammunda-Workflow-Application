package tn.scolarite.isi.model;

public class CommentaireJustificatif extends DemandeVerification {

    private String justificatif;

    public CommentaireJustificatif(String nom, String prenom, String motif, String niveau, String justificatif) {
        super(nom, prenom, motif, niveau);
        this.justificatif = justificatif;
    }

    public String getJustificatif() {
        return justificatif;
    }

    public void setJustificatif(String justificatif) {
        this.justificatif = justificatif;
    }

}
