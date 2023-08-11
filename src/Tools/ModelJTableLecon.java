package Tools;

import Entities.Lecon;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelJTableLecon extends AbstractTableModel {
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

    @Override
    public void setValueAt(Object value, int row, int column){
        lignes[row][column] = value;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        if(columnIndex == 2 || columnIndex == 1){
            return Boolean.class;
        }
        return super.getColumnClass(columnIndex);
    }

    public void loadDefautLecon(){
        colonnes = new String[]{"Date","Passée","Payée"};
        lignes = new Object[0][3];
        fireTableChanged(null);
    }
    public void loadDataLecon(ArrayList<Lecon> lesLecons){
        colonnes = new String[]{"Date","Passée","Payée"};
        lignes = new Object[lesLecons.size()][3];

        int i = 0;
        for(Lecon l: lesLecons){
            lignes[i][0] = l.getDate();
            lignes[i][1] = l.isPassee();
            lignes[i][2] = l.isPayee();
            i++;
        }
        fireTableChanged(null);
    }


}
