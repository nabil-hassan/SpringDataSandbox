package net.nabilh.spring.data.domain;

/**
 * Enum to represent a person's title. Used by the contact domain entity class.
 *
 * Author: Nabil Hassan
 * Date: 29/06/14 00:28
 */
@SuppressWarnings("unused")
public enum Title {

    MR("Mr"), MRS("Mrs"), MISS("Miss"), MS("Ms"), DR("Dr"), LORD("Lord"), PROFESSOR("Professor");

    private String value;

    Title(String value) {
        this.value = value;
    }

    private String getValue() {
        return value;
    }

    private void setValue(String value) {
        this.value = value;
    }
}