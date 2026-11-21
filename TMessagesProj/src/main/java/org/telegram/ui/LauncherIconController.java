package org.telegram.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.R;

public class LauncherIconController {
    public static void tryFixLauncherIconIfNeeded() {
        for (LauncherIcon icon : LauncherIcon.values()) {
            if (isEnabled(icon)) {
                return;
            }
        }

        setIcon(LauncherIcon.DEFAULT);
    }

    public static boolean isEnabled(LauncherIcon icon) {
        Context ctx = ApplicationLoader.applicationContext;
        int i = ctx.getPackageManager().getComponentEnabledSetting(icon.getComponentName(ctx));
        return i == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || i == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT && icon == LauncherIcon.DEFAULT;
    }

    public static void setIcon(LauncherIcon icon) {
        Context ctx = ApplicationLoader.applicationContext;
        PackageManager pm = ctx.getPackageManager();
        for (LauncherIcon i : LauncherIcon.values()) {
            pm.setComponentEnabledSetting(i.getComponentName(ctx), i == icon ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
    }

    public enum LauncherIcon {
        DEFAULT("DefaultIcon", R.drawable.ic_launcher_yuki_purple_playstore, R.drawable.ic_launcher_yuki_purple_playstore, R.string.AppIconDefault, 0xFFB388FF),
        BLUE("YukiBlueIcon", R.drawable.ic_launcher_yuki_blue_playstore, R.drawable.ic_launcher_yuki_blue_playstore, R.string.Blue, 0xFF2196F3),
        GOLD("YukiGoldIcon", R.drawable.ic_launcher_yuki_gold_playstore, R.drawable.ic_launcher_yuki_gold_playstore, R.string.Gold, 0xFFFFC107),
        WAVES("YukiWavesIcon", R.drawable.ic_launcher_yuki_waves_playstore, R.drawable.ic_launcher_yuki_waves_playstore, R.string.Waves, 0xFF00BCD4),
        RED("YukiRedIcon", R.drawable.ic_launcher_yuki_red_playstore, R.drawable.ic_launcher_yuki_red_playstore, R.string.Red, 0xFFFF3B30),
        GREEN("YukiGreenIcon", R.drawable.ic_launcher_yuki_green_playstore, R.drawable.ic_launcher_yuki_green_playstore, R.string.Green, 0xFF34C759);

        public final String key;
        public final int background;
        public final int foreground;
        public final int title;
        public final boolean premium;
        public final int previewColor;

        private ComponentName componentName;

        public ComponentName getComponentName(Context ctx) {
            if (componentName == null) {
                if (this == DEFAULT) {
                    componentName = new ComponentName(ctx.getPackageName(), "org.telegram.messenger.DefaultIcon");
                } else {
                    componentName = new ComponentName(ctx.getPackageName(), "org.telegram.messenger." + key);
                }
            }
            return componentName;
        }

        LauncherIcon(String key, int background, int foreground, int title, int previewColor) {
            this(key, background, foreground, title, false, previewColor);
        }

        LauncherIcon(String key, int background, int foreground, int title, boolean premium, int previewColor) {
            this.key = key;
            this.background = background;
            this.foreground = foreground;
            this.title = title;
            this.premium = premium;
            this.previewColor = previewColor;
        }
    }
}
