package com.lg.plugin.decode;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.lg.plugin.decode.xxtea.XXTEA;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class DecodeAnAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        //final getAliPayWapPayUrlField = xxtea.decryptToString('MOcB1wuM5P1AWA7TLuPs3IaUYu65/oES', 'xVZS9feFCxki')!;
        String selectedText = getEditor(e).getSelectionModel().getSelectedText();
        System.out.println("selectedText:"+selectedText);
        if(selectedText == null){
            return;
        }
        if(!selectedText.contains(",")){
            return;
        }
        selectedText = selectedText.replaceAll("'", "").trim();
        String[] split = selectedText.split(", ");
        String s = new String(XXTEA.decryptBase64String(split[0], split[1]));
//        Messages.showMessageDialog(e.getProject(), s, "this message title", Messages.getInformationIcon());
//        NotificationGroup first_plugin_id = new NotificationGroup("first_plugin_id", NotificationDisplayType.BALLOON, true);
//        Notification notification = first_plugin_id.createNotification(s, MessageType.INFO);
//        Notifications.Bus.notify(notification);

        FlowLayout layout1 = new FlowLayout(FlowLayout.CENTER);
        JPanel panel = new JPanel(layout1);
//        BoxLayout layout=new BoxLayout(panel, BoxLayout.Y_AXIS);

//        panel.setLayout(layout);
//        panel.setPreferredSize(new Dimension(200,100));
        JLabel label = new JLabel(s);
        label.setBorder(new EmptyBorder(20,10,20,10));
        panel.add(label, BorderLayout.CENTER);
        JButton button = new JButton("复制");
//        button.setHorizontalAlignment(SwingConstants.CENTER); //设置控件左右居中

        button.setPreferredSize(new Dimension(70,30));
        panel.add(button);
        JBPopup jbPopup = JBPopupFactory.getInstance().createComponentPopupBuilder(panel, null).createPopup();
        jbPopup.showCenteredInCurrentWindow(Objects.requireNonNull(e.getProject()));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                jbPopup.cancel();
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                // 封装文本内容
                Transferable trans = new StringSelection(s);
                // 把文本内容设置到系统剪贴板
                clipboard.setContents(trans, null);
            }
        });



    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        String selectedText = getEditor(e).getSelectionModel().getSelectedText();
        if(selectedText == null){
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }
        else if(!selectedText.contains(",")){
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }
        e.getPresentation().setEnabledAndVisible(true);
    }

    Editor getEditor(AnActionEvent e){
        return e.getRequiredData(CommonDataKeys.EDITOR);
    }
}
