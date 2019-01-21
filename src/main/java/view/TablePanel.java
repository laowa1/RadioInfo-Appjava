package view;

import model.ProgramInfo;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Table for schedule.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */
public class TablePanel extends JPanel {
    DefaultTableModel tModel;
    JTable table;
    String columnNames[] = {"Program", "Startar", "Slutar"};
    Object[][] rows = {};
    private int scrollPosition;
    private JScrollPane scrollPane;

    /**
     * Constructs the table
     */
    public TablePanel() {
        tModel = new DefaultTableModel(rows, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };

        table = new JTable(tModel);
        table.setRowHeight(50);
        table.setDefaultRenderer(Object.class, new CellRenderer());
        //table.setDefaultEditor(Object.class, new TableEditor());
        scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        table.setFillsViewportHeight(true);
        this.add(scrollPane);
    }

    /**
     * Updates the table.
     * @param pList
     */
    public void updateTable(List<ProgramInfo> pList) {
        for (ProgramInfo pI : pList) {
            String date = pI.getStartTimeUTC() + pI.getEndTimeUTC();
            tModel.addRow(new Object[]{pI.getName(), date});
        }
    }

    /**
     * Non-implemented function for showing which programs
     * have already expired.
     */
    public void renderOldPrograms() {
        //TODO implements rendering.
        for (int i = 0; i < tModel.getRowCount(); i++) {

        }
    }

    public void refreshMyTable(List<ProgramInfo> pList) {
        scrollPosition = scrollPane.getVerticalScrollBar().getValue();
        try {
            tModel.setRowCount(0);
            for (int i = 0; i < pList.size(); i++) {
                tModel.addRow(new Object[]{pList.get(i).getName(),
                        pList.get(i).getStartTimeUTC(),
                        pList.get(i).getEndTimeUTC()});
                //parseTimes(pList.get(i).getStartTimeUTC(),
                //         pList.get(i).getEndTimeUTC())});
            }
        } finally {
            scrollPane.getVerticalScrollBar().setValue(scrollPosition);
        }
    }

    public void addListenersToTable(ListSelectionListener l) {
        for (int i = 0; i < tModel.getRowCount(); i++) {
            table.getSelectionModel().addListSelectionListener(l);
        }
    }

    public class CellRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            DateTimeFormatter formatter2 = DateTimeFormatter.ISO_TIME;
            JTextField editor = new JTextField();
            LocalDateTime time = LocalDateTime.now();
            Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 13).deriveFont(
                    AffineTransform.getScaleInstance(0.8, 1d));
            editor.setFont(myFont);
            //System.out.println(value);
            if (column > 0 && value != null && value.toString().length() > 13) {
                LocalDateTime timeValue = LocalDateTime.parse(value.toString(), formatter);
                String timeText = timeValue.format(formatter2);

                if (time.isAfter(timeValue)) {
                    editor.setBackground(new Color(200, 110, 95));
                }
                else {
                    editor.setBackground(new Color(170, 200, 100));
                }
                editor.setText(timeText.substring(0, timeText.length() - 3));
            } else if (value != null) {
                editor.setText(value.toString());
            }
            return editor;
        }
    }
}
