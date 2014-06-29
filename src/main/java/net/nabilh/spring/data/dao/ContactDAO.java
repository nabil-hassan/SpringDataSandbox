package net.nabilh.spring.data.dao;

import java.util.List;

import net.nabilh.spring.data.domain.*;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface for Contact data access object.
 * N.B. there is no need to implement this interface, since Spring Data will automatically generate the findBySurname() method.
 *
 * Author: Nabil Hassan
 * Date: 29/06/14 00:41
 */
public interface ContactDAO extends CrudRepository<Contact, Long>{

    List<Contact> findBySurname(String surname);

}