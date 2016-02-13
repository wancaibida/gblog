package me.w2x.blog.exception

import groovy.transform.TupleConstructor

/**
 * Created by charles.chen on 1/9/16.
 */
@TupleConstructor
class CommandException extends RuntimeException {
    def errors = []

    CommandException(errors) {
        this.errors = errors
    }
}
