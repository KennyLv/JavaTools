#Try downloading the file manually from the project website.Then, install it using the command:
    mvn install:install-file -DgroupId=org.icepdf -DartifactId=icepdf-core -Dversion=4.1.1 -Dpackaging=jar -Dfile=/path/to/file
#Alternatively, if you host your own repository you can deploy the file there:
    mvn deploy:deploy-file -DgroupId=org.icepdf -DartifactId=icepdf-core -Dversion=4.1.1 -Dpackaging=jar -Dfile=/path/to/file -Durl=[url] -DrepositoryId=[id]

#Try downloading the file manually from the project website.Then, install it using the command:
    mvn install:install-file -DgroupId=org.icepdf -DartifactId=icepdf-viewer -Dversion=4.1.1-taverna -Dpackaging=jar -Dfile=/path/to/file
#Alternatively, if you host your own repository you can deploy the file there:
    mvn deploy:deploy-file -DgroupId=org.icepdf -DartifactId=icepdf-viewer -Dversion=4.1.1-taverna -Dpackaging=jar -Dfile=/path/to/file -Durl=[url] -DrepositoryId=[id]
