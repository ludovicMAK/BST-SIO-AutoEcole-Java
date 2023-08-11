package Tools;
import Entities.Vehicule;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelJTableVehicule extends AbstractTableModel {
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

    public void loadDataVehicule(ArrayList<Vehicule> lesVehicule){
        nomsColonnes = new String[]{"id","Immatriculation","Marque","Modèle","Année"};
        rows = new Object[lesVehicule.size()][5];

        int i = 0;
        for(Vehicule veh: lesVehicule){
            rows[i][0] = veh.getId();
            rows[i][1] = veh.getImma();
            rows[i][2] = veh.getMarque();
            rows[i][3] = veh.getModele();
            rows[i][4] = veh.getAnnee();
            i++;
        }
        fireTableChanged(null);
    }


}
