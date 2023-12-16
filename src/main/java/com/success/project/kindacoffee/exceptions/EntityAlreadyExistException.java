package com.success.project.kindacoffee.exceptions;

public class EntityAlreadyExistException extends RuntimeException {

    private final Object id;
    private final Object searchedEntity;

    public EntityAlreadyExistException(Object id, Object searchedEntity, String message) {
        super(message);
        this.id = id;
        this.searchedEntity = searchedEntity;
    }

    public String toString() {
        return "Failed saving object of " + searchedEntity.getClass()
                .getName() + "while saving " + searchedEntity;
    }

}
