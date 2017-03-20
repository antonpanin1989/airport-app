package com.company.airport.ui.forms;

import com.company.airport.model.Route;
import com.company.airport.repositori.RouteRepository;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Антон on 09.03.2017.
 */
@Component
public class RouteForm {
    @Autowired
    private RouteRepository routeRepository;

    private SwingWorker<Iterable<Route>, Integer> worker;

    private JTextField textField1;
    private JTable table1;
    private JPanel rootPanel;
    private RouteTableModel dataModel;

    public void updateData() {
        worker = new SwingWorker<Iterable<Route>, Integer>() {
            @Override
            protected Iterable<Route> doInBackground() throws Exception {
                return routeRepository.findAll();
            }

            @Override
            protected void done() {
                try {
                    dataModel.setData(get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }


    private void createUIComponents() {
        table1 = new JTable();
        dataModel = new RouteTableModel();
        table1.setModel(dataModel);

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        textField1 = new JTextField();
        rootPanel.add(textField1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        rootPanel.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(table1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
