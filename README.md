# UndertaleME
<img src="https://i.imgur.com/c7vy8cU.png">
UndertaleME is a Open-source Undertale Fight Engine project for oldschool Java-Enabled mobile devices. Developers will be able to create custom fights, dialogs, monsters, items, actions etc, and may even use it to create Undertale fangames with elaborated custom history.
<br>Some stuffs are written in portuguese into the game-side, but it's currently a experimental project and they can be easily translated<br>
<br>
<b>Collaborators: Add your signatures in your codes!! OwO</b>

## Screenshots
<p style="text-align:center"><img src="https://i.imgur.com/oG92TVu.jpg" width="25%" height="25%"> <img src="https://i.imgur.com/msSt9xB.png"> <img src="https://i.imgur.com/KtSu3HM.png"> <img src="https://i.imgur.com/AhrPMV4.png> <img src="https://i.imgur.com/HfDkNjI.png"> <img src="https://i.imgur.com/HfDkNjI.png"></p>

## Current Compatibility
### Minimum requirements
* 176x220 Colored display
* Mixing-unsupported MMAPI
* 1MB RAM
* 250KB storage
* D-Pad, Left/Right soft keys and numerical keyboard
* MIDP 2.0
* CLDC 1.1

### Recommended specifications
* 320x240 Colored display
* mixing-supported MMAPI
* 2MB RAM or higher

### Additional compatibility observations
* Touchscreen are not yet implemented
* Some devices may have issues with keymapping, as they vary a lot. Keymapping adaptation is to be implemented soon
* It's needed to manually configure audio_mode sometimes
* Minimum and recommended specification may change in the future

## Deploy instructions
  **These instructions may change as there's [new plans](https://github.com/JoseskVolpe/UndertaleME/discussions/6) for this project**
### You'll need:
* Eclipse IDE: https://www.eclipse.org/downloads/
* Java ME SDK Eclipse Plugin: https://www.oracle.com/java/technologies/sdk-downloads.html
* Lastest JDK (Needed for Eclipse): https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html
* JDK and JRE 1.4.2 (Needed for Wireless Toolkit): https://www.oracle.com/java/technologies/java-archive-javase-v14-downloads.html
* Sun Wireless Toolkit 2.5.2_01: https://www.oracle.com/java/technologies/java-archive-downloads-javame-downloads.html#sun_java_wireless_toolkit-2.5.2_01b-oth-JPR
* ProGuard 4.6: https://sourceforge.net/projects/proguard/files/proguard/4.6/proguard4.6.zip/download
#### Optional
* Samsung J2ME SDK: https://1drv.ms/u/s!AttE4nJrALFwgcMUwwIT4O716yf59Q?e=i3iKeM
* LG J2ME SDK: https://www.softpedia.com/get/Programming/SDK-DDK/LG-SDK-for-Java-ME-Platform.shtml
<br><br>
### J2ME SDK Setup instructions
* Install Eclipse, JDKs, JREs, Sun Wireless Toolkit and optional SDKs
* Open Eclipse. Go to Help > Install New Software... > Add... > Local... > Then search for Java ME SDK Eclipse Plugin you've downloaded. Click Add <br>
![Captura de Tela (700)](https://user-images.githubusercontent.com/78318343/116649572-f99c0c80-a955-11eb-8b59-32249ad2fb6f.png)
* Select all, Next, Agree the terms and restart Eclipse after installation
* Clik "Install Anyway" if this warning appears <br>
![Captura de Tela (698)](https://user-images.githubusercontent.com/78318343/116649750-6a432900-a956-11eb-8207-a4e9c0e3bd49.png)
* Extract proguard4.6.zip anywhere
* Go to Windows > Preferences > Java ME
* Search for your Wireless Toolkit in WTK Root, and Proguard 4.6 folder
![Captura de Tela (701)](https://user-images.githubusercontent.com/78318343/116650477-8eebd080-a957-11eb-82d6-62318e64a942.png)
* Now, go to Java > Installed JREs > Add... > Standard VM > Next > Search for your j2re1.4.2 folder > Finish
![Captura de Tela (702)](https://user-images.githubusercontent.com/78318343/116650880-631d1a80-a958-11eb-9406-af1ffac77583.png)
* Apply and close

### Project importing instructions
* Fork and pull this project (recommended, so you can share your contribution easier), or download as ZIP and extract it.
* Go to File > Import... > General > Existing Projects into Workspace > Next
![Captura de Tela (703)](https://user-images.githubusercontent.com/78318343/116650967-9b245d80-a958-11eb-8383-a2d3e0a8bc1b.png)
* Mark Select root directory: > Click "Browse" then search for UndertaleME project folder you just pull or extracted > Finish
![Captura de Tela (704)](https://user-images.githubusercontent.com/78318343/116651194-04a46c00-a959-11eb-8091-69947a845ec9.png)
* On Package Explorer, right click UndertaleME Project > Build path > Configure build path...![Captura de Tela (705)](https://user-images.githubusercontent.com/78318343/116651301-48977100-a959-11eb-8c4a-4806cf2b049f.png)
* Go to Java Compiler > Mark "Enable project specific settings" > And select "1.3" in "Compiler compliance level:"
![Captura de Tela (706)](https://user-images.githubusercontent.com/78318343/116651504-bb085100-a959-11eb-9e47-b544cfd3d838.png)
* Now go to Java Build Path > Order and Export > Be sure everything is marked
![Captura de Tela (707)](https://user-images.githubusercontent.com/78318343/116651621-f99e0b80-a959-11eb-879c-63591c210b70.png)
* Apply and close
* 🦊Now you're ready to go, have fun and have a good time :3
![Captura de Tela (708)](https://user-images.githubusercontent.com/78318343/116651981-aaa4a600-a95a-11eb-8855-a3a7b16751d3.png)


## License
UndertaleME is currently licensed under GNU GPLv3 license<br>
Undertale and its content is a intellectual property of Toby Fox and to all its respective owners. All rights reserved<br>
J2ME Army Knife library is a intellectual property of Ovidiu Iliescu. All rights reserved<br>
MIDI contents belongs to their respective owners.<br>
