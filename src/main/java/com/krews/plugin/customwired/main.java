package com.krews.plugin.customwired;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.ItemInteraction;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadItemsManagerEvent;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.eu.habbo.plugin.events.furniture.FurniturePlacedEvent;
import com.krews.plugin.customwired.conditions.*;
import com.krews.plugin.customwired.effects.*;
import com.krews.plugin.customwired.pluswired.effects.PlusWiredEffectMatchFurniStatePosition;
import com.krews.plugin.customwired.pluswired.triggers.PlusWiredTriggerWalksOnFurni;
import com.krews.plugin.customwired.triggers.*;

import static com.eu.habbo.Emulator.*;

public class main extends HabboPlugin implements EventListener {

    public void onEnable() throws Exception {
        Emulator.getPluginManager().registerEvents(this, this);
        if(Emulator.isReady && !Emulator.isShuttingDown) {
            this.onEmulatorLoadedEvent(null);
        }
    }

    public void onDisable() throws Exception {

    }


    @EventHandler
    public void onEmulatorLoadedEvent ( EmulatorLoadedEvent e ) throws Exception {
        System.out.println("[" + ANSI_BLUE + "OFFICIAL PLUGIN" + ANSI_WHITE + "] " + "Custom Wired (1.0.0) has official loaded!");
    }


    @EventHandler
    public void onLoadItemsManager(EmulatorLoadItemsManagerEvent e) {

        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_not_battlebz", WiredConditionBattleBanzaiGameActive.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_motto_contains", WiredConditionMottoContains.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_not_battlebanzai", WiredConditionNotBattleBanzaiGameActive.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_freeze", WiredConditionFreezeGameActive.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_not_freeze", WiredConditionNotFreezeGameActive.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_habbo_has_rank", WiredConditionHabboHasRank.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_habbo_not_rank", WiredConditionHabboNotRank.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_not_habbo_has_diamonds", WiredConditionNotHabboHasDiamonds.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_not_habbo_has_credits", WiredConditionNotHabboHasCredits.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_not_habbo_has_duckets", WiredConditionNotHabboHasDuckets.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_habbo_owns_badge", WiredConditionHabboOwnsBadge.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_not_habbo_owns_badge", WiredConditionNotHabboOwnsBadge.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_habbo_is_dancing", WiredConditionHabboIsDancing.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_not_habbo_is_dancing", WiredConditionNotHabboIsDancing.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_habbo_not_owns_furni", WiredConditionHabboNotOwnsFurni.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_cnd_habbo_owns_furni",  WiredConditionHabboOwnsFurni.class));

        // Effects
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_give_diamonds", WiredEffectGiveDiamonds.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_give_credits", WiredEffectGiveCredits.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_give_duckets", WiredEffectGiveDuckets.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_give_badge", WiredEffectGiveBadge.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_remove_badge", WiredEffectRemoveBadge.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_roller_speed", WiredEffectRollerSpeed.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_open_habbo_pages", WiredEffectOpenHabboPages.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_give_achievement", WiredEffectGiveAchievement.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_forward_user", WiredEffectForwardToRoom.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_remove_furni_inventory", WiredEffectRemoveInventoryFurni.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_send_bubble", WiredEffectShowBubbleAlert.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_freeze_habbo", WiredEffectFreezeHabbo.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_unfreeze_habbo", WiredEffectUnFreezeHabbo.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_toggle_moodlight", WiredEffectToggleMoodlight.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_raise_furni", WiredEffectRaiseFurni.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_lower_furni", WiredEffectLowerFurni.class));

        // Triggers
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_trg_says_command", WiredTriggerHabboSaysCommand.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_trg_idles", WiredTriggerHabboIdle.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_trg_unidles", WiredTriggerHabboUnidle.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_trg_starts_dancing", WiredTriggerHabboStartsDancing.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_trg_stops_dancing", WiredTriggerHabboStopsDancing.class));


        // Plus Wired: Adds custom wired that replicates how wired works in plus emulator. (WHICH IS WRONG!! :()

        // TRIGGERS
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_trg_plus_walks_on_furni", PlusWiredTriggerWalksOnFurni.class));

        // CONDITIONS

        // EFFECTS
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("wf_act_plus_match_furni_state", PlusWiredEffectMatchFurniStatePosition.class));




    }

    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }
}
