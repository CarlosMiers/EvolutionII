/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
import javax.swing.table.DefaultTableModel;

import javax.swing.table.DefaultTableModel;

public class TablaCobros extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == 9) {
            return true;
        } else {
            return false;
        }
    }
}
