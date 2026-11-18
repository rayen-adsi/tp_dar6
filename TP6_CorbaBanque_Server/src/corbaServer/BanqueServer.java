
package corbaServer;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NameComponent;

import service.BanqueImpl;

public class BanqueServer {

    public static void main(String[] args) {

        try {
            // 1. Initialiser l'ORB
            ORB orb = ORB.init(args, null);

            // 2. Recuperer le RootPOA
            POA rootPOA = POAHelper.narrow(
                    orb.resolve_initial_references("RootPOA")
            );

            // 3. Activer le POAManager
            rootPOA.the_POAManager().activate();

            // 4. Creer le servant
            BanqueImpl banqueImpl = new BanqueImpl();

            // 5. Obtenir la reference CORBA
            org.omg.CORBA.Object ref =
                    rootPOA.servant_to_reference(banqueImpl);

            // 6. Convertir la reference en NamingContextExt
            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");

            NamingContextExt ncRef =
                    NamingContextExtHelper.narrow(objRef);

            // 7. Enregistrer le service dans l'annuaire
            String name = "Banque";
            NameComponent path[] = ncRef.to_name(name);

            ncRef.rebind(path, ref);

            System.out.println("Serveur Banque pret...");

            // 8. Attendre les requetes des clients
            orb.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
