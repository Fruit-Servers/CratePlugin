package com.hazebyte.crate;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

public class BukkitTest {

    protected static ServerMock server;
    protected static CorePlugin plugin;

    @BeforeAll
    protected static void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(CorePlugin.class);
    }

    @AfterAll
    protected static void tearDown() {
        MockBukkit.unmock();
    }
}
