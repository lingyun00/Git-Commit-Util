package com.lingyun;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;


public class GitCommitMessageHelper implements ApplicationComponent {

    public GitCommitMessageHelper() {
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "GitCommitMessageHelper";
    }

}
