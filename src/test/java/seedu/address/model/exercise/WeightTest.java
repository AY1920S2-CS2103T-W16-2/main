package seedu.address.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Weight(null));
    }

    @Test
    public void constructor_invalidWeight_throwsIllegalArgumentException() {
        String invalidWeight = "";
        assertThrows(IllegalArgumentException.class, () -> new Weight(invalidWeight));
    }

    @Test
    public void isValidWeight() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Weight.isValidWeight(null));

        // invalid phone numbers
        assertFalse(Weight.isValidWeight("")); // empty string
        assertFalse(Weight.isValidWeight(" ")); // spaces only
        assertFalse(Weight.isValidWeight("phone")); // non-numeric
        assertFalse(Weight.isValidWeight("9011p041")); // alphabets within digits
        assertFalse(Weight.isValidWeight("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Weight.isValidWeight("911"));
        assertTrue(Weight.isValidWeight("93121534"));
        assertTrue(Weight.isValidWeight("124293842033123")); // long numbers
    }
}
