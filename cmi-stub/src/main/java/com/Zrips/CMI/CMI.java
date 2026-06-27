package com.Zrips.CMI;

import com.Zrips.CMI.Modules.Holograms.HologramManager;

/**
 * Compile-only API stub for the CMI plugin (https://www.zrips.net/CMI/) — not the real CMI, which the
 * server supplies at runtime. Consumed at {@code provided} scope, so this jar is never bundled; bodies
 * throw to fail fast if ever invoked. See the {@code cmi-stub} module.
 */
public class CMI {

    public static CMI getInstance() {
        throw new UnsupportedOperationException("CMI API stub — the real CMI plugin provides this at runtime");
    }

    public HologramManager getHologramManager() {
        throw new UnsupportedOperationException("CMI API stub — the real CMI plugin provides this at runtime");
    }
}
