# ElderMaps
COMP30022 - Team ElderMaps - Matt De Bono Wednesday 9 am group <br/>
Kah Chun Lee  860318    kahl2@student.unimelb.edu.au <br/>
Ho Chi Wong    860302   how4@student.unimelb.edu.au<br/>
Huiyi Ruan        860324   ruanh@student.unimelb.edu.au<br/>
Chengeng Liu   813174   chengengl@student.unimelb.edu.au<br/>
Keming Zhang  813368   kemingz1@student.unimelb.edu.au<br/>

# Synopsis
This Android application (for devices supporting Android 4.4 or later, minSdkVersion is 19 and targetSdkVersion is 27) to assist the elderly and allow them to be able to find nearby landmarks of interests and travel independently. It aims to provide the elderly with a clear walking plan to the nearby destination of interests based on their preferences, walking speed and distance. 

# Motivation
Independent travel for the elderly has always been a challenge in terms of difficulties in scheduling for an voluntary assistance and limitations in existing navigation apps. Though Apps like Google Maps provide users with multiple navigation suggestions, the elderly who have limited experience with modern technology may find them too complicated to use. Moreover, there are limited features to help users under the emergency situation or live chat option.

These main challenges turn out that the combination of manual help and technology would be the best solution for navigating the elder users.

# Prerequisites
To run on Android Studios: Create an emulator to run the app <br/>
To run on the Android devices: Please ensure you have Android 4.4 (API level 19) or later <br/>

# Dependencies
Google Play service : <br/>
&nbsp;&nbsp;&nbsp;&nbsp; Google Play client that handles location requests <br/><br/>
WebSocket : <br/>
&nbsp;&nbsp;&nbsp;&nbsp;Used to handle the P2P chat <br/><br/>
Sinch :<br/>
&nbsp;&nbsp;&nbsp;&nbsp;Used for Voice call <br/><br/>
ElderMaps API server :<br/>
&nbsp;&nbsp;&nbsp;&nbsp;Handles all external communication of the application, requests to other APIs, access to our database.<br/><br/>


# Installing and Running
Download the zip of this project. <br/>
Open the project in the Client folder <br/>
Don’t concern yourself with the server, it is well and alive on Heroku <br/>
Run project on Android Studio using an emulator or device. <br/>

# About packages
Eldermap : main package for handling Databse Query<br/><br/>
Eldermap.GPSServicePkg : Contains GPS module<br/><br/>
Eldermap.LocationPkg : Contains trip (navigation) related code<br/><br/>
Eldermap.NavigationPkg : Contains code that handles the navigation and scheduling trips<br/><br/>
Eldermap.NearbyLandmarkPkg : Contains code that handles the recommendation of landmarks based on the user’s selected landmark type.<br/><br/>
Eldermap.NetworkPkg : Communication between our API server (ElderMaps API) and the Android device.<br/><br/>
Eldermap.P2PPkg : Contains code that handles peer 2 peer communication.<br/><br/>
Eldermap.ProfilePkg : Contains code that handles creation and modification of a profile.<br/><br/>

# Servers
There are two servers contained in the Server folder, API and WebSocket. <br/><br/>
The API Server handles all the requests related to any database communication and is acting as a relay server to redirect requests to other APIs<br/><br/>
The WebSocket Server handles all the chat connections, including id generation, client pairings and message redirection.<br/><br/>

# Unit testing
Unit Tests are located in Eldermap.test.<br/>
Tests are based on PowerMockito(1.7.0) and Mockito(2.8.0) for the purpose of complicated testing.<br/>
Tests include all classes in the project, except for activities(with ui setting) or adapters(with image setting). GPS class which is from Google services is not tested either. <br/>
[Usage and Intro about PowerMockito](https://github.com/powermock/powermock/wiki)

