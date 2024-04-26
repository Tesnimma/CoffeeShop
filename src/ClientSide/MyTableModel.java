package ClientSide;

import ServerSide.ProduitDAO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyTableModel extends AbstractTableModel {
    ArrayList<Object[]> data = new ArrayList<Object[]>();
    ResultSetMetaData rsmd;
    ProduitDAO dao;
    MyTableModel(ResultSet rs, ProduitDAO dao){
        this.dao = dao;
        try {
            rsmd = rs.getMetaData();
            while (rs.next()){
                Object[] ligne = new Object[rsmd.getColumnCount()];
                for(int i=0; i<ligne.length; i++){
                    ligne[i] = rs.getObject(i+1);

                }
                data.add(ligne);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        try {
            return rsmd.getColumnCount();
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        try {
            return rsmd.getColumnName(column+1);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(getColumnName(columnIndex).equalsIgnoreCase("ID"))
            return false;
        else
            return true;
    }
    int columnNametoIndex(String columnName){
        for(int i=0; i<getColumnCount(); i++){
            if(getColumnName(i).equalsIgnoreCase(columnName))
                return i;
        }
        return -1;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        String category = getValueAt(rowIndex, columnNametoIndex("Category")).toString();
        String name = getValueAt(rowIndex, columnNametoIndex("Name")).toString();
        double price = Double.parseDouble(getValueAt(rowIndex, columnNametoIndex("Price")).toString());
        int id = Integer.parseInt(getValueAt(rowIndex, columnNametoIndex("id")).toString());
        if(getColumnName(columnIndex).equalsIgnoreCase("Name"))
            name = aValue.toString();
        if(getColumnName(columnIndex).equalsIgnoreCase("Category"))
            category = aValue.toString();
        if(getColumnName(columnIndex).equalsIgnoreCase("Price"))
            price = Double.parseDouble(aValue.toString());
        int a = dao.modifProd(name, category, id, price);
        if(a>0){data.get(rowIndex)[columnIndex] = aValue;}
    }
    public void insertProd(String name, String category, int id, double price){
        int a = dao.insertProd(name, category, id, price);
        if(a>0){
            data.add(new Object[]{name, category, id, price});
            fireTableDataChanged(); //refraichir l'affichage
            JOptionPane.showMessageDialog(null, "done");
        }
        else {
            JOptionPane.showMessageDialog(null, "not inserted..");
        }
    }
    public void suppProd(int id){
        int option = JOptionPane.showConfirmDialog(null, "Metyaked ?", "Ya weldi zid thabet ?", JOptionPane.YES_NO_OPTION);
        if(option == JOptionPane.YES_NO_OPTION){
            dao.suppProd(id);
            for(int i=0; i<data.size();i++){
                if((int) data.get(i)[columnNametoIndex("ID")] == id){
                    data.remove(i);
                    fireTableDataChanged();
                    JOptionPane.showMessageDialog(null, "done");
                    return;
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "not deleted");
        }
    }

}
