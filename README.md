# spring-boot-satellizer
Spring-boot-satellizer is an embedded Java Web application that implements support in Spring Boot to the token-based authentication module for AngularJS Satellizer (https://github.com/sahat/satellizer), currently support to Facebook and Google is included

First step is Obtaining the Google and Facebook OAuth Keys


## Obtaining OAuth Keys



<img src="http://images.google.com/intl/en_ALL/images/srpr/logo6w.png" width="150">
- Visit [Google Cloud Console](https://cloud.google.com/console/project)
- Click **CREATE PROJECT** button
- Enter *Project Name*, then click **CREATE**
- Then select *APIs & auth* from the sidebar and click on *Credentials* tab
- Click **CREATE NEW CLIENT ID** button
 - **Application Type**: Web Application
 - **Authorized Javascript origins**: *http://localhost:3000*
 - **Authorized redirect URI**: *http://localhost:3000*

**Note:** Make sure you have turned on **Contacts API** and **Google+ API** in the *APIs* tab.

<hr>

<img src="http://www.doit.ba/img/facebook.jpg" width="150">
- Visit [Facebook Developers](https://developers.facebook.com/)
- Click **Apps > Create a New App** in the navigation bar
- Enter *Display Name*, then choose a category, then click **Create app**
- Click on *Settings* on the sidebar, then click **+ Add Platform**
- Select **Website**
- Enter *http://localhost:3000* for *Site URL*

<hr>

Next in  app.js replace your client ids

```js
angular.module('MyApp', ['satellizer'])
  .config(function($authProvider) {

    $authProvider.facebook({
      clientId: 'Facebook App ID'
    });

    $authProvider.google({
      clientId: 'Google Client ID'
    });

   ...

  });
```

And in  application.propeties replace your secret keys
```
spring.thymeleaf.cache=false
server.port=8080
spring.thymeleaf.prefix=file:./wwwroot/templates/
static.resources.filepath=file:./wwwroot/
logging.level.org.springframework.web=DEBUG
logging.level.co.agileventure.jwtauth=DEBUG
facebook.secret=+your facebook secret goes here+
google.secret=+your google secret goes here+
```
