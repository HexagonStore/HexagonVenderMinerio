package com.maria.autovenderminerio.rewards;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Reward {

    private String titleLine1;
    private String titleLine2;
    private Sound rewardSound;
    private List<String> commands;
    private int chance;

}
