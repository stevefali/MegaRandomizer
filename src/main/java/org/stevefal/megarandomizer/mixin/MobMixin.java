package org.stevefal.megarandomizer.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megamobs.IMegaMob;
import org.stevefal.megarandomizer.megamobs.RandomSpawns;

@Mixin(Mob.class)
public abstract class MobMixin implements IMegaMob {


    /**
     * Override the behavior in IForgeEntity to return the MobCategory of the mapped vanilla EntityType
     * so that the mob cap counts are satisfied as though the randomized mob is the vanilla one.
     */
    public MobCategory getClassification(boolean forSpawnCount) {
        Entity self = (Entity) (Object) this;
        try {
            if (forSpawnCount &&
                    self.getServer().getGameRules().getBoolean(MegaGameRules.RULE_DO_RANDOM_SPAWNS)) {
                return RandomSpawns.getVanillaEntityType(self.getType()).getCategory();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return self.getType().getCategory();
    }


    @Shadow
    private boolean persistenceRequired;

    @Unique
    @Override
    public void setMegaMobIsPersistenceRequired(boolean isPersistenceRequired) {
        this.persistenceRequired = isPersistenceRequired;
    }
}
