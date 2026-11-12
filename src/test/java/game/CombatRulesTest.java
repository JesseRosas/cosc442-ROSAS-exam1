
package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CombatRulesTest {

    // ISP and BVA tests for CombatRules
    @ParameterizedTest(name = "{index}: HP={0}, amount={1}, expectedHP={2}, dead={3}")
    @CsvSource({
        "50,  20,  70, false",  // Normal HP increase (H2)
        "50, -20,  30, false",  // Normal HP decrease (H2)
        "50,   0,  50, false",  // No HP change (H2)
        " 2,  -1,   1, false",  // Lower alive boundary (H2)
        " 2,  -2,   0, true",   // Death boundary (H1)
        " 2,  -3,  -1, true",   // Below zero HP (H1)
        "99,   1, 100, false",  // Maximum HP boundary (H3)
        "99,   2, 100, false"   // HP capped at max (H4)
    })
    void testCombatRules(int currentHp, int amount, int expectedHp, boolean expectedDead) {

        int actualHp = CombatRules.calculateNewHp(currentHp, 100, amount);

        assertEquals(expectedHp, actualHp,
                "Calculated HP should match expected HP");

        assertEquals(expectedDead, CombatRules.isDead(actualHp),
                "Death status should match expected result");
    }
}

