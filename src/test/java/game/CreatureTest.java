package game;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreatureTest {
    private World world;
    private Creature creature;

    @BeforeEach
    void setUp() {
        Tile[][][] tiles = new Tile[3][3][1];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                tiles[x][y][0] = Tile.FLOOR;
            }
        }

        world = new World(tiles);

        creature = new Creature(world, '@', Color.GRAY, "josh", 100, 10, 5);

        creature.x = 1;
        creature.y = 1;
        creature.z = 0;

        // Initalize the creature's AI
        new CreatureAi(creature);
    }

    // Exam Q2c Nonfatal damage (false branch)
    // Keeps Creature Alive
    @Test
    void nonFatalDamage() {
        creature.modifyHp(-20, "Damage");

        assertEquals(80, creature.hp());
        assertFalse(CombatRules.isDead(creature.hp()));
    }

    // Exam Q2c Fatal damage (true branch)
    // Removes Creature
    @Test
    void fatalDamage() {
        creature.modifyHp(-100, "Fatal damage");

        assertEquals(0, creature.hp());
        assertNull(world.creature(1, 1, 0));
        assertNotNull(world.item(1, 1, 0));
    }


    // Exam Q3c Mutant removal test @ world.remove() 
    @Test
    void deadCreatureIsRemovedFromWorld() {
        // Register creature in the world
        world.add(creature);

        // Confirm creature exists before death
        assertSame(creature, world.creature(1, 1, 0));

        // Kill the creature
        creature.modifyHp(-100, "Fatal damage");

        // Confirm creature has died
        assertEquals(0, creature.hp());

        // Confirm creature was removed from the world
        assertNull(world.creature(1, 1, 0));
    }


    
}
