package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.TardisInternalDoor;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.hum.HumEntry;
import whocraft.tardis_refined.common.hum.TardisHums;
import whocraft.tardis_refined.common.protection.ProtectedZone;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.TRTeleporter;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.constants.TardisDimensionConstants;

import java.util.ArrayList;
import java.util.List;

public class TardisInteriorManager extends BaseHandler {
    private final TardisLevelOperator operator;
    private boolean isWaitingToGenerate = false;
    private boolean isGeneratingDesktop = false;
    private boolean hasGeneratedCorridors = false;
    private int interiorGenerationCooldown = 0;
    private BlockPos corridorAirlockCenter;
    private DesktopTheme preparedTheme, currentTheme = TardisDesktops.DEFAULT_OVERGROWN_THEME;

    // Airlock systems.
    private boolean processingWarping = false;
    private int airlockCountdownSeconds = 3;
    private int airlockTimerSeconds = 5;

    private HumEntry humEntry = TardisHums.getDefaultHum();

    public static final BlockPos STATIC_CORRIDOR_POSITION = new BlockPos(1013, 99, 5);

    private double fuelForIntChange = 500; // The amount of fuel required to change interior

    public DesktopTheme preparedTheme() {
        return preparedTheme;
    }

    public TardisInteriorManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public boolean isGeneratingDesktop() {
        return this.isGeneratingDesktop;
    }

    public boolean isWaitingToGenerate() {
        return this.isWaitingToGenerate;
    }

    public int getInteriorGenerationCooldown() {
        return this.interiorGenerationCooldown / 20;
    }

    public ProtectedZone[] unbreakableZones() {

        if (!hasGeneratedCorridors || corridorAirlockCenter == null) return new ProtectedZone[]{};

        ProtectedZone ctrlRoomAirlck = new ProtectedZone(corridorAirlockCenter.below(2).north(2).west(3), corridorAirlockCenter.south(3).east(3).above(6), "control_room_airlock");
        ProtectedZone hubAirlck = new ProtectedZone(STATIC_CORRIDOR_POSITION.below(2).north(2).west(3), STATIC_CORRIDOR_POSITION.south(3).east(3).above(6), "hub_airlock");
        ProtectedZone arsRoom = new ProtectedZone(new BlockPos(1051, 97, 6), new BlockPos(1023, 118, 36), "ars_room");

        return new ProtectedZone[]{ctrlRoomAirlck, hubAirlck, arsRoom};
    }

    public DesktopTheme currentTheme() {
        return currentTheme;
    }

    public TardisInteriorManager setCurrentTheme(DesktopTheme currentTheme) {
        this.currentTheme = currentTheme;
        return this;
    }

    public boolean isCave() {
        return currentTheme == TardisDesktops.DEFAULT_OVERGROWN_THEME;
    }

    @Override
    public void tick() {

    }

    @Override
    public CompoundTag saveData(CompoundTag tag) {
        tag.putBoolean(NbtConstants.TARDIS_IM_IS_WAITING_TO_GENERATE, this.isWaitingToGenerate);
        tag.putBoolean(NbtConstants.TARDIS_IM_GENERATING_DESKTOP, this.isGeneratingDesktop);
        tag.putInt(NbtConstants.TARDIS_IM_GENERATION_COOLDOWN, this.interiorGenerationCooldown);
        tag.putBoolean(NbtConstants.TARDIS_IM_GENERATED_CORRIDORS, this.hasGeneratedCorridors);

        if (this.corridorAirlockCenter != null) {
            tag.put(NbtConstants.TARDIS_IM_AIRLOCK_CENTER, NbtUtils.writeBlockPos(this.corridorAirlockCenter));
        }


        tag.putString(NbtConstants.TARDIS_IM_PREPARED_THEME, this.preparedTheme != null ? this.preparedTheme.getIdentifier().toString() : "");
        tag.putString(NbtConstants.TARDIS_IM_CURRENT_THEME, this.currentTheme.getIdentifier().toString());
        tag.putString(NbtConstants.TARDIS_CURRENT_HUM, this.humEntry.getIdentifier().toString());

        tag.putDouble(NbtConstants.TARDIS_IM_FUEL_FOR_INT_CHANGE, this.fuelForIntChange);

        return tag;
    }

    @Override
    public void loadData(CompoundTag tag) {
        this.isWaitingToGenerate = tag.getBoolean(NbtConstants.TARDIS_IM_IS_WAITING_TO_GENERATE);
        this.isGeneratingDesktop = tag.getBoolean(NbtConstants.TARDIS_IM_GENERATING_DESKTOP);
        this.interiorGenerationCooldown = tag.getInt(NbtConstants.TARDIS_IM_GENERATION_COOLDOWN);
        this.hasGeneratedCorridors = tag.getBoolean(NbtConstants.TARDIS_IM_GENERATED_CORRIDORS);
        this.preparedTheme = TardisDesktops.getDesktopById(new ResourceLocation(tag.getString(NbtConstants.TARDIS_IM_PREPARED_THEME)));
        this.currentTheme = tag.contains(NbtConstants.TARDIS_IM_CURRENT_THEME) ? TardisDesktops.getDesktopById(new ResourceLocation((NbtConstants.TARDIS_IM_CURRENT_THEME))) : preparedTheme;
        this.corridorAirlockCenter = NbtUtils.readBlockPos(tag.getCompound(NbtConstants.TARDIS_IM_AIRLOCK_CENTER));
        this.humEntry = TardisHums.getHumById(new ResourceLocation(tag.getString(NbtConstants.TARDIS_CURRENT_HUM)));

        this.fuelForIntChange = tag.getDouble(NbtConstants.TARDIS_IM_FUEL_FOR_INT_CHANGE);
        if (!tag.contains(NbtConstants.TARDIS_IM_FUEL_FOR_INT_CHANGE)) {
            this.fuelForIntChange = 500; // Default
        }
    }


    public HumEntry getHumEntry() {
        return humEntry;
    }

    public void setHumEntry(HumEntry humEntry) {
        this.humEntry = humEntry;
    }

    public void tick(ServerLevel level) {

        if (this.isWaitingToGenerate) {
            if (level.random.nextInt(30) == 0) {
                level.playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 5.0F + level.random.nextFloat(), level.random.nextFloat() * 0.7F + 0.3F);
            }

            if (level.random.nextInt(100) == 0) {
                level.playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 15.0F + level.random.nextFloat(), 0.1f);
            }

            if (level.players().isEmpty()) {
                this.operator.getExteriorManager().triggerShellRegenState();
                operator.setDoorClosed(true);
                generateDesktop(this.preparedTheme);

                this.isWaitingToGenerate = false;
                this.isGeneratingDesktop = true;
            }
        }

        if (this.isGeneratingDesktop) {

            if (!level.isClientSide()) {
                interiorGenerationCooldown--;
            }

            if (interiorGenerationCooldown == 0) {
                this.operator.setShellTheme((this.operator.getAestheticHandler().getShellTheme() != null) ? operator.getAestheticHandler().getShellTheme() : ShellTheme.FACTORY.getId(), true);
                this.operator.getExteriorManager().placeExteriorBlock(operator, operator.getExteriorManager().getLastKnownLocation());
                this.isGeneratingDesktop = false;
            }

            if (level.getGameTime() % 60 == 0) {
                operator.getExteriorManager().playSoundAtShell(SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 1.0F + operator.getExteriorManager().getLastKnownLocation().getLevel().getRandom().nextFloat(), 0.1f);
            }
        }


        /// Airlock Logic

        // Check if a player is in the radius of either airlock points
        if (!processingWarping) {
            if (level.getGameTime() % 20 == 0) {
                // Dynamic desktop position.
                List<LivingEntity> desktopEntities = getAirlockEntities(level);
                List<LivingEntity> corridorEntities = getCorridorEntities(level);

                if (!desktopEntities.isEmpty() || !corridorEntities.isEmpty()) {
                    airlockCountdownSeconds--;
                    if (airlockCountdownSeconds <= 0) {

                        this.processingWarping = true;
                        airlockCountdownSeconds = 10;
                        this.airlockTimerSeconds = 0;

                        // Lock the doors.
                        BlockPos desktopDoorPos = corridorAirlockCenter.north(2);
                        if (level.getBlockEntity(desktopDoorPos) instanceof BulkHeadDoorBlockEntity bulkHeadDoorBlockEntity) {
                            bulkHeadDoorBlockEntity.toggleDoor(level, desktopDoorPos, level.getBlockState(desktopDoorPos), false);
                            level.setBlock(desktopDoorPos, level.getBlockState(desktopDoorPos).setValue(BulkHeadDoorBlock.LOCKED, true), Block.UPDATE_CLIENTS);
                        }

                        BlockPos corridorDoorBlockPos = TardisDimensionConstants.CORRIDOR_AIRLOCK_DOOR_POS;
                        if (level.getBlockEntity(corridorDoorBlockPos) instanceof BulkHeadDoorBlockEntity bulkHeadDoorBlockEntity) {
                            bulkHeadDoorBlockEntity.toggleDoor(level, corridorDoorBlockPos, level.getBlockState(corridorDoorBlockPos), false);
                            level.setBlock(corridorDoorBlockPos, level.getBlockState(corridorDoorBlockPos).setValue(BulkHeadDoorBlock.LOCKED, true), Block.UPDATE_CLIENTS);
                        }
                    }
                } else {
                    this.processingWarping = false;
                    this.airlockCountdownSeconds = 3;
                    this.airlockTimerSeconds = 0;
                }

            }
        }

        if (processingWarping) {


            if (level.getGameTime() % 20 == 0) {

                RandomSource rand = level.getRandom();
                for (ProtectedZone protectedZone : unbreakableZones()) {
                    if (!protectedZone.getName().contains("_airlock")) continue;
                    BlockPos.betweenClosedStream(protectedZone.getArea()).forEach(position -> {
                        double velocityX = (rand.nextDouble() - 0.5) * 0.02;
                        double velocityY = (rand.nextDouble() - 0.5) * 0.02;
                        double velocityZ = (rand.nextDouble() - 0.5) * 0.02;

                        level.sendParticles(ParticleTypes.CLOUD, position.getX(), position.getY(), position.getZ(), 2, velocityX, velocityY, velocityZ, velocityZ);
                    });
                }


                if (airlockTimerSeconds == 1) {
                    level.playSound(null, corridorAirlockCenter, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 5, 0.25f);
                    level.playSound(null, STATIC_CORRIDOR_POSITION, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 5, 0.25f);
                }

                if (airlockTimerSeconds == 3) {
                    List<LivingEntity> desktopEntities = getAirlockEntities(level);
                    List<LivingEntity> corridorEntities = getCorridorEntities(level);

                    desktopEntities.forEach(x -> {
                        Vec3 offsetPos = x.position().subtract(Vec3.atCenterOf(corridorAirlockCenter));
                        TRTeleporter.performTeleport(x, level, STATIC_CORRIDOR_POSITION.getX() + offsetPos.x() + 0.5f, STATIC_CORRIDOR_POSITION.getY() + offsetPos.y() + 0.5f, STATIC_CORRIDOR_POSITION.getZ() + offsetPos.z() + 0.5f, x.getYRot(), x.getXRot());
                    });

                    corridorEntities.forEach(x -> {
                        Vec3 offsetPos = x.position().subtract(Vec3.atCenterOf(STATIC_CORRIDOR_POSITION));
                        TRTeleporter.performTeleport(x, level, corridorAirlockCenter.getX() + offsetPos.x() + 0.5f, corridorAirlockCenter.getY() + offsetPos.y() + 0.5f, corridorAirlockCenter.getZ() + offsetPos.z() + 0.5f, x.getYRot(), x.getXRot());
                    });
                }

                if (airlockTimerSeconds == 5) {
                    this.processingWarping = false;
                    this.airlockTimerSeconds = 20;
                    BlockPos desktopDoorPos = corridorAirlockCenter.north(2);
                    if (level.getBlockEntity(desktopDoorPos) instanceof BulkHeadDoorBlockEntity bulkHeadDoorBlockEntity) {
                        bulkHeadDoorBlockEntity.toggleDoor(level, desktopDoorPos, level.getBlockState(desktopDoorPos), true);
                        level.setBlock(desktopDoorPos, level.getBlockState(desktopDoorPos).setValue(BulkHeadDoorBlock.LOCKED, false), Block.UPDATE_CLIENTS);
                    }

                    BlockPos corridorDoorBlockPos = TardisDimensionConstants.CORRIDOR_AIRLOCK_DOOR_POS;
                    if (level.getBlockEntity(corridorDoorBlockPos) instanceof BulkHeadDoorBlockEntity bulkHeadDoorBlockEntity) {
                        bulkHeadDoorBlockEntity.toggleDoor(level, corridorDoorBlockPos, level.getBlockState(corridorDoorBlockPos), true);
                        level.setBlock(corridorDoorBlockPos, level.getBlockState(corridorDoorBlockPos).setValue(BulkHeadDoorBlock.LOCKED, false), Block.UPDATE_CLIENTS);
                    }
                }


                airlockTimerSeconds++;
            }
        }
    }

    public List<LivingEntity> getCorridorEntities(Level level) {
        return level.getEntitiesOfClass(LivingEntity.class, new AABB(STATIC_CORRIDOR_POSITION.north(2).west(2), STATIC_CORRIDOR_POSITION.south(2).east(2).above(4)));
    }

    public List<LivingEntity> getAirlockEntities(Level level) {

        if (corridorAirlockCenter == null) {
            return new ArrayList<>();
        }

        return level.getEntitiesOfClass(LivingEntity.class, new AABB(corridorAirlockCenter.north(2).west(2), corridorAirlockCenter.south(2).east(2).above(4)));
    }

    public boolean isInAirlock(LivingEntity livingEntity) {

        if (!hasGeneratedCorridors) return false;

        List<LivingEntity> airlock = getAirlockEntities(livingEntity.level());
        List<LivingEntity> corridor = getCorridorEntities(livingEntity.level());

        return airlock.contains(livingEntity) || corridor.contains(livingEntity);
    }

    public void generateDesktop(DesktopTheme theme) {

        if (operator.getLevel() instanceof ServerLevel serverLevel) {

            // Remove Tardis Interior DOor
            TardisInternalDoor tardisInternalDoor = this.operator.getInternalDoor();
            if (tardisInternalDoor != null) {
                serverLevel.removeBlock(tardisInternalDoor.getDoorPosition(), false);
            }

            if (theme != TardisDesktops.DEFAULT_OVERGROWN_THEME) {
                // Generate Corridors
                if (!this.hasGeneratedCorridors) {
                    TardisArchitectureHandler.generateEssentialCorridors(serverLevel); // This causes a little lag, could be worth a fix.
                    this.hasGeneratedCorridors = true;
                }
            }

            // Generate Desktop Interior
            TardisArchitectureHandler.generateDesktop(serverLevel, theme);

            setCurrentTheme(theme);

        }
    }

    public void setCorridorAirlockCenter(BlockPos center) {
        this.corridorAirlockCenter = center;
    }

    public BlockPos getCorridorAirlockCenter() {
        return this.corridorAirlockCenter;
    }

    public void prepareDesktop(DesktopTheme theme) {
        this.preparedTheme = theme;
        this.isWaitingToGenerate = true;
        this.interiorGenerationCooldown = 1200; // Make this more independent.
    }

    public void cancelDesktopChange() {
        this.preparedTheme = null;
        this.isWaitingToGenerate = false;
    }

    /**
     * Returns whether a Tardis has enough fuel to perform an interior change
     * @return true if the Tardis has enough fuel
     */
    public boolean hasEnoughFuel() {
        return this.operator.getPilotingManager().getFuel() >= this.getRequiredFuel();
    }

    /**
     * The amount of fuel required to change the interior
     * @return double amount of fuel to be removed
     */
    public double getRequiredFuel() {
        return this.fuelForIntChange;
    }

    /**
     * Sets the amount of fuel required to change the interior
     * @param fuel the amount of fuel
     */
    private void setRequiredFuel(double fuel) {
        this.fuelForIntChange = fuel;
    }
}
