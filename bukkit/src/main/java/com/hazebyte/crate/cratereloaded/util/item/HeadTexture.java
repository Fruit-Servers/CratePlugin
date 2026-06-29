package com.hazebyte.crate.cratereloaded.util.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import java.util.UUID;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Applies and reads base64-encoded skull textures via the Paper profile API. The textures are stored
 * as a "textures" profile property whose value is the base64-encoded skin payload.
 */
public class HeadTexture {

    private static final String TEXTURES = "textures";

    private HeadTexture() {}

    /**
     * Applies a base64-encoded texture to the given skull meta.
     *
     * @param meta the skull meta to modify
     * @param base64 the base64-encoded value of the "textures" profile property
     * @return the same skull meta, with the texture applied
     */
    public static SkullMeta applyToMeta(@NonNull SkullMeta meta, @NonNull String base64) {
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), "");
        profile.setProperty(new ProfileProperty(TEXTURES, base64));
        meta.setPlayerProfile(profile);
        return meta;
    }

    /**
     * Reads the base64-encoded texture value from the given skull meta.
     *
     * @param meta the skull meta to read from
     * @return the base64 "textures" value, or {@code null} if the meta has no profile or texture
     */
    public static String fromMeta(@NonNull SkullMeta meta) {
        PlayerProfile profile = meta.getPlayerProfile();
        if (profile == null) {
            return null;
        }
        return profile.getProperties().stream()
                .filter(property -> TEXTURES.equals(property.getName()))
                .map(ProfileProperty::getValue)
                .findFirst()
                .orElse(null);
    }
}
