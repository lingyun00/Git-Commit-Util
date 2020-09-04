package com.lingyun.setting.ui;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.SearchableConfigurable;
import com.lingyun.storage.GitCommitMessageHelperSettings;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 这个类 Settings 中的属性被创建的时候
 */
public class TemplateEditPaneConfigurable implements SearchableConfigurable {

    private TemplateEditPane templateEditPane;
    private GitCommitMessageHelperSettings settings;

    public TemplateEditPaneConfigurable() {
        settings = ServiceManager.getService(GitCommitMessageHelperSettings.class);
    }

    @NotNull
    @Override
    public String getId() {
        return "plugins.gitcommitutil";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (templateEditPane == null) {
            templateEditPane = new TemplateEditPane(settings);
        }
        return templateEditPane.getMainPenel();
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "help.gitcommitutil.configuration";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "GitCommitUtil";
    }

    public void reset() {
        if (templateEditPane != null) {
            templateEditPane.reset(settings);
        }
    }

    @Override
    public boolean isModified() {
        return templateEditPane != null && templateEditPane.isSettingsModified(settings);
    }

    @Override
    public void apply() {
        settings.setDateSettings(templateEditPane.getSettings().getDateSettings());
        settings = templateEditPane.getSettings().clone();
    }
}