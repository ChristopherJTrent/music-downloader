# WARNING
Do not expose this application to the internet. No effort has been made to secure this application in any way.  
If you expose this to the internet and someone does something bad to your server, network, or personal data, that is your fault. 
This software is provided AS IS, with no guarantee whatsoever, including for merchantability or fitness for any purpose, including the purpose for which it was developed. Installation and use of this software is solely at the user's own risk. 

### Project Structure
`src/main/java/...`
* `common` - constants and other shared code
* `configuration` - configuration-in-code files, e.g. database config
* `contracts` - java record classes defining the shape of JSON requests
* `controller` - REST controllers
* `model` - data structures for over-the-wire communication
* `entity` - database entities
* `repository` - spring repositories