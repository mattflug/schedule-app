package com.schedule.demo.repository;
import com.schedule.demo.model.IceCream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Stereotype annotation from Spring core that marks this class as a Spring Bean (class that spring manages)
// And specifically this class is used for db interactions.
public interface IceCreamDao extends JpaRepository<IceCream, Integer> {
    /*
    This interface will be used for interactions with the database. By default, we can extend the JpaRepository and
    provide information about the class we're looking to interact with and that class' primary key field (This is the
    <IceCream, Integer> part).

    The JpaRepository interface gives us several methods by default to interact with objects of the IceCream class in
    the db (i.e. save(), delete(), findById())

    If we want additional functionality outside of the basic CRUD methods, like maybe we want to search for an ice
    cream by its flavor name, or we want to get all ice cream that is dairy free, we'll define new method names here
    and Spring data will implement them for us
     */

    List<IceCream> findAllByIsDairyFree(boolean isDairyFree);
}
