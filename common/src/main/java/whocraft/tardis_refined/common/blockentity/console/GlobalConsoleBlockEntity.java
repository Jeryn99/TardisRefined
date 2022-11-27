package whocraft.tardis_refined.common.blockentity.console;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.BlockEntityRegistry;
import whocraft.tardis_refined.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalConsoleBlockEntity extends BlockEntity implements BlockEntityTicker<GlobalConsoleBlockEntity> {

    private boolean isDirty = true;
    private List<ControlEntity> controlEntityList = new ArrayList<>();

    public GlobalConsoleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_CONSOLE_BLOCK.get(), blockPos, blockState);
    }

    public void spawnControlEntities() {
        // Things needed.

        if (getLevel() instanceof ServerLevel level) {
            ConsoleTheme theme = getBlockState().getValue(GlobalConsoleBlock.CONSOLE);
            ControlSpecification[] controls = theme.getControlSpecificationList();
            Arrays.stream(controls).toList().forEach(control -> {
                // Spawn a control!
                ControlEntity controlEntity = EntityRegistry.CONTROL_ENTITY.get().create(getLevel());
                controlEntity.setControlSpecification(control);

                BlockPos controlPosition = getBlockPos().offset(control.offsetPosition);
                Vec3 vector3 = new Vec3(controlPosition.getX() + 0.5f, controlPosition.getY(), controlPosition.getZ() + 0.5f);
                controlEntity.setPos(vector3);
                level.addFreshEntity(controlEntity);
                controlEntityList.add(controlEntity);
            });
        }



        this.isDirty = false;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        controlEntityList.forEach(Entity::kill);
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, GlobalConsoleBlockEntity blockEntity) {
        if (this.isDirty) {
            spawnControlEntities();}
    }
}
