package Tools;

import Entities.Eleve;
import Entities.Lecon;
import Entities.Moniteur;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelJTableUser extends AbstractTableModel {
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

    public void loadDefautUser(){
        colonnes = new String[]{"Role","Login","Nom","Prénom"};
        lignes = new Object[0][4];
        fireTableChanged(null);
    }

    public void loadUser(ArrayList<Eleve> lesEleves, ArrayList<Moniteur> lesMoniteurs){
        colonnes = new String[]{"Role","Login","Nom","Prénom"};
        lignes = new Object[lesEleves.size()+lesMoniteurs.size()][4];

        int i = 0;
        for(Eleve e: lesEleves){
            lignes[i][0] = "eleve";
            lignes[i][1] = e.getUser().getLogin();
            lignes[i][2] = e.getNom();
            lignes[i][3] = e.getPrenom();
            i++;
        }
        for(Moniteur m: lesMoniteurs){
            lignes[i][0] = "moniteur";
            lignes[i][1] = m.getUser().getLogin();
            lignes[i][2] = m.getNom();
            lignes[i][3] = m.getPrenom();
            i++;
        }
        fireTableChanged(null);
    }
}
