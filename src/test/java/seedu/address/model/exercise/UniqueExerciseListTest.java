package seedu.address.model.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExercises.ALICE;
import static seedu.address.testutil.TypicalExercises.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.exercise.exceptions.DuplicateExerciseException;
import seedu.address.model.exercise.exceptions.ExerciseNotFoundException;
import seedu.address.testutil.ExerciseBuilder;

public class UniqueExerciseListTest {

    private final UniqueExerciseList uniqueExerciseList = new UniqueExerciseList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueExerciseList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueExerciseList.add(ALICE);
        assertTrue(uniqueExerciseList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueExerciseList.add(ALICE);
        Exercise editedAlice = new ExerciseBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueExerciseList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueExerciseList.add(ALICE);
        assertThrows(DuplicateExerciseException.class, () -> uniqueExerciseList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setExercise(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setExercise(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(ExerciseNotFoundException.class, () -> uniqueExerciseList.setExercise(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueExerciseList.add(ALICE);
        uniqueExerciseList.setExercise(ALICE, ALICE);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(ALICE);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueExerciseList.add(ALICE);
        Exercise editedAlice = new ExerciseBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueExerciseList.setExercise(ALICE, editedAlice);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(editedAlice);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueExerciseList.add(ALICE);
        uniqueExerciseList.setExercise(ALICE, BOB);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(BOB);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueExerciseList.add(ALICE);
        uniqueExerciseList.add(BOB);
        assertThrows(DuplicateExerciseException.class, () -> uniqueExerciseList.setExercise(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(ExerciseNotFoundException.class, () -> uniqueExerciseList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueExerciseList.add(ALICE);
        uniqueExerciseList.remove(ALICE);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setExercises((UniqueExerciseList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueExerciseList.add(ALICE);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(BOB);
        uniqueExerciseList.setExercises(expectedUniqueExerciseList);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setExercises((List<Exercise>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueExerciseList.add(ALICE);
        List<Exercise> exerciseList = Collections.singletonList(BOB);
        uniqueExerciseList.setExercises(exerciseList);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(BOB);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Exercise> listWithDuplicateExercises = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateExerciseException.class, () -> uniqueExerciseList.setExercises(listWithDuplicateExercises));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueExerciseList.asUnmodifiableObservableList().remove(0));
    }
}
