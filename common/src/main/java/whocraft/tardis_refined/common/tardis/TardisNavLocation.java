package whocraft.tardis_refined.common.tardis;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.util.Platform;

/**
 * TardisNavLocation
 * Co-ordinates that represent position, rotation, level and name.
 **/
public class TardisNavLocation {

    public static final TardisNavLocation ORIGIN = new TardisNavLocation(BlockPos.ZERO, Direction.NORTH, Level.OVERWORLD);

    private BlockPos position;
    private Direction direction;
    private ServerLevel level;

    private ResourceKey<Level> dimensionKey;

    private String name = ""; //To be used for waypoints later on

    /**
     * @param position  World co-ordinate
     * @param direction Rotation/Facing direction.
     * @param level     ResourceKey of the desired level.
     **/
    public TardisNavLocation(BlockPos position, Direction direction, ServerLevel level) {
        this.position = position;
        this.direction = direction;
        this.level = level;
        if (level != null) {
            this.dimensionKey = level.dimension();
        }

    }

    /**
     * <br> Alternate Constructor ONLY for static references.
     * <br> DO NOT use for logic E.g. Using methods from the Level instance
     * <br> This is because this version doesn't have a {@link Level}  reference
     *
     * @param position
     * @param direction
     * @param level
     */
    public TardisNavLocation(BlockPos position, Direction direction, ResourceKey<Level> level) {
        this.position = position;
        this.direction = direction;
        this.dimensionKey = level;
    }

    public ServerLevel getLevel() {
        if (this.level != null) {
            this.dimensionKey = this.level.dimension();
            return Platform.getServer().getLevel(dimensionKey);
        }

        return Platform.getServer().getLevel(Level.OVERWORLD);
    }

    public void setLevel(ServerLevel level) {
        this.dimensionKey = level.dimension();
        this.level = level;
    }

    public ResourceKey<Level> getDimensionKey() {
        return dimensionKey;
    }

    public BlockPos getPosition() {
        return position;
    }

    public TardisNavLocation setPosition(BlockPos pos) {
        this.position = pos;
        return this;
    }

    public Direction getDirection() {
        return direction;
    }

    public TardisNavLocation setDirection(Direction dir) {
        this.direction = dir;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static TardisNavLocation deserialise(CompoundTag tag) {
        TardisNavLocation loc = new TardisNavLocation(BlockPos.of(tag.getLong("pos")), Direction.values()[tag.getInt("dir")], ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(tag.getString("dim"))));
        if (tag.contains("name"))
            loc.setName(tag.getString("name"));
        return loc;
    }

    public CompoundTag serialise() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("pos", this.position.asLong());
        tag.putString("dim", this.dimensionKey.location().toString());
        tag.putInt("dir", this.direction.ordinal());
        tag.putString("name", this.name);
        return tag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof TardisNavLocation))
            return false;
        TardisNavLocation other = (TardisNavLocation) obj;
        if (this.position.equals(other.position) && this.dimensionKey.location().equals(other.dimensionKey.location()) && this.direction.equals(other.direction)) {
            if (this.name != null) {
                if (other.name != null) {
                    return this.name.equals(other.name);
                }
                return false;
            }
            if (other.name != null) {
                return false;
            }
            return true;
        }
        return false;
    }

}
