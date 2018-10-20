# ElderMaps
COMP30022 - Team ElderMaps
Kah Chun Lee  860318    kahl2@student.unimelb.edu.au 
Ho Chi Wong    860302   how4@student.unimelb.edu.au
Huiyi Ruan        860324   ruanh@student.unimelb.edu.au
Chengeng Liu   813174   chengengl@student.unimelb.edu.au
Keming Zhang  813368   kemingz1@student.unimelb.edu.au

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
Google Play client that handles location requests <br/>
WebSocket : <br/>
Used to handle the P2P chat <br/>
Sinch :<br/>
Used for Voice call <br/>
ElderMaps API server :<br/>
Handles all external communication of the application, requests to other APIs, access to our database.<br/>


# Installing and Running
Download the zip of this project. <br/>
Don’t concern yourself with the server, it is well and alive on Heroku ;D <br/>
Run project on Android Studio using an emulator or device. <br/>

#About packages
Eldermap : main package for handling Databse Query<br/>
......GPSServicePkg : Contains GPS module<br/> 
…..LocationPkg : Contains trip (navigation) related code<br/>
…..NavigationPkg : Contains code that handles the navigation and scheduling trips<br/>
…..NearbyLandmarkPkg : Contains code that handles the recommendation of landmarks based on the user’s selected landmark type.<br/>
…..NetworkPkg : Communication between our API server (ElderMaps API) and the Android device.<br/>
…..P2PPkg : Contains code that handles peer 2 peer communication.<br/>
…..ProfilePkg : Contains code that handles creation and modification of a profile.<br/>

#Unit testing



