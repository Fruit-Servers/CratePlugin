package com.hazebyte.crate.cratereloaded.provider.effect.slikey;

import de.slikey.effectlib.Effect;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Best-effort utility for discovering built-in EffectLib effect names
 * by scanning the EffectLib jar. Used only for diagnostics/logging.
 */
public final class SlikeyEffectNames {

    private static final String EFFECT_PKG_PATH = "de/slikey/effectlib/effect/";
    private SlikeyEffectNames() {}

    /**
     * @param classLoader the plugin/classloader to use.
     * @return sorted list of effect names. Empty if discovery fails.
     */
    public static List<String> discoverEffectNames(ClassLoader classLoader) {
        try {
            URL url = classLoader.getResource(EFFECT_PKG_PATH);
            if (url == null) return Collections.emptyList();

            String u = url.toString();
            if (!u.startsWith("jar:")) return Collections.emptyList();

            String jarPart = u.substring("jar:".length(), u.indexOf("!"));
            File jarFile = new File(new URI(jarPart));

            List<String> names = new ArrayList<>();

            try (JarFile jar = new JarFile(jarFile)) {
                Enumeration<JarEntry> entries = jar.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();

                    if (!name.startsWith(EFFECT_PKG_PATH)) continue;
                    if (!name.endsWith(".class")) continue;
                    if (name.contains("$")) continue;

                    String className =
                            name.substring(0, name.length() - ".class".length()).replace('/', '.');

                    Class<?> clazz;
                    try {
                        clazz = Class.forName(className, false, classLoader);
                    } catch (Throwable ignored) {
                        continue;
                    }

                    if (!Effect.class.isAssignableFrom(clazz)) continue;
                    if (java.lang.reflect.Modifier.isAbstract(clazz.getModifiers())) continue;

                    String simple = clazz.getSimpleName();
                    if (simple.endsWith("Effect")) {
                        simple = simple.substring(0, simple.length() - "Effect".length());
                    }

                    names.add(simple);
                }
            }

            names.sort(String.CASE_INSENSITIVE_ORDER);
            return Collections.unmodifiableList(names);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }
}
