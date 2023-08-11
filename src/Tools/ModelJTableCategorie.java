package Tools;

import Entities.Categorie;
import Entities.Lecon;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelJTableCategorie extends AbstractTableModel {
    private String[] colonnes;
    private Object[][] lignes;

    @Override
    public int getRowCount() {
        return lignes.length;
    }

    @Override
    public int getColumnCount() {
        return colonnes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return lignes[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return colonnes[column];
    }

    public void loadDefautCategorie(){
        colonnes = new String[]{"Libellé","Prix"};
        lignes = new Object[0][2];
        fireTableChanged(null);
    }
    public void loadDataCategorie(ArrayList<Categorie> lesCategories){
        colonnes = new String[]{"Libellé","Prix"};
        lignes = new Object[lesCategories.size()][2];

        int i = 0;
        for(Categorie c: lesCategories){
            lignes[i][0] = c.getLibelle();
            lignes[i][1] = c.getPrix();
            i++;
        }
        fireTableChanged(null);
    }

}
