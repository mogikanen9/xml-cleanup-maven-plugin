# xml-cleanup-maven-plugin
Maven plugin which can remove specified tags from xml file(s)

## Project description
 * plugin - core source code of the plugin
 * test - test/demo projects which shows how to use the plugin

## Module "plugin"

### logging
 * folder /scr/plugin-logging contains a sample configuration of [SimpleLogging](https://www.slf4j.org/apidocs/org/slf4j/impl/SimpleLogger.html) for the plugin
 * file "simplelogger.properties" needs to be placed to "${maven.home}/conf/logging/" folder to enable custom logging for the plugin
 * general details regarding logging available at [maven-logging](https://maven.apache.org/maven-logging.html)
