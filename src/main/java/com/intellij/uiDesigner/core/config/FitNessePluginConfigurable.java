package com.intellij.uiDesigner.core.config;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FitNessePluginConfigurable implements SearchableConfigurable {

    private FitnessePluginConfigurationForm mGui;

    @NotNull
    @Override
    public String getId() {
        return "preference.FitNessePluginConfigurable";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "FitNesse Plugin Configuration";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return getId();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mGui = new FitnessePluginConfigurationForm();
        mGui.createUI();
        return mGui.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return mGui.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        mGui.apply();
    }

    @Override
    public void reset() {
        mGui.reset();
    }

    @Override
    public void disposeUIResources() {
        mGui = null;
    }

    @Nullable
    @Override
    public Runnable enableSearch(String s) {
        return null;
    }
}
