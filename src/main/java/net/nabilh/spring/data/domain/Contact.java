package net.nabilh.spring.data.domain;

import javax.persistence.*;

/**
 * Domain entity class to represent an address book contact.
 *
 * Author: Nabil Hassan
 * Date: 29/06/14 00:23
 */
@Entity
@SuppressWarnings("unused")
public class Contact {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Title title;

    @Column(nullable = false)
    private String forename;

    @Column(nullable = true)
    private String initials;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = true)
    private String telephoneDay;

    @Column(nullable = true)
    private String telephoneEvening;

    @Column(nullable = true)
    private String telephoneMobile;

    @Column(nullable = true)
    private String emailAddress;

    @Column(nullable = true)
    private String fax;

    @ManyToOne
    @JoinColumn(name="address_fk")
    private Address address;

    public Contact() {
    }

    public Contact(Title title, String forename, String surname) {
        this(title, null, forename, surname, null, null, null, null, null, null);
    }

    public Contact(Title title, String forename, String surname, Address address) {
        this(title, null, forename, surname, null, null, null, null, null, address);
    }

    public Contact(Title title, String initials, String forename, String surname, String telephoneDay,
                   String telephoneEvening, String telephoneMobile, String emailAddress, String fax, Address address) {
        this.title = title;
        this.forename = forename;
        this.initials = initials;
        this.surname = surname;
        this.telephoneDay = telephoneDay;
        this.telephoneEvening = telephoneEvening;
        this.telephoneMobile = telephoneMobile;
        this.emailAddress = emailAddress;
        this.fax = fax;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephoneDay() {
        return telephoneDay;
    }

    public void setTelephoneDay(String telephoneDay) {
        this.telephoneDay = telephoneDay;
    }

    public String getTelephoneEvening() {
        return telephoneEvening;
    }

    public void setTelephoneEvening(String telephoneEvening) {
        this.telephoneEvening = telephoneEvening;
    }

    public String getTelephoneMobile() {
        return telephoneMobile;
    }

    public void setTelephoneMobile(String telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", title=" + title +
                ", forename='" + forename + '\'' +
                ", initials='" + initials + '\'' +
                ", surname='" + surname + '\'' +
                ", telephoneDay='" + telephoneDay + '\'' +
                ", telephoneEvening='" + telephoneEvening + '\'' +
                ", telephoneMobile='" + telephoneMobile + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", fax='" + fax + '\'' +
                '}';
    }
}
