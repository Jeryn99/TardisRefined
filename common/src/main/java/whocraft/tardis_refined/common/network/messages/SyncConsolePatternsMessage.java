package whocraft.tardis_refined.common.network.messages;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.patterns.Pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncConsolePatternsMessage extends MessageS2C {

    private final Map<ConsoleTheme, List<Pattern<ConsoleTheme>>> patterns;

    public SyncConsolePatternsMessage(Map<ConsoleTheme, List<Pattern<ConsoleTheme>>> patterns) {
        this.patterns = patterns;
    }

    public SyncConsolePatternsMessage(FriendlyByteBuf buf) {
        int size = buf.readInt();
        patterns = new HashMap<>();
        for (int i = 0; i < size; i++) {
            ConsoleTheme theme = ConsoleTheme.valueOf(buf.readUtf());
            List<Pattern<ConsoleTheme>> patternList = new ArrayList<>();
            int patternSize = buf.readInt();
            for (int j = 0; j < patternSize; j++) {
                patternList.add(getPattern(buf));
            }
            patterns.put(theme, patternList);
        }
    }

    private static Pattern<ConsoleTheme> getPattern(FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation(); // ID
        ResourceLocation texture = buf.readResourceLocation(); // texture
        String name = buf.readUtf(); // name
        ConsoleTheme theme = ConsoleTheme.valueOf(buf.readUtf()); // theme
        boolean glow = buf.readBoolean(); // name

        Pattern<ConsoleTheme> consolePattern = new Pattern<ConsoleTheme>(theme, id, texture);
        consolePattern.setName(name);
        consolePattern.setEmissive(glow);
        return consolePattern;
    }

    @Override
    public MessageType getType() {
        return TardisNetwork.SYNC_CONSOLE_PATTERNS;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(patterns.size());
        patterns.forEach((consoleTheme, patterns) -> {
            buf.writeUtf(consoleTheme.name());
            buf.writeInt(patterns.size());
            for (Pattern<ConsoleTheme> pattern : patterns) {
                writePattern(pattern, buf);
            }
        });
    }

    private void writePattern(Pattern<ConsoleTheme> pattern, FriendlyByteBuf buf) {
        buf.writeResourceLocation(pattern.id()); // ID
        buf.writeResourceLocation(pattern.texture()); // texture
        buf.writeUtf(pattern.name()); // name
        buf.writeUtf(pattern.theme().name()); // theme
        buf.writeBoolean(pattern.emissive()); // glow
    }

    @Override
    public void handle(MessageContext context) {
        patterns.forEach((consoleTheme, patterns) -> {
            for (Pattern<ConsoleTheme> pattern : patterns) {
                ConsolePatterns.addPattern(consoleTheme, pattern);
            }
        });
    }
}