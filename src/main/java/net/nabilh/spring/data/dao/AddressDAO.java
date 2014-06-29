package net.nabilh.spring.data.dao;

import net.nabilh.spring.data.domain.Address;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface for Address data access object.
 *
 * Author: Nabil Hassan
 * Date: 29/06/14 17:30
 */
public interface AddressDAO extends CrudRepository<Address, Long> {


}