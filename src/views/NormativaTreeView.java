package views;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.ElementoNormativo;
import models.ElementoNormativoDAO;

public class NormativaTreeView extends JFrame {

    private JTree arbolNormativo;

    public NormativaTreeView() {

        setTitle("Proyecto AIR - Árbol Normativo");
        setSize(700, 500);
        setLocationRelativeTo(null);

        DefaultMutableTreeNode raiz =
            construirArbolNormativo();

        arbolNormativo = new JTree(raiz);

        JScrollPane scrollPane =
            new JScrollPane(arbolNormativo);

        JLabel titulo = new JLabel(
            "Árbol Normativo - Reglamento AIR",
            SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        add(titulo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        expandirArbol();
    }

    private DefaultMutableTreeNode construirArbolNormativo() {

        ElementoNormativoDAO dao =
            new ElementoNormativoDAO();

        List<ElementoNormativo> elementos =
            dao.listarElementos();

        DefaultMutableTreeNode raiz =
            new DefaultMutableTreeNode("Normativa AIR");

        Map<Integer, DefaultMutableTreeNode> nodos =
            new HashMap<>();

        for (ElementoNormativo elemento : elementos) {

            DefaultMutableTreeNode nodo =
                new DefaultMutableTreeNode(elemento);

            nodos.put(
                elemento.getIdElemento(),
                nodo
            );
        }

        for (ElementoNormativo elemento : elementos) {

            DefaultMutableTreeNode nodoActual =
                nodos.get(elemento.getIdElemento());

            if (elemento.getIdPadre() == null) {

                raiz.add(nodoActual);

            } else {

                DefaultMutableTreeNode nodoPadre =
                    nodos.get(elemento.getIdPadre());

                if (nodoPadre != null) {

                    nodoPadre.add(nodoActual);

                } else {

                    raiz.add(nodoActual);
                }
            }
        }

        return raiz;
    }

    private void expandirArbol() {

        for (int i = 0; i < arbolNormativo.getRowCount(); i++) {
            arbolNormativo.expandRow(i);
        }
    }
}