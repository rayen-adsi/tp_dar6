package corbaClient;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

import corbaBanque.Compte;
import corbaBanque.IBanqueRemote;
import corbaBanque.IBanqueRemoteHelper;

public class BanqueClient {

    public static void main(String[] args) {

        try {
            // 1. Configurer l'annuaire JNDI
            Properties props = new Properties();

            props.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.cosnaming.CNCtxFactory");

            props.put(Context.PROVIDER_URL,
                    "iiop://localhost:900");

            // 2. Recuperer le contexte JNDI
            Context ctx = new InitialContext(props);

            // 3. Recuperer la reference distante
            Object obj = ctx.lookup("Banque");

            // 4. Convertir la reference en Stub CORBA
            IBanqueRemote banque =
                    IBanqueRemoteHelper.narrow(
                            (org.omg.CORBA.Object) obj
                    );

            System.out.println("Connexion au serveur reussie !");

            // 5. Creer un compte
            Compte c1 = new Compte(1, 1000);

            banque.creerCompte(c1);

            System.out.println("Compte cree avec succes !");

            // 6. Verser de l'argent
            banque.verser(500, 1);

            System.out.println("Apres versement : "
                    + banque.getCompte(1).solde);

            // 7. Retirer de l'argent
            banque.retirer(200, 1);

            System.out.println("Apres retrait : "
                    + banque.getCompte(1).solde);

            // 8. Consulter un compte
            Compte c = banque.getCompte(1);

            System.out.println("Code : " + c.code);
            System.out.println("Solde : " + c.solde);

            // 9. Consulter tous les comptes
            Compte[] comptes = banque.getComptes();

            System.out.println("Liste des comptes :");

            for (Compte cp : comptes) {
                System.out.println(
                        "Code : " + cp.code
                                + " | Solde : " + cp.solde
                );
            }

            // 10. Conversion Euro vers DT
            double resultat = banque.conversion(100);

            System.out.println("100 Euros = "
                    + resultat + " DT");

            ctx.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
