package bsu.rfe.java.group6.lab3.Pomoz.varC16;

import javax.swing.table.AbstractTableModel;

public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    private double result[] = new double[3];
    public GornerTableModel(Double from, Double to, Double step,
                            Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }
    public Double getFrom() {
        return from;
    }
    public Double getTo() {
        return to;
    }
    public Double getStep() {
        return step;
    }
    public int getColumnCount() {
        return 4;
    }
    public int getRowCount() {
// Вычислить количество точек между началом и концом отрезка
// исходя из шага табулирования
        return new Double(Math.ceil((to-from)/step)).intValue()+1;
    }
    public Object getValueAt(int row, int col) {
// Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step * row;
        switch (col) {
            case 0:
                return x;
            case 1: {
                result[0] = 0.0;
// Вычисление значения в точке по схеме Горнера.
                for (int i = 0; i < coefficients.length; i++) {
                    result[0] += Math.pow(x, coefficients.length - 1 - i) * coefficients[i];
                }
                return result[0];
            }
            case 2: {
                result[1] = 0.0;
                int p = coefficients.length - 1;
                for (int i = 0; i < coefficients.length; i++) {
                    result[1] += Math.pow(x, coefficients.length - 1 - i) * coefficients[p--];
                }
                return result[1];
            }
            default:
                return result[2] = result[1] - result[0];
        }
    }
    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена";
            case 2:
                return "В обратном порядке";
            default:
                return "Разница между 2 и 3 столбцом";
        }
    }
    public Class<?> getColumnClass(int col) {
        return Double.class;
    }
}

