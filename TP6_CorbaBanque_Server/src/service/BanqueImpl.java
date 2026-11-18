
package service;

import corbaBanque.*;

public class BanqueImpl extends IBanqueRemotePOA {

    private Compte[] comptes = new Compte[100];
    private int nombreComptes = 0;

    @Override
    public void creerCompte(Compte cpte) {
        comptes[nombreComptes] = cpte;
        nombreComptes++;
        System.out.println("Compte cree : " + cpte.code);
    }

    @Override
    public void verser(float mt, int code) {
        for (int i = 0; i < nombreComptes; i++) {
            if (comptes[i].code == code) {
                comptes[i].solde += mt;
                System.out.println("Versement effectue.");
                return;
            }
        }
        System.out.println("Compte introuvable.");
    }

    @Override
    public void retirer(float mt, int code) {
        for (int i = 0; i < nombreComptes; i++) {
            if (comptes[i].code == code) {
                if (comptes[i].solde >= mt) {
                    comptes[i].solde -= mt;
                    System.out.println("Retrait effectue.");
                } else {
                    System.out.println("Solde insuffisant.");
                }
                return;
            }
        }
        System.out.println("Compte introuvable.");
    }

    @Override
    public Compte getCompte(int code) {
        for (int i = 0; i < nombreComptes; i++) {
            if (comptes[i].code == code) {
                return comptes[i];
            }
        }
        return new Compte(0, 0);
    }

    @Override
    public Compte[] getComptes() {
        Compte[] resultat = new Compte[nombreComptes];

        for (int i = 0; i < nombreComptes; i++) {
            resultat[i] = comptes[i];
        }

        return resultat;
    }

    @Override
    public double conversion(float mt) {
        double taux = 3.4;
        return mt * taux;
    }
}
