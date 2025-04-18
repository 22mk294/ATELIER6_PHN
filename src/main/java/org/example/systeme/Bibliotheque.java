package org.example.systeme;


import org.example.model.livres.Livre;
import java.util.ArrayList;
import java.util.List;

public class Bibliotheque {
    private static Bibliotheque instance;
    private List<Livre> catalogue;

    private Bibliotheque() {
        this.catalogue = new ArrayList<>();
    }

    public static Bibliotheque getInstance() {
        if (instance == null) {
            instance = new Bibliotheque();
        }
        return instance;
    }

    public void ajouterLivre(Livre livre) {
        catalogue.add(livre);
        System.out.println("✅ Livre ajouté au catalogue");
    }

    public void supprimerLivre(int id) {
        catalogue.removeIf(l -> l.getId() == id);
        System.out.println("🗑️ Livre supprimé");
    }

    public void afficherLivresDisponibles() {
        catalogue.stream().filter(Livre::isDisponible).forEach(Livre::afficherDetails);
    }

    public void afficherTousLivres() {
        catalogue.forEach(Livre::afficherDetails);
    }

    public Livre trouverLivreParId(int id) {
        return catalogue.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
    }
}
