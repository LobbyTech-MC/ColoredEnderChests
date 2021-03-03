package io.github.thebusybiscuit.coloredenderchests;

import java.util.HashMap;
import java.util.Map;

import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.Updater;

public class ColoredEnderChests extends JavaPlugin implements SlimefunAddon {

    protected Config cfg;
    protected Map<Integer, String> colors = new HashMap<>();
    protected Category category;

    @Override
    public void onEnable() {
        cfg = new Config(this);

        // Setting up bStats
        new Metrics(this, 4907);

        // Setting up the Auto-Updater
        if (getDescription().getVersion().startsWith("DEV - ")) {
            // If we are using a development build, we want to switch to our custom
            Updater updater = new GitHubBuildsUpdater(this, getFile(), "TheBusyBiscuit/ColoredEnderChests/master");

            if (cfg.getBoolean("options.auto-update")) {
                updater.start();
            }
        }

        Research enderChestsResearch = new Research(new NamespacedKey(this, "colored_enderchests"), 2610, "彩色末影箱", 20);
        Research bigEnderChestsResearch = new Research(new NamespacedKey(this, "big_colored_enderchests"), 2611, "大型彩色末影箱", 30);

        enderChestsResearch.register();
        bigEnderChestsResearch.register();

        colors.put(0, "&r白");
        colors.put(1, "&6橙");
        colors.put(2, "&d品红");
        colors.put(3, "&b亮蓝");
        colors.put(4, "&e黄");
        colors.put(5, "&a青柠");
        colors.put(6, "&d粉");
        colors.put(7, "&8深灰");
        colors.put(8, "&7亮灰");
        colors.put(9, "&3深蓝");
        colors.put(10, "&5紫");
        colors.put(11, "&9蓝");
        colors.put(12, "&6棕");
        colors.put(13, "&2绿");
        colors.put(14, "&4红");
        colors.put(15, "&8黑");

        category = new Category(new NamespacedKey(this, "colored_enderchests"), new CustomItem(Material.ENDER_CHEST, "&5彩色末影箱"), 2);

        for (int c1 = 0; c1 < 16; c1++) {
            for (int c2 = 0; c2 < 16; c2++) {
                for (int c3 = 0; c3 < 16; c3++) {
                    registerEnderChest(enderChestsResearch, bigEnderChestsResearch, c1, c2, c3);
                }
            }
        }

    }

    private void registerEnderChest(Research smallResearch, Research bigResearch, final int c1, final int c2, final int c3) {
        if (cfg.getBoolean("small_chests")) {
            ColoredEnderChest item = new ColoredEnderChest(this, 27, c1, c2, c3);
            item.register(this);
            smallResearch.addItems(item);
        }

        if (cfg.getBoolean("big_chests")) {
            ColoredEnderChest item = new ColoredEnderChest(this, 54, c1, c2, c3);
            item.register(this);
            bigResearch.addItems(item);
        }
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/TheBusyBiscuit/ColoredEnderChests/issues";
    }
}
