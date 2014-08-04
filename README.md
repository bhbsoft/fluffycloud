fluffycloud
===========

FluffyCloud.io is a web site the presents a users virtual infrastructure in a meaningful cross platform way.

The core concepts here is to manage properties and configurations as key:value pairs and to code features 
using a defined convention vs coding complex custom APIs on top of provider APIs.

The Documents folder has high level systems design docs.

##Frontend: 

    HTML5: with mobile support from day 1
    ?CSS:SASS
    JavaScript:?Backbone?Angular?JQuery
   
##Backend:

    springboot 
    noSql:mongoDB Cassanda
    Rdb reporting: AWS RDB if needed (SQL)
   
   
##CLI:

    Once our service layer is going to be built on top of CLI from the existing providers. 
    We will implement aws, gce, then azure. The CLI ontop of the providers cli will be 
    known as percipitation. As percipitation matures there will need a need to implement
    the open source version and closed source version.
    
#Project dependencies
    Groovy/Gradle
    Springboot
    aws-cli
    gcutil    
   
