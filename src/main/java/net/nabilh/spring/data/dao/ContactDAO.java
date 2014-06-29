package net.nabilh.spring.data.dao;

import java.util.List;

import net.nabilh.spring.data.domain.*;
import org.springframework.data.repository.CrudRepository;

/**
 * Author: Nabil Hassan (inside.the.byte@gmail.com)
 * Date: 29/06/14 00:41
 */
public interface ContactDAO extends CrudRepository<Contact, Long>{

    List<Contact> findBySurname(String surname);

}