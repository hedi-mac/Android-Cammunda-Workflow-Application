package tn.scolarite.isi.model;

public class DemandeVerification {

    private String nom;
    private String prenom;
    private String motif;
    private String niveau;

    public DemandeVerification() {
    }

    public DemandeVerification(String nom, String prenom, String motif, String niveau) {
        this.nom = nom;
        this.prenom = prenom;
        this.motif = motif;
        this.niveau = niveau;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMotif() {
        return motif;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

}
