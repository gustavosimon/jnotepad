package jnotepad;

/**
 * Classe executável do editor de texto
 * @author Gustavo Simon
 */
public class jNotepad {
    public static void main(String[] args) {
        jTela e = new jTela();
        e.montarTela();
        e.setVisible(true);
    }
}
