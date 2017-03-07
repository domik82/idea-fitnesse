package com.intellij.uiDesigner.core.config;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(
        name = "FitNessePluginConfig",
        storages = {
                @Storage(file = StoragePathMacros.APP_CONFIG + "/FitNessePluginConfig.xml")
        }
)
public class FitNessePluginConfig implements PersistentStateComponent<FitNessePluginConfig> {

    private String fitnesseRootFolderUrl;
    private String fixtureSuffix;
    private final String DEFAULT_URL = "http://localhost:8025/NextGen";
    private final String DEFAULT_SUFFIX = "Fixture";

    public String getFitnesseRootFolderUrl() {
        return fitnesseRootFolderUrl;
    }

    public String getFixtureSuffix() {
        return fixtureSuffix;
    }

    void setFitnesseRootFolderUrl(String fitnesseRootFolderUrl) {
        this.fitnesseRootFolderUrl = fitnesseRootFolderUrl;
    }

    void setFixtureSuffix(String fixtureSuffix) {
        this.fixtureSuffix = fixtureSuffix;
    }

    @Nullable
    @Override
    public FitNessePluginConfig getState() {
        return this;
    }

    @Override
    public void loadState(FitNessePluginConfig singleFileExecutionConfig) {
        XmlSerializerUtil.copyBean(singleFileExecutionConfig, this);
        setupDefaultValuesIfNeeded();
    }

    private void setupDefaultValuesIfNeeded() {
        if (getFitnesseRootFolderUrl() == null) {
            setFitnesseRootFolderUrl(DEFAULT_URL);
        }
        if (getFixtureSuffix() == null) {
            setFixtureSuffix(DEFAULT_SUFFIX);
        }
    }

    @Nullable
    public static FitNessePluginConfig getInstance() {
        FitNessePluginConfig config = ServiceManager.getService(FitNessePluginConfig.class);
        if (config == null) {
            config = new FitNessePluginConfig();
        }
        config.setupDefaultValuesIfNeeded();
        return config;
    }


}
