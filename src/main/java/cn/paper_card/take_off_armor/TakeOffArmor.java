package cn.paper_card.take_off_armor;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public final class TakeOffArmor extends JavaPlugin {

    private static void sendError(@NotNull CommandSender sender, @NotNull String error) {
        sender.sendMessage(Component.text(error).color(NamedTextColor.DARK_RED));
    }

    @Override
    public void onEnable() {
        final PluginCommand command = this.getCommand("take-off-armor");

        assert command != null;
        command.setExecutor((commandSender, command1, s, strings) -> {

            if (!(commandSender instanceof final Player player)) {
                sendError(commandSender, "该命令只能由玩家来执行");
                return true;
            }

            final PlayerInventory inventory = player.getInventory();

            final @Nullable ItemStack[] armorContents = inventory.getArmorContents();

            final int MAX_INDEX = 9 * 4;

            int index = 0;
            int c = 0;

            for (int j = 0; j < armorContents.length; ++j) {
                final ItemStack armorContent = armorContents[j];

                if (armorContent == null) continue;

                while (index < MAX_INDEX) {
                    final ItemStack item = inventory.getItem(index);

                    if (item == null) {
                        inventory.setItem(index, armorContent);
                        armorContents[j] = null;
                        inventory.setArmorContents(armorContents);
                        ++c;
                        ++index;
                        break;
                    }
                    ++index;
                }

                if (index == MAX_INDEX) {
                    if (c == 0) {
                        player.sendActionBar(Component.text("背包满啦，没有脱下来噢~").color(NamedTextColor.RED));
                        return true;
                    }
                    break;
                }
            }

            player.sendActionBar(Component.text("已脱下你的衣服~").color(NamedTextColor.GREEN));

            return true;

        });

    }

    @Override
    public void onDisable() {
    }
}
