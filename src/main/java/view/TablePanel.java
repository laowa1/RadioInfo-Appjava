package view;

import controller.TableSelectionListener;
import model.ProgramInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Table for schedule.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */
public class TablePanel extends JPanel {
    private final DefaultTableModel tModel;
    private final JTable table;
    private final String[] columnNames = {"Program", "Startar", "Slutar"};
    private final Object[][] rows = {};
    private ListSelectionModel lModel;
    private final JScrollPane scrollPane;
    public int index;
    private List<ProgramInfo> pList;
    int scrollPosition = 0;

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
        setLayout(new BorderLayout());
        table = new JTable(tModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(50);
        table.setRowSelectionAllowed(true);
        table.setCellSelectionEnabled(false);
        table.setColumnSelectionAllowed(false);
        table.setDefaultRenderer(Object.class, new CellRenderer());
        //table.setDefaultEditor(Object.class, new TableEditor());
        scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        table.setFillsViewportHeight(true);
        table.setTableHeader(null);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void setpList(List<ProgramInfo> pList) {
        this.pList = pList;
    }
    /**
     * Refreshes the table with new information.
     */
    public void refreshMyTable() {
        table.removeAll();
        scrollPosition = scrollPane.getVerticalScrollBar().getValue();
        try {
            tModel.setRowCount(0);
            for (ProgramInfo programInfo : pList) {
                tModel.addRow(new Object[]{programInfo.getName(),
                        programInfo.getStartTimeUTC(),
                        programInfo.getEndTimeUTC()});
                //parseTimes(pList.get(i).getStartTimeUTC(),
                //         pList.get(i).getEndTimeUTC())});
            }
            table.setGridColor(new Color(150, 220, 220, 120));
            //table.setIntercellSpacing(new Dimension(0,0));
            TableColumn column;
            for (int i = 0; i < 3; i++) {
                column = table.getColumnModel().getColumn(i);
                if (i > 0) {
                    column.setPreferredWidth(50); //third column is bigger
                } else {
                    column.setPreferredWidth(300);
                }
            }
        } finally {
            scrollPane.getVerticalScrollBar().setValue(scrollPosition);
        }
    }

    /**
     * Adds listeners to the table.
     * @param l listener to add.
     */
    public void addListenersToTableV2(TableSelectionListener l) {
        lModel = table.getSelectionModel();
        lModel.addListSelectionListener(l);
    }

    /**
     * Gets the table
     * @return table.
     */
    public JTable getTable() {
        return table;
    }

    public void resetScrollPosition() {
        scrollPosition = 0;
    }


    /**
     * Class for rendering the cells of the table.
     */
    private class CellRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {


            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            DateTimeFormatter formatter2 = DateTimeFormatter.ISO_TIME;
            JTextField editor = new JTextField();
            LocalDateTime time = LocalDateTime.now();
            Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 14).deriveFont(
                    AffineTransform.getScaleInstance(0.9, 1d));
            editor.setFont(myFont);
            editor.setOpaque(true);
            //System.out.println(value);
            if (column > 0 && value != null && value.toString().length() > 13) {
                LocalDateTime timeValue = LocalDateTime.parse(value.toString(), formatter);
                String timeText = timeValue.format(formatter2);
                editor.setBounds(new Rectangle(table.getWidth()/6, table.getRowHeight()));
                final String substring = timeText.substring(0, timeText.length() - 3);
                if (time.isAfter(timeValue)) {
                    editor.setBackground(new Color(210, 120, 105, 200));
                    table.repaint();
                    editor.setText('(' + substring + ')');
                }
                else {
                    editor.setBackground(new Color(180, 210, 100, 200));
                    table.repaint();
                    editor.setText(substring);
                }
            } else if (value != null) {
                if ((row % 2) == 0) {
                    //editor.setBackground(new Color(100, 10, 10, 50));
                } else {
                    editor.setBackground(new Color(150, 220, 220, 120));
                    table.repaint();
                }
                editor.setText(value.toString());
            }
            return editor;
        }
    }
}
