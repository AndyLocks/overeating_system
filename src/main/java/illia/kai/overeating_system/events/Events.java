package illia.kai.overeating_system.events;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Integer;

public class Events implements Listener {
    private static final Logger LOGGER = Logger.getLogger(Events.class.getName()); static {
        LOGGER.setLevel(Level.OFF);
        LOGGER.addHandler(new ConsoleHandler());
    }

    private static final List<Material> food = List.of(
            Material.APPLE,
            Material.GOLDEN_APPLE,
            Material.ENCHANTED_GOLDEN_APPLE,
            Material.MELON_SLICE,
            Material.SWEET_BERRIES,
            Material.GLOW_BERRIES,
            Material.CHORUS_FRUIT,
            Material.GOLDEN_CARROT,
            Material.CARROT,
            Material.POTATO,
            Material.BAKED_POTATO,
            Material.POISONOUS_POTATO,
            Material.BEETROOT,
            Material.BEEF,
            Material.COOKED_BEEF,
            Material.PORKCHOP,
            Material.COOKED_PORKCHOP,
            Material.MUTTON,
            Material.COOKED_MUTTON,
            Material.CHICKEN,
            Material.COOKED_CHICKEN,
            Material.RABBIT,
            Material.COOKED_RABBIT,
            Material.COD,
            Material.COOKED_COD,
            Material.SALMON,
            Material.COOKED_SALMON,
            Material.TROPICAL_FISH,
            Material.PUFFERFISH,
            Material.BREAD,
            Material.COOKIE,
            Material.PUMPKIN_PIE,
            Material.ROTTEN_FLESH,
            Material.SPIDER_EYE,
            Material.MUSHROOM_STEW,
            Material.BEETROOT_SOUP,
            Material.SUSPICIOUS_STEW,
            Material.MILK_BUCKET
    );

    @EventHandler
    public void onEating(FoodLevelChangeEvent event) {
        if(event.getEntity().getType() != EntityType.PLAYER) return;

        Player player = (Player) event.getEntity();
        List<Integer> quantityEaten = new ArrayList<>();

        for (Material material : food){
            quantityEaten.add(
                    player.getStatistic(Statistic.USE_ITEM, material)
            );
            LOGGER.log(Level.WARNING, "Material: {0}", material.name());
            LOGGER.log(Level.WARNING, "Used: {0}", player.getStatistic(Statistic.USE_ITEM, material));
        }
        quantityEaten.sort((num1, num2) -> {
            return Integer.compare(num2, num1);
        });

        Material currentFoodMaterial;
        try {
            currentFoodMaterial = event.getItem().getType();
        }
        catch (NullPointerException ignored) {
            return;
        }

        if(player.getStatistic(Statistic.USE_ITEM, currentFoodMaterial) != quantityEaten.get(0)) return;

        int foodQuantityDifference = quantityEaten.get(0) - quantityEaten.get(1);
        if(foodQuantityDifference >= 9)
            player.addPotionEffect(
                    new PotionEffect(
                            PotionEffectType.CONFUSION,
                            foodQuantityDifference+100,
                            1,
                            true,
                            false
                    )
            );
    }
}
