# json-xml

    mvn eclipse:eclipse -DdownloadSources=true && sed -i '' -E '/NO_M2ECLIPSE_SUPPORT|<projects\/>/d' .project

(The Gradle Eclipse plugin generates extremely ugly .classpath entries
and generally ugly Eclipse config files. Don't use it.)   