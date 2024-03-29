package whocraft.tardis_refined.common.capability.upgrades;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.RegistryHelper;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class Upgrades {

    public static final ResourceKey<Registry<Upgrade>> UPGRADE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "upgrade"));

    /** Tardis Refined instance of the Upgrade registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only*/
    public static final DeferredRegistry<Upgrade> UPGRADE_DEFERRED_REGISTRY = DeferredRegistry.createCustom(TardisRefined.MODID, UPGRADE_REGISTRY_KEY, true);
    /** Global instance of the Upgrade custom registry created by Tardis Refined*/
    public static final Registry<Upgrade> UPGRADE_REGISTRY = UPGRADE_DEFERRED_REGISTRY.getRegistry();

    // Base Upgrades
    public static final RegistrySupplier<Upgrade> TARDIS_XP = UPGRADE_DEFERRED_REGISTRY.register("tardis_xp", () -> new Upgrade(Items.GLASS_BOTTLE::getDefaultInstance, RegistryHelper.makeKey("tardis_xp"), Upgrade.UpgradeType.MAIN_UPGRADE)
            .setSkillPointsRequired(50).setPosition(0, 0));

    // Chameleon Circuit Upgrades
    public static final RegistrySupplier<Upgrade> ARCHITECTURE_SYSTEM = UPGRADE_DEFERRED_REGISTRY.register("architecture_system", () -> new Upgrade(BlockRegistry.ARS_EGG.get().asItem()::getDefaultInstance, TARDIS_XP, RegistryHelper.makeKey("architecture_system"), Upgrade.UpgradeType.MAIN_UPGRADE)
            .setSkillPointsRequired(50).setPosition(1, 0));

    public static final RegistrySupplier<Upgrade> CHAMELEON_CIRCUIT_SYSTEM = UPGRADE_DEFERRED_REGISTRY.register("chameleon_circuit_system", () -> new Upgrade(BlockRegistry.ROOT_SHELL_BLOCK.get().asItem()::getDefaultInstance, ARCHITECTURE_SYSTEM, RegistryHelper.makeKey("chameleon_circuit_system"), Upgrade.UpgradeType.SUB_UPGRADE)
            .setSkillPointsRequired(50).setPosition(2, 0));

    public static final RegistrySupplier<Upgrade> INSIDE_ARCHITECTURE = UPGRADE_DEFERRED_REGISTRY.register("inside_architecture", () -> new Upgrade(BlockRegistry.TERRAFORMER_BLOCK.get().asItem()::getDefaultInstance, ARCHITECTURE_SYSTEM, RegistryHelper.makeKey("inside_architecture"), Upgrade.UpgradeType.SUB_UPGRADE)
            .setSkillPointsRequired(50).setPosition(3, 0));

    // Defense Upgrades
    public static final RegistrySupplier<Upgrade> DEFENSE_SYSTEM = UPGRADE_DEFERRED_REGISTRY.register("defense_system", () -> new Upgrade(Items.DIAMOND_SWORD::getDefaultInstance, TARDIS_XP, RegistryHelper.makeKey("defense_system"), Upgrade.UpgradeType.MAIN_UPGRADE)
            .setSkillPointsRequired(50).setPosition(0, 1));

    public static final RegistrySupplier<Upgrade> MATERIALIZE_AROUND = UPGRADE_DEFERRED_REGISTRY.register("materialize_around", () -> new Upgrade(Items.GLASS_PANE::getDefaultInstance, DEFENSE_SYSTEM, RegistryHelper.makeKey("materialize_around"), Upgrade.UpgradeType.SUB_UPGRADE)
            .setSkillPointsRequired(50).setPosition(1, 1));

    // Navigation Upgrades
    public static final RegistrySupplier<Upgrade> NAVIGATION_SYSTEM = UPGRADE_DEFERRED_REGISTRY.register("navigation_system", () -> new Upgrade(Items.COMPASS::getDefaultInstance, TARDIS_XP, RegistryHelper.makeKey("navigation_system"), Upgrade.UpgradeType.MAIN_UPGRADE)
            .setSkillPointsRequired(50).setPosition(0, 2));

    public static final RegistrySupplier<Upgrade> EXPLORER = UPGRADE_DEFERRED_REGISTRY.register("explorer", () -> new IncrementUpgrade(Items.COMPASS::getDefaultInstance, NAVIGATION_SYSTEM, RegistryHelper.makeKey("explorer"), Upgrade.UpgradeType.SUB_UPGRADE).setIncrementAmount(1000)
            .setSkillPointsRequired(50).setPosition(1, 2));

    public static final RegistrySupplier<Upgrade> EXPLORER_II = UPGRADE_DEFERRED_REGISTRY.register("explorer_ii", () -> new IncrementUpgrade(Items.COMPASS::getDefaultInstance, EXPLORER, RegistryHelper.makeKey("explorer_ii"), Upgrade.UpgradeType.SUB_UPGRADE).setIncrementAmount(2500)
            .setSkillPointsRequired(50).setPosition(1.5, 2));

    public static final RegistrySupplier<Upgrade> EXPLORER_III = UPGRADE_DEFERRED_REGISTRY.register("explorer_iii", () -> new IncrementUpgrade(Items.COMPASS::getDefaultInstance, EXPLORER_II, RegistryHelper.makeKey("explorer_iii"), Upgrade.UpgradeType.SUB_UPGRADE).setIncrementAmount(5000)
            .setSkillPointsRequired(50).setPosition(2, 2));

    public static final RegistrySupplier<Upgrade> DIMENSION_TRAVEL = UPGRADE_DEFERRED_REGISTRY.register("dimension_travel", () -> new Upgrade(Blocks.NETHER_BRICKS.asItem()::getDefaultInstance, NAVIGATION_SYSTEM, RegistryHelper.makeKey("dimension_travel"), Upgrade.UpgradeType.SUB_UPGRADE)
            .setSkillPointsRequired(50).setPosition(0, 3));

    public static final RegistrySupplier<Upgrade> WAYPOINTS = UPGRADE_DEFERRED_REGISTRY.register("waypoints", () -> new Upgrade(Items.MAP::getDefaultInstance, NAVIGATION_SYSTEM, RegistryHelper.makeKey("waypoints"), Upgrade.UpgradeType.SUB_UPGRADE)
            .setSkillPointsRequired(50).setPosition(1, 3));

    public static final RegistrySupplier<Upgrade> COORDINATE_INPUT = UPGRADE_DEFERRED_REGISTRY.register("coordinate_input", () -> new Upgrade(Items.FILLED_MAP::getDefaultInstance, WAYPOINTS, RegistryHelper.makeKey("coordinate_input"), Upgrade.UpgradeType.SUB_UPGRADE)
            .setSkillPointsRequired(50).setPosition(2, 3));

    public static final RegistrySupplier<Upgrade> LANDING_PAD = UPGRADE_DEFERRED_REGISTRY.register("landing_pad", () -> new Upgrade(BlockRegistry.LANDING_PAD.get().asItem()::getDefaultInstance, NAVIGATION_SYSTEM, RegistryHelper.makeKey("landing_pad"), Upgrade.UpgradeType.SUB_UPGRADE)
            .setSkillPointsRequired(50).setPosition(3, 3));



}
