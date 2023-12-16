package com.success.project.kindacoffee.exceptions;

import lombok.Data;

@Data
public class EntityDoesntExistException extends RuntimeException {

    private final Object id;
    private final Object searchedEntity;

    public EntityDoesntExistException(Object id, Object searchedEntity, String message) {
        super(message);
        this.id = id;
        this.searchedEntity = searchedEntity;
    }

    public String toString() {
        return "Failed searching for id" + id + "while looking for entity of class" + searchedEntity.getClass()
                .getName();
    }

}
