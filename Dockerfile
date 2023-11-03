# Use the official Tomcat image as the base image
FROM tomcat:latest

# Remove the default Tomcat webapps folder
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR file to the Tomcat webapps directory
COPY target/simple-servlet-*.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]