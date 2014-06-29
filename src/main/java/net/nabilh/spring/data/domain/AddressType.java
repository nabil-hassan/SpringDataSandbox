package net.nabilh.spring.data.domain;

/**
 * Author: Nabil Hassan (inside.the.byte@gmail.com)
 * Date: 29/06/14 00:23
 */
@SuppressWarnings("unused")
public enum AddressType {

    RESIDENTIAL("Residential"), WORK("Work");

    AddressType(String value) {
        this.value = value;
    }

    private String value;

    private String getValue() {
        return value;
    }

    private void setValue(String value) {
        this.value = value;
    }
}