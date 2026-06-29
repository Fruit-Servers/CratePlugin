package com.hazebyte.crate.api;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Server;

public class ServerVersion implements Comparable<ServerVersion> {

    private static final Map<String, ServerVersion> versions = new HashMap<>();

    // Leading numeric core (major.minor[.revision]); trailing build suffixes like "26.1.2.build.72" are ignored.
    private static final Pattern versionPattern = Pattern.compile("(\\d+)\\.(\\d+)(?:\\.(\\d+))?");

    public static ServerVersion v1_8_R1 = new ServerVersion(1, 8, 1);
    public static ServerVersion v1_8_R2 = new ServerVersion(1, 8, 2);
    public static ServerVersion v1_9_R1 = new ServerVersion(1, 9, 1);
    public static ServerVersion v1_10_R1 = new ServerVersion(1, 10, 1);
    public static ServerVersion v1_12_R1 = new ServerVersion(1, 12, 1);
    public static ServerVersion v1_13_R1 = new ServerVersion(1, 13, 1);
    public static ServerVersion v1_14_R1 = new ServerVersion(1, 14, 1);
    public static ServerVersion v1_16_R1 = new ServerVersion(1, 16, 1);
    public static ServerVersion v1_18_R0 = new ServerVersion(1, 18, 0);
    public static ServerVersion v1_20_R6 = new ServerVersion(1, 20, 6);
    public static ServerVersion v1_21_R0 = new ServerVersion(1, 21, 0);

    public static ServerVersion SERVER_MOCK = new ServerVersion(Integer.MAX_VALUE,0,0);

    private final int major;
    private final int minor;
    private final int revision;

    private ServerVersion(int major, int minor, int revision) {
        this.major = major;
        this.minor = minor;
        this.revision = revision;
    }

    public static boolean isMockServer(String versionString) {
        // versionString is unused; MockBukkit's version looks real, so check the server class instead.
        Server server = Bukkit.getServer();
        return server != null
                && server.getClass().getName().toLowerCase(Locale.ROOT).contains("mockbukkit");
    }

    public boolean isMockServer() {
        return this.equals(SERVER_MOCK);
    }

    /**
     *  1.20.6 => Major (1), Minor (20), Revision (6)
     * @return the server version
     */
    public static ServerVersion of(String versionString) {
        if (isMockServer(versionString)) {
            return ServerVersion.SERVER_MOCK;
        }

        if (versions.containsKey(versionString))
            return versions.get(versionString);

        Matcher matcher = versionPattern.matcher(versionString);
        if (!matcher.lookingAt())
            throw new IllegalArgumentException(String.format("Unable to parse server version: [%s]", versionString));

        int major = Integer.parseInt(matcher.group(1));
        int minor = Integer.parseInt(matcher.group(2));
        int revision = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;

        ServerVersion version = new ServerVersion(major, minor, revision);
        versions.put(versionString, version);
        return version;
    }

    /**
     * Returns the current running server version.
     */
    public static ServerVersion getVersion() {
        // e.g. "26.1.2.build.72-stable"; of() extracts the leading numeric core (26.1.2).
        String serverVersion = Bukkit.getServer().getBukkitVersion();
        String[] parts = serverVersion.split("-");
        return ServerVersion.of(parts[0]);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(major) +
                Integer.hashCode(minor) +
                Integer.hashCode(revision);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ServerVersion))
            return false;

        ServerVersion version = (ServerVersion) obj;
        return this.compareTo(version) == 0;
    }

    @Override
    public String toString() {
        return String.format("%d_%d_R%d", major, minor, revision);
    }

    /**
     * Returns true if the calling server version is greater than
     * the parameter.
     */
    public boolean gt(ServerVersion version) {
        return this.compareTo(version) > 0;
    }

    /**
     * Returns true if the calling server version is greater than
     * or equal to the parameter.
     */
    public boolean gte(ServerVersion version) {
        return this.compareTo(version) >= 0;
    }

    /**
     * Returns true if the calling server version is less than
     * the parameter.
     */
    public boolean lt(ServerVersion version) {
        return this.compareTo(version) < 0;
    }

    /**
     * Returns true if the calling server version is less than
     * or equal to the parameter.
     */
    public boolean lte(ServerVersion version) {
        return this.compareTo(version) <= 0;
    }

    @Override
    public int compareTo(ServerVersion serverVersion) {
        if (this.major > serverVersion.major) {
            return 1;
        } else if (this.major < serverVersion.major) {
            return -1;
        }

        if (this.minor > serverVersion.minor) {
            return 1;
        } else if (this.minor < serverVersion.minor) {
            return -1;
        }

        return Integer.compare(this.revision, serverVersion.revision);
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getRevision() {
        return revision;
    }
}
