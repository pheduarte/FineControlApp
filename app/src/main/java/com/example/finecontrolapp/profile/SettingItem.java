package com.example.finecontrolapp.profile;

public class SettingItem {
    private final int iconRes;
    private final String title;
    private final String subtitle;
    private final Runnable action;

    public SettingItem(int iconRes, String title, String subtitle, Runnable action) {
        this.iconRes = iconRes;
        this.title = title;
        this.subtitle = subtitle;
        this.action = action;
    }

    public int getIconRes() { return iconRes; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public Runnable getAction() { return action; }
}
