package Tools;
import Entities.Moniteur;
import Entities.Vehicule;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelJTableMoniteurForStatGerante extends AbstractTableModel {
    private String[] nomsColonnes;
    private Object[][] rows;

    @Override
    public String getColumnName(int column) {
        return nomsColonnes[column];
    }

    @Override
    public int getRowCount() {
        return rows.length;
    }

    @Override
    public int getColumnCount() {
        return nomsColonnes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        rows[row][column] = value;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 1 ;
    }

    public void loadDataMoniteur(ArrayList<Moniteur> lesMoniteurs){
        nomsColonnes = new String[]{"Nombre de leçon","id","moniteur"};
        rows = new Object[lesMoniteurs.size()][3];

        int i = 0;
        for(Moniteur moniteur: lesMoniteurs){
            rows[i][0] = moniteur.getNbLecon();
            rows[i][1] = moniteur.getIdMoniteur();
            rows[i][2] = moniteur.getNom() +" "+moniteur.getPrenom();
            i++;
        }
        fireTableChanged(null);
    }



}
