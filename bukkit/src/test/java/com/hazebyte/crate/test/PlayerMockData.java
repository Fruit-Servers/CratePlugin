package com.hazebyte.crate.test;

import com.hazebyte.crate.api.util.ItemBuilder;
import org.bukkit.Material;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

public class PlayerMockData {

    public static PlayerMock createPlayerWithFullInventory(ServerMock serverMock) {
        PlayerMock playerMock = serverMock.addPlayer();

        for (int i = 0; i < playerMock.getInventory().getSize(); i++) {
            playerMock.getInventory().setItem(i, new ItemBuilder(Material.DIAMOND).asItemStack());
        }
        return playerMock;
    }
}
