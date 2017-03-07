package com.intellij.uiDesigner.core.config;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

class FitnessePluginConfigurationForm {

    private JPanel rootPanel;
    private JTextField fitnesseRootFolderUrl;
    private JTextField fixtureSuffix;
    private FitNessePluginConfig mConfig;

    private int currentRow = 0;

    FitnessePluginConfigurationForm() {
        rootPanel = new JPanel();
        Border border = rootPanel.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        rootPanel.setBorder(new CompoundBorder(border, margin));

        GridBagLayout panelGridBagLayout = new GridBagLayout();
        panelGridBagLayout.columnWidths = new int[] { 86, 86, 0 };
        panelGridBagLayout.rowHeights = new int[] { 20, 20, 20, 20, 20, 0 };
        panelGridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        panelGridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        rootPanel.setLayout(panelGridBagLayout);

        fitnesseRootFolderUrl = createRow("Fitnesse Root Folder Url:");
        fixtureSuffix = createRow("Fixture Class Suffix:");
    }

    private JTextField createRow(String tabelText) {
        JLabel label = new JLabel(tabelText);
        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
        gridBagConstraintForLabel.gridx = 0;
        gridBagConstraintForLabel.gridy = currentRow;
        rootPanel.add(label, gridBagConstraintForLabel);

        JTextField field = new JTextField();
        GridBagConstraints gridBagConstraintForTextField = new GridBagConstraints();
        gridBagConstraintForTextField.fill = GridBagConstraints.BOTH;
        gridBagConstraintForTextField.insets = new Insets(0, 0, 5, 0);
        gridBagConstraintForTextField.gridx = 1;
        gridBagConstraintForTextField.gridy = currentRow++;
        rootPanel.add(field, gridBagConstraintForTextField);
        return field;
    }

    void createUI() {
        mConfig = FitNessePluginConfig.getInstance();
        fitnesseRootFolderUrl.setText(mConfig.getFitnesseRootFolderUrl());
        fixtureSuffix.setText(mConfig.getFixtureSuffix());
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public boolean isModified() {
        boolean modified = !Objects.equals(mConfig.getFitnesseRootFolderUrl(), fitnesseRootFolderUrl.getText());
        modified |= !Objects.equals(fixtureSuffix.getText(), mConfig.getFixtureSuffix());
        return modified;
    }

    public void apply() {
        mConfig.setFitnesseRootFolderUrl(fitnesseRootFolderUrl.getText());
        mConfig.setFixtureSuffix(fixtureSuffix.getText());
    }

    public void reset() {
        fitnesseRootFolderUrl.setText(mConfig.getFitnesseRootFolderUrl());
        fixtureSuffix.setText(mConfig.getFixtureSuffix());
    }
}
