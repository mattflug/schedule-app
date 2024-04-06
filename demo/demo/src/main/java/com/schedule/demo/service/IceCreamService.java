package com.schedule.demo.service;
import com.schedule.demo.model.IceCream;
import com.schedule.demo.repository.IceCreamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IceCreamService {
    /*
    This class is going to be in charge of connecting the controller layer and the dao layer and does all of the
    business logic in between. It receives information from the controller, then makes an appropriately dao call
    to interact with the db, then it validates the information or does any other necessary logic and then returns the
    correct result to the controller layer
     */

    // Todo Dependency Injection for our DAO Layer
    /*
    Dependency injection is something that's very important in Spring, it's how spring is able to manage all of our
    classes and provide the functionality that it does. There are 2 major ways we do dependency injection

    Constructor Injection
        - Preferred way to do dependency injection in spring
        - More secure than Setter Injection
        - Creates mandatory dependencies
        - Used in 90% of standard circumstances

    Setter Injection
        - Injecting a dependency through a setter method as opposed to a constructor
        - Used for optional dependencies
        - Used to resolve circular dependencies (Class A depends on Class B with also depends on Class A)

    Field Injection (Hidden 3rd form)
        - Generally avoid using this
        - This is accomplished by just putting @Autowired over a field
        - Can create hidden dependencies and hide the level of complexity for a class
     */

    // To do constructor injection we'll do 3 things
    // Create private instance of the dependency
    private IceCreamDao icd;

    // Constructor with @Autowired on top so Spring can wire our beans together
    @Autowired
    public IceCreamService(IceCreamDao icd){
        this.icd = icd;
    }


    // Todo Create a new ice cream entry method
    // We'll need to have a little bit of foresight into how we expect the data to be handed to us from the controller
    // layer

    // I imagine that I will be handed the IceCream object itself from the controller layer
    public IceCream createNewIceCream(IceCream iceCream){
        // We receive the ice cream object from the controller layer and call the dao layer to do the creation in the
        // db

        IceCream returnedIceCream = icd.save(iceCream);
        // Why do this? When we receive the ice cream data from the POST request, it will not have an id field
        // We plan to leverage our database to make sure it actually save the ice cream

        // So the response sent back in the http response should include an IceCream object that has a valid id

        return returnedIceCream;
    }


    // Todo Get a specific Ice Cream record

    /*
    How do we get a specific ice cream record?
    If we're working with a RESTful API, it makes sense for our URLs follow a specific pattern
    Normally if you're trying to access all records of a specific type you'll go to /icecream (normally plural noun)
    To access a specific resource from that larger list it would be /icecream/2 (path variable)
    To filter the whole collection of resources you'd normally use query parameters /icecream?dairy_free=true
     */
    public IceCream getIceCreamById(int id){
        // Now that we've taken in the id value, we can simply look up the record and return it
        Optional<IceCream> returnedIceCream = icd.findById(id);
        // getReferenceById will also work perfectly fine, but I want to try using our Optional Class

        return returnedIceCream.orElseThrow();
        // By choosing to throw an exception we can use a try-catch block in our controller layer and appropriately
        // return a 404 status code instead of a 200
    }


    // Todo Update an ice cream
    // I imagine I will be able to update an ice cream record by the data that is passed into the http request
    // Since I'm accessing a single resource, it makes sense that it should use a path variable to denote the
    // resource to be updated.

    // This means I'll have access to the id field of the record to update as well as the new fields from the body of
    // the request

    public IceCream updateIceCreamById(int id, IceCream iceCream){
        // Get the ice cream from the database with the id
        Optional<IceCream> possibleReturnedIceCream = icd.findById(id);
        // We'll extract the record with the orElseThrow

        IceCream returnedIceCream = possibleReturnedIceCream.orElseThrow();

        // Update the relevant fields
        returnedIceCream.setFlavor(iceCream.getFlavor());
        returnedIceCream.setDairyFree(iceCream.isDairyFree());

        // Validation is important in the service layer
        // If somebody tries to set the scoopsAvailable to a negative number we need some way to handle it
        if (iceCream.getScoopsAvailable() < 0){
            returnedIceCream.setScoopsAvailable(0);
        } else{
            returnedIceCream.setScoopsAvailable(iceCream.getScoopsAvailable());
        }


        // Save the resulting ice cream record
        return icd.save(returnedIceCream);
    }


    public List<IceCream> getAllIceCream(){
        return icd.findAll();
    }

    public List<IceCream> getAllIceCreamByDairyFree(boolean isDairyFree){
        return icd.findAllByIsDairyFree(isDairyFree);
    }

}
