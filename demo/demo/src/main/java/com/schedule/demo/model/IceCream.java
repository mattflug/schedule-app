package com.schedule.demo.model;
import jakarta.persistence.*;

import java.util.Objects;
@Entity // Marks this class as an entity that needs to be stored in the database
@Table(name = "inventory")
// Allows us to add in config info for the table that will be created by Spring Data and Hibernate
public class IceCream {

    /*
    Model classes are used to describe the data structure of whatever you're trying to interact with throughout your
    application. We plan to use this app to keep track of the inventory levels for an ice cream shop.

    Since we plan to use this class to describe the data we should have appropriate fields that represent the object

    Here we'll want an id field (primary key), flavor name field, scoops available, and finally we'll also add a
    boolean field for is dairy free.
     */

  @Id   // Denotes this as the primary key field

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // This will automatically create a pk that is unique and auto-increments
  private int id; // This will be the primary key field in my database

  private String flavor;

  private int scoopsAvailable;

  @Column(name = "dairy_free")
  // This is similar to the @Table annotation allowing us to set config info for the column instead
  private boolean isDairyFree;


  // Constructors
  // Constructors are more important in some of the other classes but it'll be important for us to at least guarantee
  // that we have a no-args constructor (Jackson Databind will use this to create JSON formatting of the Java object)
  public IceCream(){
    // We can actually leave this method blank for now, its existence is what's important to us for the time being
  }


  // Recall that getters are used to retrieve the value of an instance variable that has been marked private
  public int getId(){
    return this.id;
  }

  // Recall setters are used to set the value of an instance variable, we can provide additional logic in these
  // methods so the value is never something unexpected (like a negative number for scoops available)
  public void setId(int id){
    this.id = id;
  }

  public String getFlavor() {
    return flavor;
  }

  public void setFlavor(String flavor) {
    this.flavor = flavor;
  }

  public int getScoopsAvailable() {
    return scoopsAvailable;
  }

  public void setScoopsAvailable(int scoopsAvailable) {
    // Simple use of encapsulation here to prevent scoopsAvailable from being negative
    if (scoopsAvailable < 0){
      this.scoopsAvailable = 0;
    } else{
      this.scoopsAvailable = scoopsAvailable;
    }
  }

  public boolean isDairyFree() {
    return isDairyFree;
  }

  public void setDairyFree(boolean dairyFree) {
    isDairyFree = dairyFree;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IceCream iceCream = (IceCream) o;
    return id == iceCream.id && scoopsAvailable == iceCream.scoopsAvailable && isDairyFree == iceCream.isDairyFree && Objects.equals(flavor, iceCream.flavor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, flavor, scoopsAvailable, isDairyFree);
  }

  @Override
  public String toString() {
    return "IceCream{" +
            "id=" + id +
            ", flavor='" + flavor + '\'' +
            ", scoopsAvailable=" + scoopsAvailable +
            ", isDairyFree=" + isDairyFree +
            '}';
  }
}
