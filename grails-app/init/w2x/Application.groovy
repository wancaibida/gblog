package w2x

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration

@SuppressWarnings('FileEndsWithoutNewline')
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }
}