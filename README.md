# Kraken
Light-weight object-oriented Tab-API for the Bukkit/Spigot API

###Installation

####Option 1: Maven repository 
    *Coming soon*
####Option 2: JAR
  1. Download the [latest release](https://github.com/Alexandeh/Kraken/releases).
  2. Add the JAR to your project.
    + For Eclipse users, see [here](http://stackoverflow.com/questions/11033603/how-to-create-a-jar-with-external-libraries-included-in-eclipse).
    + For IntelliJ users, see [here](http://stackoverflow.com/questions/1051640/correct-way-to-add-external-jars-lib-jar-to-an-intellij-idea-project).

Instantiate Kraken in your onEnable:

  ```java
public void onEnable() {
  //All your other stuff
  new Kraken(this);
}
  
  ```
  
###Example Usage
```java
@EventHandler
public void onPlayerTabCreateEvent(PlayerTabCreateEvent event) {
    PlayerTab playerTab = event.getPlayerTab();

    playerTab.getByPosition(0, 0).text("&5Player Info:").send();
    playerTab.getByPosition(0, 1).text("&eKills: 86").send();

    playerTab.getByPosition(0, 3).text("&5Your Location:").send();
    playerTab.getByPosition(0, 4).text("&7(300, -50)").send();

    playerTab.getByPosition(2, 0).text("&5Kit:").send();
    playerTab.getByPosition(2, 1).text("&eProt 2, Sharp 2").send();

    playerTab.getByPosition(1, 0).text("&6&lKraken API").send();
}
```
####Result
![result](https://i.gyazo.com/bb380e0eeaa16a3e948d29166790d630.png)
