package seedu.zerotoone.testutil;

import seedu.zerotoone.model.AddressBook;
import seedu.zerotoone.model.exercise.Exercise;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withExercise("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Exercise} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withExercise(Exercise exercise) {
        addressBook.addExercise(exercise);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
