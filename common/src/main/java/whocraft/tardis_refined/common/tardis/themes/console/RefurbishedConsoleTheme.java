package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class RefurbishedConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-1.22f, 0.63f, 0.71f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(1.43f, 0.66f, -0.29f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(1.18f, 0.69f, -0.51f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(0.96f, 0.75f, -0.75f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(0.96f, 0.64f, -1.05f), EntityDimensions.scalable(0.12f, 0.12f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(1.68f, 0.50f, 0.11f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-1.07f, 0.56f, -0.65f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.33f, 0.50f, -1.51f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.89f, 0.94f, -0.07f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(1.30f, 0.50f, -0.78f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-0.48f, 0.50f, -1.51f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.READOUT, new Vector3f(0.80f, 0.71f, 0.93f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.HANDBRAKE, new Vector3f(0.24f, 0.69f, -1.20f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.04f, 0.50f, -1.49f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.01f, 0.66f, -1.34f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.47f, 0.56f, -1.29f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.47f, 0.50f, -1.46f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.36f, 0.50f, -1.46f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.26f, 0.50f, -1.46f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.11f, 0.51f, -1.05f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.16f, 0.51f, -0.95f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.22f, 0.51f, -0.86f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.43f, 0.51f, -0.49f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.38f, 0.51f, -0.59f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.49f, 0.51f, -0.40f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.24f, 0.59f, 0.46f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.32f, 0.59f, 0.32f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.21f, 0.70f, 0.21f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.96f, 0.70f, 0.66f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.05f, 0.51f, 1.21f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.94f, 0.51f, 1.37f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.85f, 0.55f, 1.24f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.49f, 0.51f, 0.33f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.61f, 0.49f, 0.33f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.57f, 0.49f, 0.40f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.56f, 0.53f, 0.26f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.35f, 0.51f, 1.41f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.49f, 0.51f, 1.41f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.21f, 0.49f, 1.50f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.29f, 0.49f, 1.50f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.35f, 0.49f, 0.66f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.27f, 0.49f, 0.78f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.22f, 0.49f, 0.88f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.20f, 0.62f, -0.35f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.90f, 0.62f, -0.89f), EntityDimensions.scalable(0.12f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.64f, 0.46f, -0.27f), EntityDimensions.scalable(0.12f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.02f, 0.46f, -1.29f), EntityDimensions.scalable(0.12f, 0.12f))

        };
    }
}
