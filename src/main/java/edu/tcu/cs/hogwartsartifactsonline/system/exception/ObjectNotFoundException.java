package edu.tcu.cs.hogwartsartifactsonline.system.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String id) {
        super("Could not find artifact with Id " + id + " :(");
    }

    public ObjectNotFoundException(Integer id) {
        super("Could not find artifact with Id " + id + " :(");
    }


}
