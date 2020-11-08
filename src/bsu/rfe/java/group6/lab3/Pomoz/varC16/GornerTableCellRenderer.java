package bsu.rfe.java.group6.lab3.Pomoz.varC16;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    // Ищем ячейки, строковое представление которых совпадает с needle
// (иголкой).
    private String needle = null;
    private String needleFrom = null;
    private String needleTo = null;
    private DecimalFormat formatter =
            (DecimalFormat)NumberFormat.getInstance();
    public GornerTableCellRenderer() {
// Показывать только 5 знаков после запятой
        formatter.setMaximumFractionDigits(5);
// Не использовать группировку (т.е. не отделять тысячи
// ни запятыми, ни пробелами), т.е. показывать число как "1000",
// а не "1 000" или "1,000"
        formatter.setGroupingUsed(false);
// Установить в качестве разделителя дробной части точку, а не
// запятую
        DecimalFormatSymbols dottedDouble =
                formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
// Разместить надпись внутри панели
        panel.add(label);
// Установить выравнивание надписи по левому краю панели
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row, int col) {
// Преобразовать double в строку с помощью форматировщика
        String formattedDouble = formatter.format(value);

        // Установить текст надписи равным строковому представлению числа
        label.setText(formattedDouble);
        if (col%2==0 && row%2==0 || col%2==1 && row%2==1){
            panel.setBackground(Color.BLACK);
            label.setForeground(Color.WHITE);
        }else {
            panel.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);
        }

        if (col >= 1 && needle!=null && needle.equals(formattedDouble)) {
            panel.setBackground(Color.RED);
            label.setForeground(Color.BLACK);
        }

        if(needleFrom!=null && needleTo!=null){
            Double coefficients1 =null;
            Double coefficients2 = null;
            Double coefficients3 = null;
            try{
                coefficients1 = Double.parseDouble(needleFrom);
                coefficients2 = Double.parseDouble(needleTo);
                coefficients3 = Double.parseDouble(formattedDouble);
            }catch(NullPointerException e){

            }

            if(col >= 1 && (coefficients3 <= coefficients2) && (coefficients3 >= coefficients1))
            {
                panel.setBackground(Color.GREEN);
                label.setForeground(Color.BLACK);
            }
        }
        return panel;

    }
    public void setNeedle(String needle) {
        this.needle = needle;
    }

    public void setNeedleFrom(String needleFrom) {
        this.needleFrom = needleFrom;
    }

    public void setNeedleTo(String needleTo) {
        this.needleTo = needleTo;
    }

    public String getNeedleFrom() {
        return needleFrom;
    }

    public String getNeedleTo() {
        return needleTo;
    }

    public void setNeedle1(String needleFrom, String needleTo)
    {
        this.needleFrom = needleFrom;
        this.needleTo = needleTo;
    }

}
