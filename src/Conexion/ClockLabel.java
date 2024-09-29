/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Conexion;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author FedeXavier
 */
public class ClockLabel extends JLabel implements ActionListener {

    String type;
    SimpleDateFormat sdf;

    public ClockLabel(String type2) {
        this.type = type2;
        setForeground(new Color(3, 190, 163));

        if (type2.equals("date")) {
            sdf = new SimpleDateFormat("  MMMM dd yyyy");
            setFont(new Font("century gothic", Font.PLAIN, 12));
            setHorizontalAlignment(SwingConstants.LEFT);

        }
        if (type2.equals("time")) {
            sdf = new SimpleDateFormat("hh:mm:ss a");
            setFont(new Font("century gothic", Font.PLAIN, 30));
            setHorizontalAlignment(SwingConstants.CENTER);
        }
        if (type2.equals("day")) {
            sdf = new SimpleDateFormat("EEEE  ");
            setFont(new Font("century gothic", Font.PLAIN, 16));
            setHorizontalAlignment(SwingConstants.RIGHT);
        }
       // sdf = new SimpleDateFormat();
    Timer t = new Timer(1000, this);
    t.start ();
}

public void actionPerformed(ActionEvent ae) {
    Date d = new Date();
    setText(sdf.format(d));
  }
}
