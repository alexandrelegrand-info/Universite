package fr.univlille.g2.sae302.utils;

/**
 * Interface Observer qui sert à implémenter le modèle MVC
 * 
 * @author Aymane Benafquir
 * @author Alexandre Legrand
 * @author Louis Beck
 * @author Kylian Robin
 */
public interface Observer {
        void update(Observable observable);
        void update(Observable observable, Object data);
}
