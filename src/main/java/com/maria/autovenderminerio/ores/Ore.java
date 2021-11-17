package com.maria.autovenderminerio.ores;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Ore {

    private ItemStack ore;
    private List<String> rewards;
    private double price;

}
