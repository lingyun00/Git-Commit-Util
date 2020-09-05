package com.lingyun.beforeCommit;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
import com.intellij.ui.NonFocusableCheckBox;
import com.lingyun.model.TypeAlias;
import com.lingyun.storage.GitCommitMessageHelperSettings;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class CheckCommitMsgStyleHandler extends CheckinHandler {

    private final static String CHECK_GIT_HANDLER_NAME = "Check Style";
    private final static String CHECK_RESULT_TIP = "Error";

    private static boolean checkFlag = true;

    private final GitCommitMessageHelperSettings settings;
    private final CheckinProjectPanel myCheckinPanel;
    private final Project myProject;

    CheckCommitMsgStyleHandler(Project myProject, CheckinProjectPanel myCheckinPanel) {
        this.myProject = myProject;
        this.myCheckinPanel = myCheckinPanel;
        settings = ServiceManager.getService(GitCommitMessageHelperSettings.class);
    }

    @Nullable
    @Override
    public RefreshableOnComponent getBeforeCheckinConfigurationPanel() {

        // 构造非焦点复选框
        NonFocusableCheckBox checkBox = new NonFocusableCheckBox(CHECK_GIT_HANDLER_NAME);

        return new RefreshableOnComponent() {

            @Override
            public JComponent getComponent() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(checkBox);
                boolean dumb = DumbService.isDumb(myProject);
                checkBox.setEnabled(!dumb);

                return panel;
            }

            @Override
            public void refresh() {
            }

            @Override
            public void saveState() {
                checkFlag = checkBox.isSelected();
            }

            @Override
            public void restoreState() {
                checkBox.setSelected(checkFlag);
            }
        };
    }


    @Override
    public ReturnResult beforeCheckin() {
        if (!checkFlag) {
            return ReturnResult.COMMIT;
        } else {
            if (cheackCommitMessageSytle(myCheckinPanel.getCommitMessage())) {
                return ReturnResult.COMMIT;
            } else {
                // 取消提交,并弹出错误提示框
                Messages.showErrorDialog("Commit message Not contain Template", CHECK_RESULT_TIP);
                return ReturnResult.CANCEL;
            }
        }
    }

    private boolean cheackCommitMessageSytle(String commitMessage) {

        List<TypeAlias> typeAliases = settings.getDateSettings().getTypeAliases();
        if (CollectionUtils.isEmpty(typeAliases)) {
            return true;
        }

        for (TypeAlias typeAlias : typeAliases) {
            if (commitMessage.contains(typeAlias.getTitle())) {
                return true;
            }
        }

        return false;
    }
}
